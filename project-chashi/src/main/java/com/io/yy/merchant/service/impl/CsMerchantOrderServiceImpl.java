package com.io.yy.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.service.CsMembercardConsumService;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.service.CsRechargeConsumService;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.mapper.CsMerchantMapper;
import com.io.yy.merchant.mapper.CsMerchantOrderMapper;
import com.io.yy.merchant.mapper.CsTearoomMapper;
import com.io.yy.merchant.param.CsMerchantNotifyQueryParam;
import com.io.yy.merchant.service.CsMerchantNotifyService;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.vo.*;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.HttpServletResponseUtil;
import com.io.yy.util.UUIDUtil;
import com.io.yy.util.excel.ExcelExport;
import com.io.yy.util.excel.annotation.ExcelField;
import com.io.yy.util.lang.DateUtils;
import com.io.yy.util.lang.DoubleUtils;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * <pre>
 * 商店茶室订单记录 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Slf4j
@Service
public class CsMerchantOrderServiceImpl extends BaseServiceImpl<CsMerchantOrderMapper, CsMerchantOrder> implements CsMerchantOrderService {

    @Autowired
    private CsMerchantOrderMapper csMerchantOrderMapper;

    @Autowired
    private CsMerchantMapper csMerchantMapper;

    @Autowired
    private CsTearoomMapper csTearoomMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CsCouponReleasedService csCouponReleasedService;

    @Autowired
    private CsMembercardConsumService csMembercardConsumService;

    @Autowired
    private CsMembercardOrderService csMembercardOrderService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private CsRechargeConsumService csRechargeConsumService;

    @Autowired
    private CsMerchantNotifyService csMerchantNotifyService;

