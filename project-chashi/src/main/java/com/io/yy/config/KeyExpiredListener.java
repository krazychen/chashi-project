package com.io.yy.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.param.CsRechargeRecordQueryParam;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.service.CsMembercardConsumService;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.service.CsRechargeRecordService;
import com.io.yy.merchant.entity.CsMerchantNotify;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.mapper.CsMerchantMapper;
import com.io.yy.merchant.mapper.CsMerchantOrderMapper;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.service.CsMerchantNotifyService;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.service.CsMerchantService;
import com.io.yy.merchant.service.CsTearoomService;
import com.io.yy.merchant.vo.CsMerchantQueryVo;
import com.io.yy.merchant.vo.CsTearoomQueryVo;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.wxops.entity.WxUser;
import com.io.yy.wxops.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis key过期回调
 *
 * @author kris
 * @date 2021/10/207
 **/
@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    //用于执行任务的线程池
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CsRechargeRecordService csRechargeRecordService;

    @Autowired
    private CsMembercardOrderService csMembercardOrderService;

    @Autowired
    private CsMerchantOrderService csMerchantOrderService;

    @Autowired
    private CsCouponReleasedService csCouponReleasedService;

    @Autowired
    private CsMembercardConsumService csMembercardConsumService;

    @Autowired
    private CsMerchantOrderMapper csMerchantOrderMapper;

    @Autowired
    private CsMerchantNotifyService csMerchantNotifyService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private CsMerchantService csMerchantService;

    @Autowired
    private CsTearoomService csTearoomService;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获取配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        //过期的key
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("redis key 过期：pattern={},channel={},key={}",new String(pattern),channel,key);

        //解决集群并发问题，由于getAndSet不是原子操作，高并发，大于1000以上需要更换方法
        String newKey = key + ".end.lock";
        String oldLock = (String) redisTemplate.opsForValue().getAndSet(newKey, "1");
        if (StrUtil.isNotBlank(oldLock) && oldLock.equals("1"))
            return;

        if(key.indexOf("CARD_ORDER]")!=-1){
            String outTradeNo = key.substring(key.lastIndexOf("]")+1);
            CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
            csMembercardOrderQueryParam.setOutTradeNo(outTradeNo);
            csMembercardOrderQueryParam.setPaymentStatus(4);
            boolean flag= csMembercardOrderService.updatePaymentStatus(csMembercardOrderQueryParam);
            if(flag){
                redisTemplate.delete(key);
                redisTemplate.delete(newKey);
            }

        }else if(key.indexOf("RECHARGE_ORDER]")!=-1){
            String outTradeNo = key.substring(key.lastIndexOf("]")+1);
            CsRechargeRecordQueryParam csRechargeRecordQueryParam = new CsRechargeRecordQueryParam();
            csRechargeRecordQueryParam.setOutTradeNo(outTradeNo);
            csRechargeRecordQueryParam.setPaymentStatus(4);
            boolean flag=csRechargeRecordService.updatePaymentStatus(csRechargeRecordQueryParam);
            if(flag){
                redisTemplate.delete(key);
                redisTemplate.delete(newKey);
            }
        }else if(key.indexOf("MERCHANT_ORDER]")!=-1){
            String id = key.substring(key.lastIndexOf("]")+1);

            CsMerchantOrderQueryParam csMerchantOrderQueryParam = new CsMerchantOrderQueryParam();
            csMerchantOrderQueryParam.setId(Long.parseLong(id));
            csMerchantOrderQueryParam.setPaymentStatus(4);

            CsMerchantOrder csMerchantOrder=csMerchantOrderService.getById(Long.parseLong(id));
            boolean flag = orderFailCommonOP(csMerchantOrderQueryParam,csMerchantOrder);
            if(flag){
                redisTemplate.delete(key);
                redisTemplate.delete(newKey);
            }
        }else if(key.indexOf("ORDER_NOTIFY]")!=-1){
            // 获取通知信息
            String merchantId = key.substring(key.lastIndexOf("]")+1,key.lastIndexOf("["));
            CsMerchantOrder csMerchantOrder =  csMerchantOrderService.getById(merchantId);

            String ccId = key.substring(key.lastIndexOf("[")+1);
            CsMerchantNotify csMerchantNotify = csMerchantNotifyService.getById(ccId);
            if(csMerchantNotify ==null){
                log.error(ccId+" 通知不存在！");
            }
            String notifyBaojieID = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notifyBaojieID")).collect(Collectors.toList()).get(0).getConfigValue();
            String notifyZhibaoID = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notifyZhibaoID")).collect(Collectors.toList()).get(0).getConfigValue();
            String appid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList()).get(0).getConfigValue();

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("touser",csMerchantNotify.getOpenid());
            Map<String,String> minMap = new HashMap();
            minMap.put("appid",appid);
            minMap.put("pagepath","/");
            map.put("miniprogram",minMap);
            map.put("url","http://weixin.qq.com/download");

            Map dataMap = null;
            // 保洁
            if(csMerchantNotify.getNotifyType().equals(0)){
                map.put("template_id",notifyBaojieID);
                //计算订单开始时间和结束时间
                String[] timeRangeArr = csMerchantOrder.getOrderTimerage().split("-");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // 设置结束时间:
                Calendar endC = Calendar.getInstance();
                // 清除所有:
                endC.clear();
                if(csMerchantOrder.getNextOrderDate()!=null){
                    endC.setTime(csMerchantOrder.getNextOrderDate());
                    timeRangeArr = csMerchantOrder.getNextOrderTimerage().split("-");
                    String[] endTimeRangeArr = timeRangeArr[timeRangeArr.length-1].split(":");
                    endC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeRangeArr[0]));
                    endC.set(Calendar.MINUTE, Integer.parseInt(endTimeRangeArr[1]));
                    endC.set(Calendar.SECOND, 00);
                }else{
                    endC.setTime(csMerchantOrder.getOrderDate());
                    String[] endTimeRangeArr = timeRangeArr[timeRangeArr.length-1].split(":");
                    endC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeRangeArr[0]));
                    endC.set(Calendar.MINUTE, Integer.parseInt(endTimeRangeArr[1]));
                    endC.set(Calendar.SECOND, 00);
                }

                dataMap = this.getBaojieDataMap(sdf.format(endC.getTime()),csMerchantOrder.getRoomName());
            }else{
                map.put("template_id",notifyZhibaoID);

                //计算订单开始时间和结束时间
                String[] timeRangeArr = csMerchantOrder.getOrderTimerage().split("-");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 设置开始时间:
                Calendar startC = Calendar.getInstance();
                // 清除所有:
                startC.clear();
                startC.setTime(csMerchantOrder.getOrderDate());
                String[] startTimeRangeArr = timeRangeArr[0].split(":");
                startC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeRangeArr[0]));
                startC.set(Calendar.MINUTE, Integer.parseInt(startTimeRangeArr[1]));
                startC.set(Calendar.SECOND, 00);

                // 获取用户
                WxUser wxUser = wxUserService.getById(csMerchantOrder.getWxuserId());
                dataMap = this.getZhibanDataMap(sdf.format(startC.getTime()),wxUser.getNickname(),csMerchantOrder.getWxuserPhone(),csMerchantOrder.getRoomName(),String.valueOf(Double.valueOf(csMerchantOrder.getOrderOriginTimenum())));

            }
            map.put("data",dataMap);
            log.debug(JSONObject.toJSONString(dataMap));

            this.sendWXMessage(map);

            redisTemplate.delete(key);
            redisTemplate.delete(newKey);

        }else if (key.indexOf("ORDER_END_USED]")!=-1){
            String id = key.substring(key.lastIndexOf("]")+1);
            //获取订单
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapper.eq("id",id);
            CsMerchantOrder csMerchantOrder = csMerchantOrderService.getOne(csMerchantOrderQueryWrapper);

            // 如果使用状态为1,更新为3，否则更新为2
            if(StringUtils.isNotBlank(csMerchantOrder.getUsedStatus())&&"1".equals(csMerchantOrder.getUsedStatus())){
                CsMerchantOrderQueryParam temp = new CsMerchantOrderQueryParam();
                temp.setId(Long.parseLong(id));
                temp.setUsedStatus("3");
                csMerchantOrderMapper.updateUsedStatus(temp);
            }else{
                CsMerchantOrderQueryParam temp = new CsMerchantOrderQueryParam();
                temp.setId(Long.parseLong(id));
                temp.setUsedStatus("2");
                csMerchantOrderMapper.updateUsedStatus(temp);
            }

            //如果有续单订单，则不关闭空开
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapperXD=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapperXD.eq("origin_order_id",id);
            CsMerchantOrder csMerchantOrderXD = csMerchantOrderService.getOne(csMerchantOrderQueryWrapperXD);
            if(csMerchantOrderXD==null) {

                //关闭空开
                try {
                    CsMerchantQueryVo csMerchantQueryVo = csMerchantService.getCsMerchantById(csMerchantOrder.getMerchantId());
                    CsTearoomQueryVo csTearoomQueryVo = csTearoomService.getCsTearoomById(csMerchantOrder.getTearoomId());
                    if (StringUtils.isNotEmpty(csMerchantQueryVo.getKkClientId()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkRedirectUri())
                            && StringUtils.isNotEmpty(csMerchantQueryVo.getKkPassword()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkAppSecret())
                            && StringUtils.isNotEmpty(csMerchantQueryVo.getKkProjectCode()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkUname())) {
                        if (StringUtils.isNotEmpty(csTearoomQueryVo.getKkMac()) && StringUtils.isNotEmpty(csTearoomQueryVo.getKkOcSwitch())) {
                            String code = getCode(csMerchantQueryVo);
                            String token = getToken(csMerchantQueryVo, code);

                            PUT_BOX_CONTROL_Switch(csMerchantQueryVo, csTearoomQueryVo, token, "close");
                        } else {
                            log.error(id + ":茶室空开配置错误！！！");
                        }
                    } else {
                        log.error(id + ":商店空开配置错误！！！");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            redisTemplate.delete("SY1_isExist]"+csMerchantOrder.getId());
            redisTemplate.delete(key);
            redisTemplate.delete(newKey);
        }
        else if (key.indexOf("ORDER_KONGTAI_USED]")!=-1){
            String id = key.substring(key.lastIndexOf("]")+1);
            //获取订单
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapper.eq("id",id);
            CsMerchantOrder csMerchantOrder = csMerchantOrderService.getOne(csMerchantOrderQueryWrapper);

            //如果是续单订单，则不需要开启空开
            if(StringUtils.isBlank(csMerchantOrder.getOriginOrderId())) {
                try {
                    CsMerchantQueryVo csMerchantQueryVo = csMerchantService.getCsMerchantById(csMerchantOrder.getMerchantId());
                    CsTearoomQueryVo csTearoomQueryVo = csTearoomService.getCsTearoomById(csMerchantOrder.getTearoomId());
                    if (StringUtils.isNotEmpty(csMerchantQueryVo.getKkClientId()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkRedirectUri())
                            && StringUtils.isNotEmpty(csMerchantQueryVo.getKkPassword()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkAppSecret())
                            && StringUtils.isNotEmpty(csMerchantQueryVo.getKkProjectCode()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkUname())) {
                        if (StringUtils.isNotEmpty(csTearoomQueryVo.getKkMac()) && StringUtils.isNotEmpty(csTearoomQueryVo.getKkOcSwitch())) {
                            String code = getCode(csMerchantQueryVo);
                            String token = getToken(csMerchantQueryVo, code);

                            PUT_BOX_CONTROL_Switch(csMerchantQueryVo, csTearoomQueryVo, token, "open");
                        } else {
                            log.error(id + ":茶室空开配置错误！！！");
                        }
                    } else {
                        log.error(id + ":商店空开配置错误！！！");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            redisTemplate.delete(key);
            redisTemplate.delete(newKey);
        }else if (key.indexOf("ORDER_SHENGYING1_USED]")!=-1){
            String id = key.substring(key.lastIndexOf("]")+1);
            //获取订单
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapper.eq("id",id);
            CsMerchantOrder csMerchantOrder = csMerchantOrderService.getOne(csMerchantOrderQueryWrapper);

            try {
                CsTearoomQueryVo csTearoomQueryVo = csTearoomService.getCsTearoomById(csMerchantOrder.getTearoomId());
                if(StringUtils.isNotEmpty(csTearoomQueryVo.getSyUrl())&&
                        StringUtils.isNotEmpty(csTearoomQueryVo.getSyProKey())&&
                        StringUtils.isNotEmpty(csTearoomQueryVo.getSySname())&&
                        StringUtils.isNotEmpty(csTearoomQueryVo.getSySid1())){
                    String requestStr = csTearoomQueryVo.getSyUrl()+"?sid="+csTearoomQueryVo.getSySid1()+
                            "&ProKey="+csTearoomQueryVo.getSyProKey()+
                            "&sname="+csTearoomQueryVo.getSySname();
                    log.info("声音："+requestStr);
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url(requestStr)
                            .method("GET", null)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string();
                    log.info("声音:"+responseBody);
                    JSONObject jsonObject = JSON.parseObject(responseBody);
                    String Success = jsonObject.getString("Success");
                    if("true".equals(Success)){
                        log.info(id+": 声音提醒正常");
                        redisTemplate.opsForValue().set("SY1_isExist]"+csMerchantOrder.getId(),csMerchantOrder.getId().toString());
                    }else{
                        log.error("声音提醒api存在问题，请检查！");
                    }
                }else{
                    log.error(id+":声音提醒配置错误！！！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            redisTemplate.delete(key);
            redisTemplate.delete(newKey);
        }else if (key.indexOf("ORDER_SHENGYING2_USED]")!=-1){
            String id = key.substring(key.lastIndexOf("]")+1);
            //获取订单
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapper.eq("id",id);
            CsMerchantOrder csMerchantOrder = csMerchantOrderService.getOne(csMerchantOrderQueryWrapper);

            //如果有续单订单，则不关闭空开
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapperXD=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapperXD.eq("origin_order_id",id);
            CsMerchantOrder csMerchantOrderXD = csMerchantOrderService.getOne(csMerchantOrderQueryWrapperXD);
            if(csMerchantOrderXD==null) {
                try {
                    CsTearoomQueryVo csTearoomQueryVo = csTearoomService.getCsTearoomById(csMerchantOrder.getTearoomId());
                    if (StringUtils.isNotEmpty(csTearoomQueryVo.getSyUrl()) &&
                            StringUtils.isNotEmpty(csTearoomQueryVo.getSyProKey()) &&
                            StringUtils.isNotEmpty(csTearoomQueryVo.getSySname()) &&
                            StringUtils.isNotEmpty(csTearoomQueryVo.getSySid2())) {
                        String requestStr = csTearoomQueryVo.getSyUrl() + "?sid=" + csTearoomQueryVo.getSySid2() +
                                "&ProKey=" + csTearoomQueryVo.getSyProKey() +
                                "&sname=" + csTearoomQueryVo.getSySname();
                        log.info("声音：" + requestStr);
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        Request request = new Request.Builder()
                                .url(requestStr)
                                .method("GET", null)
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseBody = response.body().string();
                        log.info("声音:" + responseBody);
                        JSONObject jsonObject = JSON.parseObject(responseBody);
                        String Success = jsonObject.getString("Success");
                        if ("true".equals(Success)) {
                            log.info(id + ": 声音提醒正常");
                        } else {
                            log.error("声音提醒api存在问题，请检查！");
                        }
                    } else {
                        log.error(id + ":声音提醒配置错误！！！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            redisTemplate.delete(key);
            redisTemplate.delete(newKey);
        }else if (key.indexOf("ORDER_SHENGYING3_USED]")!=-1){
            String id = key.substring(key.lastIndexOf("]")+1);
            //获取订单
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapper.eq("id",id);
            CsMerchantOrder csMerchantOrder = csMerchantOrderService.getOne(csMerchantOrderQueryWrapper);

            //如果有续单订单，则不关闭空开
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapperXD=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapperXD.eq("origin_order_id",id);
            CsMerchantOrder csMerchantOrderXD = csMerchantOrderService.getOne(csMerchantOrderQueryWrapperXD);
            if(csMerchantOrderXD==null) {
                try {
                    CsTearoomQueryVo csTearoomQueryVo = csTearoomService.getCsTearoomById(csMerchantOrder.getTearoomId());
                    if (StringUtils.isNotEmpty(csTearoomQueryVo.getSyUrl()) &&
                            StringUtils.isNotEmpty(csTearoomQueryVo.getSyProKey()) &&
                            StringUtils.isNotEmpty(csTearoomQueryVo.getSySname()) &&
                            StringUtils.isNotEmpty(csTearoomQueryVo.getSySid3())) {
                        String requestStr = csTearoomQueryVo.getSyUrl() + "?sid=" + csTearoomQueryVo.getSySid3() +
                                "&ProKey=" + csTearoomQueryVo.getSyProKey() +
                                "&sname=" + csTearoomQueryVo.getSySname();
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        Request request = new Request.Builder()
                                .url(requestStr)
                                .method("GET", null)
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseBody = response.body().string();
                        log.info(responseBody);
                        JSONObject jsonObject = JSON.parseObject(responseBody);
                        String Success = jsonObject.getString("Success");
                        if ("true".equals(Success)) {
                            log.info(id + ": 声音提醒正常");
                        } else {
                            log.error("声音提醒api存在问题，请检查！");
                        }
                    } else {
                        log.error(id + ":声音提醒配置错误！！！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            redisTemplate.delete(key);
            redisTemplate.delete(newKey);
        }

    }

    public boolean orderFailCommonOP(CsMerchantOrderQueryParam csMerchantOrderQueryParam, CsMerchantOrder csMerchantOrder){

        boolean flag=csMerchantOrderService.updatePaymentStatus(csMerchantOrderQueryParam);

        //获取数据库的csMerchantOrder对象
        CsMerchantOrder newCsMerchantOrder=csMerchantOrderService.getById(csMerchantOrder.getId());
        //判断是否有优惠卷，有的话将优惠卷设置为未使用；
        if(newCsMerchantOrder.getCouponReleasedId()!=null && newCsMerchantOrder.getCouponReleasedId()!=0){
            CsCouponReleasedQueryParam csCouponReleasedQueryParam = new CsCouponReleasedQueryParam();
            csCouponReleasedQueryParam.setId(csMerchantOrder.getCouponReleasedId());
            csCouponReleasedQueryParam.setIsUsed(0);
            csCouponReleasedQueryParam.setUsedTime(null);
            flag = csCouponReleasedService.updateUsedStatus(csCouponReleasedQueryParam);
        }

        //判断是否有会员卡，有的话删除会员卡折扣、优惠数据，并更新会员卡的剩余优惠时长和金额
        if(newCsMerchantOrder.getMembercardOrderId()!=null && newCsMerchantOrder.getMembercardOrderId()!=0){
            QueryWrapper<CsMembercardConsum> csMembercardConsumQueryWrapper=new QueryWrapper<CsMembercardConsum>();
            csMembercardConsumQueryWrapper.eq("room_order_id",csMerchantOrder.getId());
            List<CsMembercardConsum> csMembercardConsumList=csMembercardConsumService.list(csMembercardConsumQueryWrapper);
            csMembercardConsumList.stream().forEach(cc-> {
                try {
                    csMembercardConsumService.deleteCsMembercardConsum(cc.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //更新会员卡的剩余时长和剩余金额
            if(newCsMerchantOrder.getOrderMbAmount()!=0 || newCsMerchantOrder.getOrderMbTimenum()!=0) {
                CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
                csMembercardOrderQueryParam.setId(newCsMerchantOrder.getMembercardOrderId());
                csMembercardOrderQueryParam.setRestDiscountPrice(newCsMerchantOrder.getOrderMbAmount());
                csMembercardOrderQueryParam.setRestDiscountTime(newCsMerchantOrder.getOrderMbTimenum().doubleValue());
                flag = csMembercardOrderService.addRest(csMembercardOrderQueryParam);
            }
        }
        return flag;
    }

    public String getWXMessageToken(){

        String token = (String)redisTemplate.opsForValue().get("wx_message_token");

        if(StringUtils.isEmpty(token)){
            // 获取微信配置
            List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();
            String notifyWXAppid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notifyWXAppid")).collect(Collectors.toList()).get(0).getConfigValue();
            String notifyWXSecret = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notifyWXSecret")).collect(Collectors.toList()).get(0).getConfigValue();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                            "&appid=" + notifyWXAppid +
                            "&secret=" + notifyWXSecret
                    )
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                log.info(responseBody);
                JSONObject jsonObject = JSON.parseObject(responseBody);
                String errorCode = jsonObject.getString("errcode");
                // 如果有错误代码，返回错误信息
                if(StringUtils.isNotBlank(errorCode)){
                    return (String) jsonObject.get("errmsg");
                }
                String access_token = jsonObject.getString("access_token");
                String expires_in = jsonObject.getString("expires_in");

                redisTemplate.opsForValue().set("wx_message_token",access_token,Integer.parseInt(expires_in), TimeUnit.SECONDS);
                token=access_token;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return token;

    }

    public void sendWXMessage(Map map){

        log.info(JSON.toJSONString(map));
        String token = this.getWXMessageToken();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(map));
        Request request = new Request.Builder()
                .url("https://api.weixin.qq.com/cgi-bin/message/template/send?" +
                        "access_token="+token)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info("消息发送情况："+responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getBaojieDataMap(String endTime,String roomName){

        Map<String,Object> dataMap = new HashMap<String,Object>();
        Map<String,String> firstMap = new HashMap<String,String>();
        firstMap.put("value","您好，有一间保洁服务需求时间即将到，请及时处理！");
        Map<String,String> key1Map = new HashMap<String,String>();
        key1Map.put("value","茶室清理项目");
        Map<String,String> key2Map = new HashMap<String,String>();
        key2Map.put("value",endTime);
        Map<String,String> key3Map = new HashMap<String,String>();
        key3Map.put("value",roomName);
        Map<String,String> remarkMap = new HashMap<String,String>();
        remarkMap.put("value","请保洁人员做好清洁准备。");
        dataMap.put("first",firstMap);
        dataMap.put("keyword1",key1Map);
        dataMap.put("keyword2",key2Map);
        dataMap.put("keyword3",key3Map);
        dataMap.put("remark",remarkMap);

        return dataMap;
    }

    public Map getZhibanDataMap(String startTime,String userName,String phone, String roomName,String times){

        Map<String,Object> dataMap = new HashMap<String,Object>();
        Map<String,String> firstMap = new HashMap<String,String>();
        firstMap.put("value","您好，接到一个新订单：详情");
        Map<String,String> key1Map = new HashMap<String,String>();
        key1Map.put("value",startTime);
        Map<String,String> key2Map = new HashMap<String,String>();
        key2Map.put("value",userName);
        Map<String,String> key3Map = new HashMap<String,String>();
        key3Map.put("value",phone);
        Map<String,String> key4Map = new HashMap<String,String>();
        key4Map.put("value",roomName);
        Map<String,String> key5Map = new HashMap<String,String>();
        key5Map.put("value",times);
        Map<String,String> remarkMap = new HashMap<String,String>();
        remarkMap.put("value","请客服人员关注服务。");
        dataMap.put("first",firstMap);
        dataMap.put("keyword1",key1Map);
        dataMap.put("keyword2",key2Map);
        dataMap.put("keyword3",key3Map);
        dataMap.put("keyword4",key4Map);
        dataMap.put("keyword5",key5Map);
        dataMap.put("remark",remarkMap);

        return dataMap;
    }


    private String getCode(CsMerchantQueryVo csMerchantQueryVo) throws Exception{

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "response_type=code" +
                "&client_id=" + csMerchantQueryVo.getKkClientId()+
                "&redirect_uri=" + csMerchantQueryVo.getKkRedirectUri()+
                "&uname=" + csMerchantQueryVo.getKkUname() +
                "&passwd=" + csMerchantQueryVo.getKkPassword());
        Request request = new Request.Builder()
                .url("https://open.snd02.com:443/oauth/authverify2.as")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info(responseBody);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        String success = jsonObject.getString("success");
        String code = null;
        if("true".equals(success)){
            code= jsonObject.getString("code");
        }
        return code;
    }

    private String getToken(CsMerchantQueryVo csMerchantQueryVo, String code) throws Exception{

        // 获取曼顿空开的redis key，若不存在则需要去取key；
        String token = (String)redisTemplate.opsForValue().get("KK_TOKEN");
        if(StringUtils.isEmpty(token)) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

            String client_secret = MD5(csMerchantQueryVo.getKkClientId() + "authorization_code"
                    + csMerchantQueryVo.getKkRedirectUri()
                    + code + csMerchantQueryVo.getKkAppSecret());
            log.info(client_secret);
            RequestBody body = RequestBody.create(mediaType, "client_id=" + csMerchantQueryVo.getKkClientId() +
                    "&client_secret=" + client_secret +
                    "&grant_type=authorization_code" +
                    "&redirect_uri=" + csMerchantQueryVo.getKkRedirectUri() +
                    "&code=" + code +
                    "&response_type=code");
            Request request = new Request.Builder()
                    .url("https://open.snd02.com:443/oauth/token.as")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info(responseBody);
            JSONObject jsonObject = JSON.parseObject(responseBody);
            String success = jsonObject.getString("success");
            JSONObject dataObject = JSON.parseObject(jsonObject.getString("data"));
            if ("true".equals(success)) {
                token = dataObject.getString("accessToken");
                Long expires_in = dataObject.getLong("expiresIn");
                redisTemplate.opsForValue().set("KK_TOKEN",token,expires_in, TimeUnit.SECONDS);
            } else {
                log.error(dataObject.getString("description") + ":" + dataObject.getString("error"));
            }
        }
        return token;
    }

    private boolean get_switch_status(CsMerchantQueryVo csMerchantQueryVo, CsTearoomQueryVo csTearoomQueryVo, String token, String operation) throws Exception{
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("client_id", csMerchantQueryVo.getKkClientId());
        packageParams.put("method", "GET_BOX_CHANNELS_OC");
        packageParams.put("access_token", token);
        packageParams.put("timestamp", sdf.format(nowDate));
        packageParams.put("projectCode", csMerchantQueryVo.getKkProjectCode());
        packageParams.put("mac", csTearoomQueryVo.getKkMac());
        packageParams.put("addr", csTearoomQueryVo.getKkOcSwitch());

        String reqMessage = concatSignString(packageParams);
        String sigeMessage = concatMessageString(packageParams);
        log.info(sigeMessage);
        sigeMessage+=csMerchantQueryVo.getKkAppSecret();
        String sign = MD5(sigeMessage);
        reqMessage=reqMessage+"&sign="+sign;
        log.info(reqMessage);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, reqMessage);
        Request request = new Request.Builder()
                .url("https://open.snd02.com:443/invoke/router.as")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info(responseBody);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        String success = jsonObject.getString("success");
        JSONArray dataArray = JSON.parseArray(jsonObject.getString("data"));
        JSONObject dataObject = JSON.parseObject(dataArray.getString(0));
        if ("true".equals(success)) {
//            log.info(dataObject.toString());
//            log.info(String.valueOf(dataObject.getBoolean("oc")));
            return dataObject.getBoolean("oc");
        } else {
            log.error(jsonObject.getString("message") );
            return false;
        }
    }

    private void PUT_BOX_CONTROL_Switch(CsMerchantQueryVo csMerchantQueryVo, CsTearoomQueryVo csTearoomQueryVo, String token, String operation) throws Exception{

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("client_id", csMerchantQueryVo.getKkClientId());
        packageParams.put("method", "PUT_BOX_CONTROL");
        packageParams.put("access_token", token);
        packageParams.put("timestamp", sdf.format(nowDate));
        packageParams.put("projectCode", csMerchantQueryVo.getKkProjectCode());
        packageParams.put("mac", csTearoomQueryVo.getKkMac());
        packageParams.put("cmd", "OCSWITCH");
        packageParams.put("value1", operation);
        packageParams.put("value2", csTearoomQueryVo.getKkOcSwitch());

        String reqMessage = concatSignString(packageParams);
        String sigeMessage = concatMessageString(packageParams);
        log.info(sigeMessage);
        sigeMessage+=csMerchantQueryVo.getKkAppSecret();
        String sign = MD5(sigeMessage);
        reqMessage=reqMessage+"&sign="+sign;
        log.info(reqMessage);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, reqMessage);
        Request request = new Request.Builder()
                .url("https://open.snd02.com:443/invoke/router.as")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info(responseBody);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        String success = jsonObject.getString("success");
        JSONObject dataObject = JSON.parseObject(jsonObject.getString("data"));
        if ("true".equals(success)) {
            log.info(dataObject.getString("message") );
        } else {
            log.error(dataObject.getString("message") );
        }

    }

    static char[] hc = "0123456789abcdef".toCharArray();

    public static String MD5(String param) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(param.getBytes("utf-8"));
        byte[] d = md.digest();
        StringBuilder r = new StringBuilder(d.length*2);
        for (byte b : d) {
            r.append(hc[(b >> 4) & 0xF]);
            r.append(hc[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String concatMessageString(Map<String, String> map) {
        Map<String, String> paramterMap = new HashMap<>();
        map.forEach((key, value) -> paramterMap.put(key, value));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();

        for (String k : keyArray) {
            sb.append(map.get(k));
        }
        return sb.toString();
    }

    public static String concatSignString(Map<String, String> map) {
        Map<String, String> paramterMap = new HashMap<>();
        map.forEach((key, value) -> paramterMap.put(key, value));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();

        for (String k : keyArray) {
            if (StringUtils.isEmpty(sb.toString())) {
                sb.append("");
            } else {
                sb.append("&");
            }
            sb.append(k).append("=").append(map.get(k));
        }
        return sb.toString();
    }
}

