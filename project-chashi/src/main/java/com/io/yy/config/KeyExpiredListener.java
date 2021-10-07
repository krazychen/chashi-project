package com.io.yy.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.param.CsRechargeRecordQueryParam;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.service.CsMembercardConsumService;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.service.CsRechargeRecordService;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.service.CsMerchantOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        //过期的key
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        log.debug("redis key 过期：pattern={},channel={},key={}",new String(pattern),channel,key);

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
}

