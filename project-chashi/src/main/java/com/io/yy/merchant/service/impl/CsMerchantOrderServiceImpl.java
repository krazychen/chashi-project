package com.io.yy.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.mapper.CsMerchantMapper;
import com.io.yy.merchant.mapper.CsMerchantOrderMapper;
import com.io.yy.merchant.mapper.CsTearoomMapper;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.vo.CsMerchantOrderQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.merchant.vo.CsMerchantQueryVo;
import com.io.yy.merchant.vo.CsTearoomQueryVo;
import com.io.yy.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


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

        // 获取ttlock token的redis key，若不存在则需要去取key；
        String token = (String)redisTemplate.opsForValue().get("TTLOCK_TOKEN");
        if(StringUtils.isBlank(token)){
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

        int endMin = Integer.valueOf(times[times.length-1].split(":")[0]).intValue();
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(orderDate);
        endCal.set(Calendar.HOUR_OF_DAY, endMin);
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
        return "获取开锁密码失败，请联系管理员！";
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

        // 获取ttlock token的redis key，若不存在则需要去取key；
        String token = (String)redisTemplate.opsForValue().get("TTLOCK_TOKEN");
        if(StringUtils.isBlank(token)){
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
        if(StringUtils.isNotBlank(errorCode)){
            return (String) jsonObject.get("description");
        }else {
            // 通过茶室id获取茶室
            CsTearoomQueryVo csTearoomQueryVo = csTearoomMapper.getCsTearoomById(csMerchantOrderQueryParam.getTearoomId());
            if(StringUtils.isNotBlank(csTearoomQueryVo.getRttlLockId())){
                nowDate = new Date();
                client = new OkHttpClient().newBuilder()
                        .build();
                requestParam = "clientId=" +clientId +
                        "&accessToken=" + token+
                        "&lockId=" + csTearoomQueryVo.getRttlLockId() +
                        "&date="+ nowDate.getTime() ;
                mediaType = MediaType.parse("text/plain");
                body = RequestBody.create(mediaType, "");
                request = new Request.Builder()
                        .url("https://api.ttlock.com/v3/lock/unlock?"+requestParam)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                response = client.newCall(request).execute();
                responseBody = response.body().string();
                log.info(responseBody);
                jsonObject = JSON.parseObject(responseBody);
                errorCode = jsonObject.getString("errcode");
                // 如果有错误代码，返回错误信息
                if(StringUtils.isNotBlank(errorCode)) {
                    return (String) jsonObject.get("description");
                }else{
                    return rtnMessage;
                }
            }
        }
        return rtnMessage;
    }

}