    @Autowired
    private CsMerchantOrderService csMerchantOrderService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMerchantOrder(CsMerchantOrder csMerchantOrder) throws Exception {
        return super.save(csMerchantOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMerchantOrder(CsMerchantOrder csMerchantOrder) throws Exception {
        return super.updateById(csMerchantOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchantOrder(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchantOrders(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsMerchantOrderQueryVo getCsMerchantOrderById(Serializable id) throws Exception {
        return csMerchantOrderMapper.getCsMerchantOrderDetailById(id);
    }

    @Override
    public Paging<CsMerchantOrderQueryVo> getCsMerchantOrderPageList(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        Page page = setPageParam(csMerchantOrderQueryParam, OrderItem.desc("create_time"));
        IPage<CsMerchantOrderQueryVo> iPage = csMerchantOrderMapper.getCsMerchantOrderPageList(page, csMerchantOrderQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsMerchantOrderQueryParam csMerchantOrderQueryParam) {
        return csMerchantOrderMapper.updateStatus(csMerchantOrderQueryParam) > 0;
    }

    /**
     * 根据tearoomid和预订日期获取当前茶室已经被预定的时间段，返回是时间段的一个包含","的字符串
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    @Override
    public String getTimeRangeForWx(CsMerchantOrderQueryParam csMerchantOrderQueryParam) {
        List<String> list = csMerchantOrderMapper.getTimeRangeForWx(csMerchantOrderQueryParam);
        String timeRanges = "";
        for(int i=0 ; i<list.size();i++){
            if(i != list.size()-1){
                timeRanges += list.get(i)+",";
            }else{
                timeRanges += list.get(i);
            }
        }
        return timeRanges;
    }

    /**
     * 更新支付状态
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    @Override
    public boolean updatePaymentStatus(CsMerchantOrderQueryParam csMerchantOrderQueryParam) {
        return csMerchantOrderMapper.updatePaymentStatus(csMerchantOrderQueryParam) > 0;
    }

    @Override
    public String getLockKey(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        String rtnMessage = "大厅开锁密码：";
        // 先去订单里面取，看是否已经存在key，存在则不需要再去请求，不存在则进行判断
        CsMerchantOrderQueryVo csMerchantOrderQueryVo = this.getCsMerchantOrderById(csMerchantOrderQueryParam.getId());
        if(StringUtils.isNotBlank(csMerchantOrderQueryVo.getKeyboardPwdId())&&StringUtils.isNotBlank((csMerchantOrderQueryVo.getKeyboardPwd()))){
            rtnMessage += csMerchantOrderQueryVo.getKeyboardPwd();
            if(StringUtils.isNotBlank(csMerchantOrderQueryVo.getRkeyboardPwdId())&&StringUtils.isNotBlank((csMerchantOrderQueryVo.getRkeyboardPwd()))){
                rtnMessage += "\n\r茶室开锁密码：" + csMerchantOrderQueryVo.getRkeyboardPwd();
            }
            return rtnMessage;
        }

        // 取merchant的clientId，lockId
        CsMerchantQueryVo csMerchantQueryVo = csMerchantMapper.getCsMerchantById(csMerchantOrderQueryVo.getMerchantId());
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        String lockId = csMerchantQueryVo.getTtlLockId();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
        StringUtils.isEmpty(ttlPassword)||StringUtils.isEmpty(lockId)){
            return "请联系管理员检查智能锁的配置是否正确！";
        }

        // 获取ttlock token的redis key，若不存在则需要去取key；
        String token = (String)redisTemplate.opsForValue().get("TTLOCK_TOKEN");
        if(StringUtils.isEmpty(token)){
            String requestParam = "client_id="+clientId +
                    "&client_secret="+clientSecret +
                    "&username="+ttlUserName +
                    "&password="+ DigestUtils.md5Hex(ttlPassword);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, requestParam);
            Request request = new Request.Builder()
                    .url("https://api.ttlock.com/oauth2/token")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info(responseBody);
            JSONObject jsonObject = JSON.parseObject(responseBody);
            String errorCode = jsonObject.getString("errcode");
            // 如果有错误代码，返回错误信息
            if(StringUtils.isNotBlank(errorCode)){
                return (String) jsonObject.get("description");
            }
            token = jsonObject.getString("access_token");
            Long expires_in = jsonObject.getLong("expires_in");
            redisTemplate.opsForValue().set("TTLOCK_TOKEN",token,expires_in, TimeUnit.SECONDS);
        }

        // 获取大厅ttlock token的redis key，如果存在则开始新增key；
        // 随机6位开锁密码
        long random = Math.abs(new Random().nextLong());
        String lockKey = String.valueOf(random).substring(0, 6);

        // 拼开始时间和结束时间
        Date orderDate = csMerchantOrderQueryVo.getOrderDate();
        String[] times=csMerchantOrderQueryVo.getOrderTimerage().split("-");
        int startMin = Integer.valueOf(times[0].split(":")[0]).intValue();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(orderDate);
        startCal.set(Calendar.HOUR_OF_DAY, startMin);
        Date startDate = startCal.getTime();

        //如果下一天的有值，则结束时间按照下一天来
        Calendar endCal = Calendar.getInstance();
        if(csMerchantOrderQueryVo.getNextOrderDate()!=null){
            endCal.setTime(csMerchantOrderQueryVo.getNextOrderDate());
            times=csMerchantOrderQueryVo.getNextOrderTimerage().split("-");
            int endMin = Integer.valueOf(times[times.length-1].split(":")[0]).intValue();
            endCal.set(Calendar.HOUR_OF_DAY, endMin);
        }else{
            int endMin = Integer.valueOf(times[times.length-1].split(":")[0]).intValue();
            endCal.setTime(orderDate);
            endCal.set(Calendar.HOUR_OF_DAY, endMin);
        }

//        int endMin = Integer.valueOf(times[times.length-1].split(":")[0]).intValue();
//        Calendar endCal = Calendar.getInstance();
//        endCal.setTime(orderDate);
//        endCal.set(Calendar.HOUR_OF_DAY, endMin);
        Date endDate = endCal.getTime();

        Date nowDate = new Date();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String requestParam = "clientId=" +clientId +
                "&accessToken=" + token+
                "&lockId=" + lockId +
                "&keyboardPwd=" +lockKey +
                "&startDate=" + startDate.getTime() +
                "&endDate=" + endDate.getTime() +
                "&date="+ nowDate.getTime() +
                "&addType=2";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, requestParam);
        Request request = new Request.Builder()
                .url("https://api.ttlock.com/v3/keyboardPwd/add")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info(responseBody);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        String errorCode = jsonObject.getString("errcode");
        // 如果有错误代码，返回错误信息
        if(StringUtils.isNotBlank(errorCode)){
            return (String) jsonObject.get("description");
        }
        String keyboardPwdId = jsonObject.getString("keyboardPwdId");

        csMerchantOrderQueryParam.setKeyboardPwdId(keyboardPwdId);
        csMerchantOrderQueryParam.setKeyboardPwd(lockKey);
        rtnMessage += csMerchantOrderQueryVo.getKeyboardPwd();

        //获取茶室的锁和密码
        CsTearoomQueryVo csTearoomQueryVo = csTearoomMapper.getCsTearoomById(csMerchantOrderQueryParam.getTearoomId());
        // 判断是否有维护茶室的锁id
        if(StringUtils.isNotBlank(csTearoomQueryVo.getRttlLockId())){
            long rrandom = Math.abs(new Random().nextLong());
            String rlockKey = String.valueOf(rrandom).substring(0, 6);
            client = new OkHttpClient().newBuilder()
                    .build();
            requestParam = "clientId=" +clientId +
                    "&accessToken=" + token+
                    "&lockId=" + lockId +
                    "&keyboardPwd=" +lockKey +
                    "&startDate=" + startDate.getTime() +
                    "&endDate=" + endDate.getTime() +
                    "&date="+ nowDate.getTime() +
                    "&addType=2";
            mediaType = MediaType.parse("application/x-www-form-urlencoded");
            body = RequestBody.create(mediaType, requestParam);
            request = new Request.Builder()
                    .url("https://api.ttlock.com/v3/keyboardPwd/add")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            response = client.newCall(request).execute();
            responseBody = response.body().string();
            log.info(responseBody);
            jsonObject = JSON.parseObject(responseBody);
            errorCode = jsonObject.getString("errcode");
            // 如果有错误代码，返回错误信息
            if(StringUtils.isNotBlank(errorCode)){
                return (String) jsonObject.get("description");
            }
            String rkeyboardPwdId = jsonObject.getString("keyboardPwdId");
            csMerchantOrderQueryParam.setRkeyboardPwdId(rkeyboardPwdId);
            csMerchantOrderQueryParam.setRkeyboardPwd(rlockKey);
            rtnMessage += "\n\r茶室开锁密码：" + csMerchantOrderQueryVo.getRkeyboardPwd();
        }

        //响应正常，则将lockkey和lock的id存到订单内；
        int flag = csMerchantOrderMapper.updateLockKey(csMerchantOrderQueryParam);
        if(flag>0){
            return rtnMessage;
        }
        return "请联系管理员检查智能锁的配置是否正确！";
    }

    @Override
    public String openLock(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {

        String rtnMessage = "一键开锁成功!";

        // 通过商店id获取商店
        CsMerchantQueryVo csMerchantQueryVo = csMerchantMapper.getCsMerchantById(csMerchantOrderQueryParam.getMerchantId());

        // 商店开锁
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        String lockId = csMerchantQueryVo.getTtlLockId();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
                StringUtils.isEmpty(ttlPassword)||StringUtils.isEmpty(lockId)){
            return "请联系管理员检查智能锁的配置是否正确！";
        }

        // 获取ttlock token的redis key，若不存在则需要去取key；
        String token = (String)this.getLockToken(csMerchantQueryVo);
        if(StringUtils.isEmpty(token)){
            return "请联系管理员检查智能锁的配置是否正确！";
        }

        Date nowDate = new Date();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String requestParam = "clientId=" +clientId +
                "&accessToken=" + token+
                "&lockId=" + lockId +
                "&date="+ nowDate.getTime() ;
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.ttlock.com/v3/lock/unlock?"+requestParam)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info(responseBody);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        String errorCode = jsonObject.getString("errcode");
        // 如果有错误代码，返回错误信息
        if(StringUtils.isNotBlank(errorCode)&&!"0".equals(errorCode)){
            return (String) jsonObject.get("description");
        }
        //开锁成功，设置订单使用状态为已使用
        CsMerchantOrderQueryParam temp = new CsMerchantOrderQueryParam();
        temp.setId(csMerchantOrderQueryParam.getId());
        temp.setUsedStatus("1");
        csMerchantOrderMapper.updateUsedStatus(temp);
        return rtnMessage;
    }

    @Override
    public String openRoomLock(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        String rtnMessage = "一键开锁成功!";

        // 通过商店id获取商店
        CsMerchantQueryVo csMerchantQueryVo = csMerchantMapper.getCsMerchantById(csMerchantOrderQueryParam.getMerchantId());

        // 商店开锁
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
                StringUtils.isEmpty(ttlPassword)){
            return "请联系管理员检查智能锁的配置是否正确！";
        }

        // 获取ttlock token的redis key，若不存在则需要去取key；
        String token = (String)this.getLockToken(csMerchantQueryVo);
        if(StringUtils.isEmpty(token)){
            return "请联系管理员检查智能锁的配置是否正确！";
        }

        Date nowDate = new Date();

        // 通过茶室id获取茶室
        CsTearoomQueryVo csTearoomQueryVo = csTearoomMapper.getCsTearoomById(csMerchantOrderQueryParam.getTearoomId());
        if(StringUtils.isNotBlank(csTearoomQueryVo.getRttlLockId())) {
            nowDate = new Date();
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            String requestParam = "clientId=" + clientId +
                    "&accessToken=" + token +
                    "&lockId=" + csTearoomQueryVo.getRttlLockId() +
                    "&date=" + nowDate.getTime();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.ttlock.com/v3/lock/unlock?" + requestParam)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info(responseBody);
            JSONObject jsonObject = JSON.parseObject(responseBody);
            String errorCode = jsonObject.getString("errcode");
            // 如果有错误代码，返回错误信息
            if (StringUtils.isNotBlank(errorCode)&&!"0".equals(errorCode)) {
                return (String) jsonObject.get("description");
            }
            // 开锁成功
            //获取订单
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapper.eq("id",csMerchantOrderQueryParam.getId());
            CsMerchantOrder csMerchantOrder = csMerchantOrderService.getOne(csMerchantOrderQueryWrapper);

            String isExist = (String)redisTemplate.opsForValue().get("SY1_isExist]"+csMerchantOrder.getId());
            if(StringUtils.isEmpty(isExist)){
                List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

                // 设置声音的定时器
                String shengyintime1 = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("shengyintime1")).collect(Collectors.toList()).get(0).getConfigValue();

                redisTemplate.opsForValue().set("ORDER_SHENGYING1_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),Integer.parseInt(shengyintime1), TimeUnit.SECONDS);

            }

            return rtnMessage;
        }
        return "请联系管理员检查智能锁的配置是否正确！";
    }

    @Override
    public Boolean saveCsMerchantOrderForWX(CsMerchantOrder csMerchantOrder) throws Exception {
        // 保存茶室订单，订单保存成功后，即扣除优惠卷和会员优惠时长、金额，在支付失败、取消时，会重新删除优惠卷和优惠时长、金额的使用
        csMerchantOrder.setSourceType(0);
        csMerchantOrder.setPaymentStatus(2);
        Date now = new Date();
        LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        Date newDate=java.sql.Date.valueOf(localDate);
        csMerchantOrder.setOrderDate(csMerchantOrder.getOrderDate());
        csMerchantOrder.setOrderName(csMerchantOrder.getRoomName()+'-'+
                DateUtils.getYYYYMMDDHHMMSS(csMerchantOrder.getOrderDate())+'-'+ UUIDUtil.getUUID());
        csMerchantOrder.setUsedStatus("0");
        boolean flag = this.saveCsMerchantOrder(csMerchantOrder);

        this.orderPayRedis(null,csMerchantOrder.getId().toString());

        //如果有优惠卷，扣除优惠卷
        if(csMerchantOrder.getCouponReleasedId()!=null && csMerchantOrder.getCouponReleasedId()!=0){
            CsCouponReleasedQueryParam csCouponReleasedQueryParam = new CsCouponReleasedQueryParam();
            csCouponReleasedQueryParam.setId(csMerchantOrder.getCouponReleasedId());
            csCouponReleasedQueryParam.setIsUsed(1);
            csCouponReleasedQueryParam.setUsedTime(new Date());
            csCouponReleasedService.updateUsedStatus(csCouponReleasedQueryParam);
        }

        //如果是会员
        if(csMerchantOrder.getMembercardOrderId()!=null && csMerchantOrder.getMembercardOrderId()!=0){
            // 记录折扣金额
            CsMembercardConsum discountCsMembercardConsum = new CsMembercardConsum();
            discountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
            discountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
            //记录消费原始价格
            discountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
            discountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
            discountCsMembercardConsum.setConsumType(2);
            //优惠单价*原始总时长
            discountCsMembercardConsum.setConsumDiscountAmount(
                    DoubleUtils.subtract(csMerchantOrder.getOrderOriginPrice(),DoubleUtils.multiply(csMerchantOrder.getOrderUnitPrice(),csMerchantOrder.getOrderOriginTimenum().doubleValue())));
            discountCsMembercardConsum.setCousumDate(new Date());
            csMembercardConsumService.save(discountCsMembercardConsum);

            //如果使用优惠时长，扣除优惠时长
            if(csMerchantOrder.getOrderMbTimenum()!=null && csMerchantOrder.getOrderMbTimenum()!=0){
                CsMembercardConsum timeCsMembercardConsum = new CsMembercardConsum();
                timeCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
                timeCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
                //记录消费原始价格
                timeCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
                timeCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
                timeCsMembercardConsum.setConsumType(0);
                //优惠单价*原始总时长
                timeCsMembercardConsum.setConsumTime(csMerchantOrder.getOrderMbTimenum());
                timeCsMembercardConsum.setCousumDate(new Date());
                csMembercardConsumService.save(timeCsMembercardConsum);
            }else{
                csMerchantOrder.setOrderMbTimenum(new Double(0));
            }

            //如果使用优惠金额，扣除优惠金额
            if(csMerchantOrder.getOrderMbAmount()!=null && csMerchantOrder.getOrderMbAmount()!=0){
                // 记录折扣金额
                CsMembercardConsum amountCsMembercardConsum = new CsMembercardConsum();
                amountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
                amountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
                //记录消费原始价格
                amountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
                amountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
                amountCsMembercardConsum.setConsumType(1);
                //优惠单价*原始总时长
                amountCsMembercardConsum.setConsumAmount(csMerchantOrder.getOrderMbAmount());
                amountCsMembercardConsum.setCousumDate(new Date());
                csMembercardConsumService.save(amountCsMembercardConsum);
            }else{
                csMerchantOrder.setOrderMbAmount(new Double(0));
            }

            //更新会员卡的剩余时长和剩余金额
            if(csMerchantOrder.getOrderMbAmount()!=0 || csMerchantOrder.getOrderMbTimenum()!=0) {
                CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
                csMembercardOrderQueryParam.setId(csMerchantOrder.getMembercardOrderId());
                csMembercardOrderQueryParam.setRestDiscountPrice(csMerchantOrder.getOrderMbAmount());
                csMembercardOrderQueryParam.setRestDiscountTime(csMerchantOrder.getOrderMbTimenum().doubleValue());
                csMembercardOrderService.reduceRest(csMembercardOrderQueryParam);
            }
        }

        //如果是余额支付，则需要更新账户余额信息
        if(csMerchantOrder.getPaymentType().equals(1)){
            WxUserQueryParam wxUserQueryParam = new WxUserQueryParam();
            wxUserQueryParam.setId(csMerchantOrder.getWxuserId());
            wxUserQueryParam.setBalance(csMerchantOrder.getOrderPrice());
            wxUserService.reduceBalance(wxUserQueryParam);

            CsRechargeConsum csRechargeConsum = new CsRechargeConsum();
            csRechargeConsum.setWxuserId(csMerchantOrder.getWxuserId());
            csRechargeConsum.setCousumAmount(csMerchantOrder.getOrderPrice());
            csRechargeConsum.setCousumDate(new Date());
            csRechargeConsum.setRoomOrderId(csMerchantOrder.getId());
            csRechargeConsum.setConsumType(0);
            csRechargeConsumService.saveCsRechargeConsum(csRechargeConsum);
        }

        return flag;
    }

    public void orderPayRedis(String out_trade_no, String orderId) throws Exception{

        //设置定时提醒任务。1.查询商家存在的通知，规则，商家ID，预定时间在通知规则有效时间内
        QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
        if(StringUtils.isNotBlank(out_trade_no)){
            csMerchantOrderQueryWrapper.eq("out_trade_no",out_trade_no);
        }else{
            csMerchantOrderQueryWrapper.eq("id",orderId);
        }
        CsMerchantOrder csMerchantOrder = this.getOne(csMerchantOrderQueryWrapper);

        //删除订单关闭的定时器
        redisTemplate.delete("MERCHANT_ORDER]"+csMerchantOrder.getId());

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
        log.debug(sdf.format(startC.getTime()));

        // 设置结束时间:
        Calendar endC = Calendar.getInstance();
        // 清除所有:
        endC.clear();
        //如果下一天的有值，则结束时间按照下一天来
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

        log.info("订单结束时间:============="+sdf.format(endC.getTime()));

        CsMerchantNotifyQueryParam csMerchantNotifyQueryParam  = new CsMerchantNotifyQueryParam();
        csMerchantNotifyQueryParam.setMerchantId(csMerchantOrder.getMerchantId());
        csMerchantNotifyQueryParam.setOrderDate(csMerchantOrder.getOrderDate());
        List<CsMerchantNotifyQueryVo> notifyList = null;
        try {
            notifyList = csMerchantNotifyService.getCsMerchantNotifyPageList(csMerchantNotifyQueryParam).getRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(notifyList!=null&&notifyList.size()>0){
            // 根据通知类型，设置不同的rediskey
            notifyList.stream().forEach(cc-> {
                int notifyTime = cc.getNotifyTime();
                if(notifyTime == 0){
                    // 预订后X分钟，获取后几分钟，通过订单的创建时间+X分钟后这个时间进行提醒,其实就是支付成功后+X分钟后
                    redisTemplate.opsForValue().set("ORDER_NOTIFY]"+csMerchantOrder.getId()+"["+cc.getId(),cc.getId(),cc.getNotifyRearTime(), TimeUnit.MINUTES);
                }else if(notifyTime == 1){
                    // 消费前后X分钟
                    int fenzhong = DateUtils.differentMinute(new Date(),endC.getTime());
                    if(cc.getNotifyFrontTime()!=null&&cc.getNotifyFrontTime()!=0){
                        redisTemplate.opsForValue().set("ORDER_NOTIFY]"+csMerchantOrder.getId()+"["+cc.getId(),cc.getId(),fenzhong-cc.getNotifyFrontTime(), TimeUnit.MINUTES);
                    }else if (cc.getNotifyRearTime()!=null&&cc.getNotifyRearTime()!=0){
                        redisTemplate.opsForValue().set("ORDER_NOTIFY]"+csMerchantOrder.getId()+"["+cc.getId(),cc.getId(),fenzhong+cc.getNotifyRearTime(), TimeUnit.MINUTES);
                    }
                }

            });
        }

        //设置订单结束状态的定时器，用来进行订单使用状态的控制
        int fenzhong = DateUtils.differentMinute(new Date(),endC.getTime());
        redisTemplate.opsForValue().set("ORDER_END_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),fenzhong, TimeUnit.MINUTES);

        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        // 设置电控的定时器
        String kongkaitime = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("kongkaitime")).collect(Collectors.toList()).get(0).getConfigValue();
        int kongkaifengzhong = DateUtils.differentMinute(new Date(),startC.getTime());
        // 对于立即使用的，如果分钟小于0，则默认为1
        int kktime = kongkaifengzhong-Integer.parseInt(kongkaitime);
        if(kktime <=0){
            redisTemplate.opsForValue().set("ORDER_KONGTAI_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),10, TimeUnit.SECONDS);
        }else{
            redisTemplate.opsForValue().set("ORDER_KONGTAI_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),kktime, TimeUnit.MINUTES);
        }

        // 设置声音的定时器
        String shengyintime2 = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("shengyintime2")).collect(Collectors.toList()).get(0).getConfigValue();
        String shengyintime3 = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("shengyintime3")).collect(Collectors.toList()).get(0).getConfigValue();

        redisTemplate.opsForValue().set("ORDER_SHENGYING2_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),fenzhong-Integer.parseInt(shengyintime2), TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("ORDER_SHENGYING3_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),fenzhong-Integer.parseInt(shengyintime3), TimeUnit.MINUTES);

        //如果是续单订单，则订单使用状态直接设置为已使用
        if(StringUtils.isNotBlank(csMerchantOrder.getOriginOrderId())){
            CsMerchantOrderQueryParam temp = new CsMerchantOrderQueryParam();
            temp.setId(Long.valueOf(csMerchantOrder.getOriginOrderId()));
            temp.setUsedStatus("1");
            csMerchantOrderMapper.updateUsedStatus(temp);
        }
    }

    @Override
    public void exportList(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        Page page = setPageParam(csMerchantOrderQueryParam,OrderItem.desc("create_time"));
        IPage<CsMerchantOrderQueryVo> iPage=csMerchantOrderMapper.getCsMerchantOrderPageList(page,csMerchantOrderQueryParam);

        List<CsMerchantOrderQueryVo> list = iPage.getRecords();
        List<CsMerchantOrderExportQueryVo> exportList = new ArrayList<CsMerchantOrderExportQueryVo>();
        Iterator<CsMerchantOrderQueryVo> iter = list.iterator();
        while(iter.hasNext()){
            CsMerchantOrderExportQueryVo exportVo=new CsMerchantOrderExportQueryVo();
            BeanUtils.copyProperties(iter.next(),exportVo);
            if(exportVo.getPaymentStatus()!=null){
                switch (exportVo.getPaymentStatus()){
                    case 0:
                        exportVo.setPaymentStatusName("支付中");
                        break;
                    case 1:
                        exportVo.setPaymentStatusName("支付失败");
                        break;
                    case 2:
                        exportVo.setPaymentStatusName("支付成功");
                        break;
                    case 3:
                        exportVo.setPaymentStatusName("支付取消");
                        break;
                    case 4:
                        exportVo.setPaymentStatusName("支付失败");
                        break;
                }
            }
            if(exportVo.getPaymentType()!=null){
                switch(exportVo.getPaymentType()){
                    case 1:
                        exportVo.setPaymentTypeName("余额支付");
                        break;
                    case 2:
                        exportVo.setPaymentTypeName("微信支付");
                        break;
                }
            }
            if(exportVo.getUsedStatus()!=null){
                switch(exportVo.getUsedStatus()){
                    case "0":
                        exportVo.setUsedStatusName("未使用");
                        break;
                    case "1":
                        exportVo.setUsedStatusName("已使用");
                        break;
                    case "2":
                        exportVo.setUsedStatusName("已取消");
                        break;
                    case "3":
                        exportVo.setUsedStatusName("已完成");
                        break;
                }
            }
            exportList.add(exportVo);
        }

        // 创建一个Sheet表，并导入数据
        ExcelExport ee = new ExcelExport("订单列表",CsMerchantOrderExportQueryVo.class, ExcelField.Type.ALL);
        ee.setDataList(exportList);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        ee.write(HttpServletResponseUtil.getResponse(), "订单列表" + time + ".xlsx");
        // 清理销毁
        ee.close();
        log.debug("Export success.");

    }

    public String getLockToken(CsMerchantQueryVo csMerchantQueryVo) throws Exception {
        // 商店开锁
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
                StringUtils.isEmpty(ttlPassword)){
            return "请联系管理员检查智能锁的配置是否正确！";
        }

        // 获取ttlock token的redis key，若不存在则需要去取key；
        String token = (String)redisTemplate.opsForValue().get("TTLOCK_TOKEN");
        if(StringUtils.isEmpty(token)){
            String requestParam = "client_id="+clientId +
                    "&client_secret="+clientSecret +
                    "&username="+ttlUserName +
                    "&password="+ DigestUtils.md5Hex(ttlPassword);
            log.info(requestParam);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, requestParam);
            Request request = new Request.Builder()
                    .url("https://api.ttlock.com/oauth2/token")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info(responseBody);
            JSONObject jsonObject = JSON.parseObject(responseBody);
            String errorCode = jsonObject.getString("errcode");
            // 如果有错误代码，返回错误信息
            if(StringUtils.isNotBlank(errorCode)){
                return (String) jsonObject.get("description");
            }
            token = jsonObject.getString("access_token");
            Long expires_in = jsonObject.getLong("expires_in");
            redisTemplate.opsForValue().set("TTLOCK_TOKEN",token,expires_in, TimeUnit.SECONDS);
        }
        return token;
    }

}
