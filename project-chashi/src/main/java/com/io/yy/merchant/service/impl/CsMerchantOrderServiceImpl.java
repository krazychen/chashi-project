package com.io.yy.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
 * ???????????????????????? ???????????????
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
     * ??????tearoomid????????????????????????????????????????????????????????????????????????????????????????????????","????????????
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
     * ??????tearoomid???????????????????????????????????????????????????????????????????????????????????????????????????????????????","????????????
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    @Override
    public String getTimeRangeCleanForWx(CsMerchantOrderQueryParam csMerchantOrderQueryParam) {
        List<String> list = csMerchantOrderMapper.getTimeRangeCleanForWx(csMerchantOrderQueryParam);
        String timeRangeCleans = "";
        for(int i=0 ; i<list.size();i++){
            if(i != list.size()-1){
                timeRangeCleans += list.get(i)+",";
            }else{
                timeRangeCleans += list.get(i);
            }
        }
        return timeRangeCleans;
    }

    @Override
    public Long getOrderByCurrent(CsMerchantOrderQueryParam csMerchantOrderQueryParam) {
        return csMerchantOrderMapper.getOrderByCurrent(csMerchantOrderQueryParam);
    }

    /**
     * ??????????????????
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
        String rtnMessage = "?????????????????????";
        // ?????????????????????????????????????????????key????????????????????????????????????????????????????????????
        CsMerchantOrderQueryVo csMerchantOrderQueryVo = this.getCsMerchantOrderById(csMerchantOrderQueryParam.getId());
        if(StringUtils.isNotBlank(csMerchantOrderQueryVo.getKeyboardPwdId())&&StringUtils.isNotBlank((csMerchantOrderQueryVo.getKeyboardPwd()))){
            rtnMessage += csMerchantOrderQueryVo.getKeyboardPwd();
            if(StringUtils.isNotBlank(csMerchantOrderQueryVo.getRkeyboardPwdId())&&StringUtils.isNotBlank((csMerchantOrderQueryVo.getRkeyboardPwd()))){
                rtnMessage += "\n\r?????????????????????" + csMerchantOrderQueryVo.getRkeyboardPwd();
            }
            return rtnMessage;
        }

        // ???merchant???clientId???lockId
        CsMerchantQueryVo csMerchantQueryVo = csMerchantMapper.getCsMerchantById(csMerchantOrderQueryVo.getMerchantId());
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        String lockId = csMerchantQueryVo.getTtlLockId();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
        StringUtils.isEmpty(ttlPassword)||StringUtils.isEmpty(lockId)){
            return "?????????????????????????????????????????????????????????";
        }

        // ??????ttlock token???redis key??????????????????????????????key???
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
            // ??????????????????????????????????????????
            if(StringUtils.isNotBlank(errorCode)){
                return (String) jsonObject.get("description");
            }
            token = jsonObject.getString("access_token");
            Long expires_in = jsonObject.getLong("expires_in");
            redisTemplate.opsForValue().set("TTLOCK_TOKEN",token,expires_in, TimeUnit.SECONDS);
        }

        // ????????????ttlock token???redis key??????????????????????????????key???
        // ??????6???????????????
        long random = Math.abs(new Random().nextLong());
        String lockKey = String.valueOf(random).substring(0, 6);

        // ??????????????????????????????
        Date orderDate = csMerchantOrderQueryVo.getOrderDate();
        String[] times=csMerchantOrderQueryVo.getOrderTimerage().split("-");
        int startMin = Integer.valueOf(times[0].split(":")[0]).intValue();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(orderDate);
        startCal.set(Calendar.HOUR_OF_DAY, startMin);
        Date startDate = startCal.getTime();

        //????????????????????????????????????????????????????????????
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
        // ??????????????????????????????????????????
        if(StringUtils.isNotBlank(errorCode)){
            return (String) jsonObject.get("description");
        }
        String keyboardPwdId = jsonObject.getString("keyboardPwdId");

        csMerchantOrderQueryParam.setKeyboardPwdId(keyboardPwdId);
        csMerchantOrderQueryParam.setKeyboardPwd(lockKey);
        rtnMessage += csMerchantOrderQueryVo.getKeyboardPwd();

        //???????????????????????????
        CsTearoomQueryVo csTearoomQueryVo = csTearoomMapper.getCsTearoomById(csMerchantOrderQueryParam.getTearoomId());
        // ?????????????????????????????????id
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
            // ??????????????????????????????????????????
            if(StringUtils.isNotBlank(errorCode)){
                return (String) jsonObject.get("description");
            }
            String rkeyboardPwdId = jsonObject.getString("keyboardPwdId");
            csMerchantOrderQueryParam.setRkeyboardPwdId(rkeyboardPwdId);
            csMerchantOrderQueryParam.setRkeyboardPwd(rlockKey);
            rtnMessage += "\n\r?????????????????????" + csMerchantOrderQueryVo.getRkeyboardPwd();
        }

        //?????????????????????lockkey???lock???id??????????????????
        int flag = csMerchantOrderMapper.updateLockKey(csMerchantOrderQueryParam);
        if(flag>0){
            return rtnMessage;
        }
        return "?????????????????????????????????????????????????????????";
    }

    @Override
    public String openLock(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {

        String rtnMessage = "??????????????????!";

        // ????????????id????????????
        CsMerchantQueryVo csMerchantQueryVo = csMerchantMapper.getCsMerchantById(csMerchantOrderQueryParam.getMerchantId());

        // ????????????
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        String lockId = csMerchantQueryVo.getTtlLockId();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
                StringUtils.isEmpty(ttlPassword)||StringUtils.isEmpty(lockId)){
            return "?????????????????????????????????????????????????????????";
        }

        // ??????ttlock token???redis key??????????????????????????????key???
        String token = (String)this.getLockToken(csMerchantQueryVo);
        if(StringUtils.isEmpty(token)){
            return "?????????????????????????????????????????????????????????";
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
        // ??????????????????????????????????????????
        if(StringUtils.isNotBlank(errorCode)&&!"0".equals(errorCode)){
            return (String) jsonObject.get("description");
        }
        //???????????????????????????????????????????????????
        CsMerchantOrderQueryParam temp = new CsMerchantOrderQueryParam();
        temp.setId(csMerchantOrderQueryParam.getId());
        temp.setUsedStatus("1");
        csMerchantOrderMapper.updateUsedStatus(temp);
        //????????????????????????????????????????????????
        try {
            CsTearoomQueryVo csTearoomQueryVo = csTearoomMapper.getCsTearoomById(csMerchantOrderQueryParam.getTearoomId());
            if (StringUtils.isNotEmpty(csMerchantQueryVo.getKkClientId()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkRedirectUri())
                    && StringUtils.isNotEmpty(csMerchantQueryVo.getKkPassword()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkAppSecret())
                    && StringUtils.isNotEmpty(csMerchantQueryVo.getKkProjectCode()) && StringUtils.isNotEmpty(csMerchantQueryVo.getKkUname())) {
                if (StringUtils.isNotEmpty(csTearoomQueryVo.getKkMac()) && StringUtils.isNotEmpty(csTearoomQueryVo.getKkOcSwitch())) {
                    String code = getCode(csMerchantQueryVo);
                    String doorToken = getToken(csMerchantQueryVo, code);

                    String addrs = csTearoomQueryVo.getKkOcSwitch();
                    String[] addrA = addrs.split(",");
                    for(int i =0;i<addrA.length;i++){
                        boolean isopen=get_switch_status(csMerchantQueryVo,csTearoomQueryVo,doorToken,addrA[i]);
                        if(!isopen){
                            PUT_BOX_CONTROL_Switch(csMerchantQueryVo, csTearoomQueryVo, doorToken, "open");
                        }
                    }
//                    if(!isopen){
//                        PUT_BOX_CONTROL_Switch(csMerchantQueryVo, csTearoomQueryVo, doorToken, "open");
//                    }
                } else {
                    log.error(csMerchantOrderQueryParam.getId() + ":?????????????????????????????????");
                }
            } else {
                log.error(csMerchantOrderQueryParam.getId() + ":?????????????????????????????????");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnMessage;
    }

    @Override
    public String openRoomLock(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        String rtnMessage = "??????????????????!";

        // ????????????id????????????
        CsMerchantQueryVo csMerchantQueryVo = csMerchantMapper.getCsMerchantById(csMerchantOrderQueryParam.getMerchantId());

        // ????????????
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
                StringUtils.isEmpty(ttlPassword)){
            return "?????????????????????????????????????????????????????????";
        }

        // ??????ttlock token???redis key??????????????????????????????key???
        String token = (String)this.getLockToken(csMerchantQueryVo);
        if(StringUtils.isEmpty(token)){
            return "?????????????????????????????????????????????????????????";
        }

        Date nowDate = new Date();

        // ????????????id????????????
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
            // ??????????????????????????????????????????
            if (StringUtils.isNotBlank(errorCode)&&!"0".equals(errorCode)) {
                return (String) jsonObject.get("description");
            }
            // ????????????
            //????????????
            QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
            csMerchantOrderQueryWrapper.eq("id",csMerchantOrderQueryParam.getId());
            CsMerchantOrder csMerchantOrder = csMerchantOrderService.getOne(csMerchantOrderQueryWrapper);

            String isExist = (String) redisTemplate.opsForValue().get("SY1_isExist]"+csMerchantOrder.getId());
            if(StringUtils.isEmpty(isExist)){
                List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

                // ????????????????????????
                String shengyintime1 = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("shengyintime1")).collect(Collectors.toList()).get(0).getConfigValue();

                redisTemplate.opsForValue().set("ORDER_SHENGYING1_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),Integer.parseInt(shengyintime1), TimeUnit.SECONDS);

            }

            return rtnMessage;
        }
        return "?????????????????????????????????????????????????????????";
    }

    @Override
    public Boolean saveCsMerchantOrderForWX(CsMerchantOrder csMerchantOrder) throws Exception {
        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

        //????????????????????????????????????
        if(csMerchantOrder.getCouponReleasedId()!=null && csMerchantOrder.getCouponReleasedId()!=0){
            CsCouponReleasedQueryParam csCouponReleasedQueryParam = new CsCouponReleasedQueryParam();
            csCouponReleasedQueryParam.setId(csMerchantOrder.getCouponReleasedId());
            csCouponReleasedQueryParam.setIsUsed(1);
            csCouponReleasedQueryParam.setUsedTime(new Date());
            csCouponReleasedService.updateUsedStatus(csCouponReleasedQueryParam);
        }

        //???????????????
        if(csMerchantOrder.getMembercardOrderId()!=null && csMerchantOrder.getMembercardOrderId()!=0){
            // ??????????????????
            CsMembercardConsum discountCsMembercardConsum = new CsMembercardConsum();
            discountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
            discountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
            //????????????????????????
            discountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
            discountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
            discountCsMembercardConsum.setConsumType(2);
            //????????????*???????????????
            discountCsMembercardConsum.setConsumDiscountAmount(
                    DoubleUtils.subtract(csMerchantOrder.getOrderOriginPrice(),DoubleUtils.multiply(csMerchantOrder.getOrderUnitPrice(),csMerchantOrder.getOrderOriginTimenum().doubleValue())));
            discountCsMembercardConsum.setCousumDate(new Date());
            csMembercardConsumService.save(discountCsMembercardConsum);

            //?????????????????????????????????????????????
            if(csMerchantOrder.getOrderMbTimenum()!=null && csMerchantOrder.getOrderMbTimenum()!=0){
                CsMembercardConsum timeCsMembercardConsum = new CsMembercardConsum();
                timeCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
                timeCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
                //????????????????????????
                timeCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
                timeCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
                timeCsMembercardConsum.setConsumType(0);
                //????????????*???????????????
                timeCsMembercardConsum.setConsumTime(csMerchantOrder.getOrderMbTimenum());
                timeCsMembercardConsum.setCousumDate(new Date());
                csMembercardConsumService.save(timeCsMembercardConsum);
            }else{
                csMerchantOrder.setOrderMbTimenum(new Double(0));
            }

            //?????????????????????????????????????????????
            if(csMerchantOrder.getOrderMbAmount()!=null && csMerchantOrder.getOrderMbAmount()!=0){
                // ??????????????????
                CsMembercardConsum amountCsMembercardConsum = new CsMembercardConsum();
                amountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
                amountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
                //????????????????????????
                amountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
                amountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
                amountCsMembercardConsum.setConsumType(1);
                //????????????*???????????????
                amountCsMembercardConsum.setConsumAmount(csMerchantOrder.getOrderMbAmount());
                amountCsMembercardConsum.setCousumDate(new Date());
                csMembercardConsumService.save(amountCsMembercardConsum);
            }else{
                csMerchantOrder.setOrderMbAmount(new Double(0));
            }

            //?????????????????????????????????????????????
            if(csMerchantOrder.getOrderMbAmount()!=0 || csMerchantOrder.getOrderMbTimenum()!=0) {
                CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
                csMembercardOrderQueryParam.setId(csMerchantOrder.getMembercardOrderId());
                csMembercardOrderQueryParam.setRestDiscountPrice(csMerchantOrder.getOrderMbAmount());
                csMembercardOrderQueryParam.setRestDiscountTime(csMerchantOrder.getOrderMbTimenum().doubleValue());
                csMembercardOrderService.reduceRest(csMembercardOrderQueryParam);
            }
        }

        //?????????????????????????????????????????????????????????
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

        //???????????????????????????1.?????????????????????????????????????????????ID?????????????????????????????????????????????
        QueryWrapper<CsMerchantOrder> csMerchantOrderQueryWrapper=new QueryWrapper<CsMerchantOrder>();
        if(StringUtils.isNotBlank(out_trade_no)){
            csMerchantOrderQueryWrapper.eq("out_trade_no",out_trade_no);
        }else{
            csMerchantOrderQueryWrapper.eq("id",orderId);
        }
        CsMerchantOrder csMerchantOrder = this.getOne(csMerchantOrderQueryWrapper);

        //??????????????????????????????
        redisTemplate.delete("MERCHANT_ORDER]"+csMerchantOrder.getId());

        //???????????????????????????????????????
        String[] timeRangeArr = csMerchantOrder.getOrderTimerage().split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // ??????????????????:
        Calendar startC = Calendar.getInstance();
        // ????????????:
        startC.clear();
        startC.setTime(csMerchantOrder.getOrderDate());
        String[] startTimeRangeArr = timeRangeArr[0].split(":");
        startC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeRangeArr[0]));
        startC.set(Calendar.MINUTE, Integer.parseInt(startTimeRangeArr[1]));
        startC.set(Calendar.SECOND, 00);
        log.debug(sdf.format(startC.getTime()));

        // ??????????????????:
        Calendar endC = Calendar.getInstance();
        // ????????????:
        endC.clear();
        //????????????????????????????????????????????????????????????
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

        log.info("??????????????????:============="+sdf.format(endC.getTime()));

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
            // ????????????????????????????????????rediskey
            notifyList.stream().forEach(cc-> {
                int notifyTime = cc.getNotifyTime();
                if(notifyTime == 0){
                    // ?????????X?????????????????????????????????????????????????????????+X?????????????????????????????????,???????????????????????????+X?????????
                    redisTemplate.opsForValue().set("ORDER_NOTIFY]"+csMerchantOrder.getId()+"["+cc.getId(),cc.getId(),cc.getNotifyRearTime(), TimeUnit.MINUTES);
                }else if(notifyTime == 1){
                    // ????????????X??????
                    int fenzhong = DateUtils.differentMinute(new Date(),endC.getTime());
                    if(cc.getNotifyFrontTime()!=null&&cc.getNotifyFrontTime()!=0){
                        redisTemplate.opsForValue().set("ORDER_NOTIFY]"+csMerchantOrder.getId()+"["+cc.getId(),cc.getId(),fenzhong-cc.getNotifyFrontTime(), TimeUnit.MINUTES);
                    }else if (cc.getNotifyRearTime()!=null&&cc.getNotifyRearTime()!=0){
                        redisTemplate.opsForValue().set("ORDER_NOTIFY]"+csMerchantOrder.getId()+"["+cc.getId(),cc.getId(),fenzhong+cc.getNotifyRearTime(), TimeUnit.MINUTES);
                    }
                }

            });
        }

        //??????????????????????????????????????????????????????????????????????????????
        int fenzhong = DateUtils.differentMinute(new Date(),endC.getTime())+1;
        redisTemplate.opsForValue().set("ORDER_END_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),fenzhong, TimeUnit.MINUTES);

        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        // ????????????????????????
        String kongkaitime = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("kongkaitime")).collect(Collectors.toList()).get(0).getConfigValue();
        String lijikongkaitime = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("lijikongkaitime")).collect(Collectors.toList()).get(0).getConfigValue();
        int kongkaifengzhong = DateUtils.differentMinute(new Date(),startC.getTime());
        // ??????????????????????????????????????????0???????????????1
        int kktime = kongkaifengzhong-Integer.parseInt(kongkaitime);
        if(kktime <=0){
            //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            // ????????????????????????????????????1???????????????????????????????????????2?????????????????????????????????10S
            int kktime2 = kongkaifengzhong-Integer.parseInt(lijikongkaitime);
            if(kktime2 <=0){
                redisTemplate.opsForValue().set("ORDER_KONGTAI_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),10, TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set("ORDER_KONGTAI_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),kktime2, TimeUnit.MINUTES);
            }
        }else{
            redisTemplate.opsForValue().set("ORDER_KONGTAI_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),kktime, TimeUnit.MINUTES);
        }

        // ????????????????????????
        String shengyintime2 = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("shengyintime2")).collect(Collectors.toList()).get(0).getConfigValue();
        String shengyintime3 = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("shengyintime3")).collect(Collectors.toList()).get(0).getConfigValue();

        redisTemplate.opsForValue().set("ORDER_SHENGYING2_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),fenzhong-Integer.parseInt(shengyintime2), TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("ORDER_SHENGYING3_USED]"+csMerchantOrder.getId(),csMerchantOrder.getId(),fenzhong-Integer.parseInt(shengyintime3), TimeUnit.MINUTES);

        //?????????????????????????????????????????????????????????????????????
        if(StringUtils.isNotBlank(csMerchantOrder.getOriginOrderId())){
            CsMerchantOrderQueryParam temp = new CsMerchantOrderQueryParam();
            temp.setId(Long.valueOf(csMerchantOrder.getMerchantId()));
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
                        exportVo.setPaymentStatusName("?????????");
                        break;
                    case 1:
                        exportVo.setPaymentStatusName("????????????");
                        break;
                    case 2:
                        exportVo.setPaymentStatusName("????????????");
                        break;
                    case 3:
                        exportVo.setPaymentStatusName("????????????");
                        break;
                    case 4:
                        exportVo.setPaymentStatusName("????????????");
                        break;
                }
            }
            if(exportVo.getPaymentType()!=null){
                switch(exportVo.getPaymentType()){
                    case 1:
                        exportVo.setPaymentTypeName("????????????");
                        break;
                    case 2:
                        exportVo.setPaymentTypeName("????????????");
                        break;
                }
            }
            if(exportVo.getUsedStatus()!=null){
                switch(exportVo.getUsedStatus()){
                    case "0":
                        exportVo.setUsedStatusName("?????????");
                        break;
                    case "1":
                        exportVo.setUsedStatusName("?????????");
                        break;
                    case "2":
                        exportVo.setUsedStatusName("?????????");
                        break;
                    case "3":
                        exportVo.setUsedStatusName("?????????");
                        break;
                }
            }
            exportList.add(exportVo);
        }

        // ????????????Sheet?????????????????????
        ExcelExport ee = new ExcelExport("????????????",CsMerchantOrderExportQueryVo.class, ExcelField.Type.ALL);
        ee.setDataList(exportList);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        ee.write(HttpServletResponseUtil.getResponse(), "????????????" + time + ".xlsx");
        // ????????????
        ee.close();
        log.debug("Export success.");

    }

    public String getLockToken(CsMerchantQueryVo csMerchantQueryVo) throws Exception {
        // ????????????
        String clientId = csMerchantQueryVo.getTtlClientId();
        String clientSecret = csMerchantQueryVo.getTtlClientSecret();
        String ttlUserName = csMerchantQueryVo.getTtlUsername();
        String ttlPassword = csMerchantQueryVo.getTtlPassword();
        if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)|| StringUtils.isEmpty(ttlUserName)||
                StringUtils.isEmpty(ttlPassword)){
            return "?????????????????????????????????????????????????????????";
        }

        // ??????ttlock token???redis key??????????????????????????????key???
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
            // ??????????????????????????????????????????
            if(StringUtils.isNotBlank(errorCode)){
                return (String) jsonObject.get("description");
            }
            token = jsonObject.getString("access_token");
            Long expires_in = jsonObject.getLong("expires_in");
            redisTemplate.opsForValue().set("TTLOCK_TOKEN",token,expires_in, TimeUnit.SECONDS);
        }
        return token;
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

        // ?????????????????????redis key??????????????????????????????key???
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

    private boolean get_switch_status(CsMerchantQueryVo csMerchantQueryVo, CsTearoomQueryVo csTearoomQueryVo, String token, String addr) throws Exception{
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("client_id", csMerchantQueryVo.getKkClientId());
        packageParams.put("method", "GET_BOX_CHANNELS_OC");
        packageParams.put("access_token", token);
        packageParams.put("timestamp", sdf.format(nowDate));
        packageParams.put("projectCode", csMerchantQueryVo.getKkProjectCode());
        packageParams.put("mac", csTearoomQueryVo.getKkMac());
        packageParams.put("addr", addr);

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
        // ??????key?????????????????????????????????
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
        // ??????key?????????????????????????????????
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
