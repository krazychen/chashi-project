package com.io.yy.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
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
import com.io.yy.merchant.vo.CsMerchantQueryVo;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        String keyValue = (String) redisTemplate.opsForValue().get(key);
        log.info("redis key 过期：pattern={},channel={},key={}, value= {}",new String(pattern),channel,key,keyValue);

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
            String merchantId = key.substring(key.lastIndexOf("]")+1);
            CsMerchantOrder csMerchantOrder =  csMerchantOrderService.getById(merchantId);

            String ccId = keyValue;
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
                endC.setTime(csMerchantOrder.getOrderDate());
                String[] endTimeRangeArr = timeRangeArr[timeRangeArr.length-1].split(":");
                endC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeRangeArr[0]));
                endC.set(Calendar.MINUTE, Integer.parseInt(endTimeRangeArr[1]));
                endC.set(Calendar.SECOND, 00);
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
                dataMap = this.getZhibanDataMap(sdf.format(startC.getTime()),wxUser.getNickname(),csMerchantOrder.getWxuserPhone(),csMerchantOrder.getRoomName(),String.valueOf(Double.valueOf(csMerchantOrder.getOrderTimerage())*0.5));

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
                csMerchantOrderMapper.updateUserdStatus(temp);
            }else{
                CsMerchantOrderQueryParam temp = new CsMerchantOrderQueryParam();
                temp.setId(Long.parseLong(id));
                temp.setUsedStatus("2");
                csMerchantOrderMapper.updateUserdStatus(temp);
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
}

