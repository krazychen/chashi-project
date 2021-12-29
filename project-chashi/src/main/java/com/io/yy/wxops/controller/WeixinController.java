package com.io.yy.wxops.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.common.api.ApiResult;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.entity.CsMembercardOrder;
import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.marketing.entity.CsRechargeRecord;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.param.CsRechargeRecordQueryParam;
import com.io.yy.marketing.service.*;
import com.io.yy.marketing.vo.CsRechargeRecordQueryVo;
import com.io.yy.merchant.entity.CsMerchantNotify;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.param.CsMerchantNotifyQueryParam;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.service.CsMerchantNotifyService;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.vo.CsMerchantNotifyQueryVo;
import com.io.yy.merchant.vo.CsMerchantOrderQueryVo;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.IpUtil;
import com.io.yy.util.UUIDUtil;
import com.io.yy.util.lang.DateUtils;
import com.io.yy.util.lang.DoubleUtils;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.service.WxUserService;
import com.io.yy.wxops.utils.ClientCustomSSL;
import com.io.yy.wxops.utils.PayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.weixin4j.WeixinSupport;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author kris
 * @ClassName WeixinController
 * @Description TODO
 * @date 08/11
 */
@RequestMapping("/weixin")
@RestController
public class WeixinController extends WeixinSupport {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String grant_type = "authorization_code";

    @Autowired
    private WhyySystemProperties whyySystemProperties;

    @Autowired
    private CsMembercardOrderService csMembercardOrderService;

    @Autowired
    private CsRechargeRecordService csRechargeRecordService;

    @Autowired
    private CsCouponReleasedService csCouponReleasedService;

    @Autowired
    private CsMembercardConsumService csMembercardConsumService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private CsRechargeConsumService csRechargeConsumService;

    @Autowired
    private CsMerchantOrderService csMerchantOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发起会员卡微信支付
     *
     * @param csMembercardOrder
     * @param request
     * @return
     */
    @RequestMapping("/cardWxPay")
    @ApiOperation(value = "发起会员卡微信支付", notes = "发起会员卡微信支付", response = ApiResult.class)
    public ApiResult<Boolean> cardWxPay(@ModelAttribute CsMembercardOrder csMembercardOrder, HttpServletRequest request) throws Exception {
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        String appid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList()).get(0).getConfigValue();
        String appSecret = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appSecret")).collect(Collectors.toList()).get(0).getConfigValue();
        String loginUrl = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("loginUrl")).collect(Collectors.toList()).get(0).getConfigValue();
        String mch_id = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("mch_id")).collect(Collectors.toList()).get(0).getConfigValue();
        String key = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("key")).collect(Collectors.toList()).get(0).getConfigValue();
        String pay_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("pay_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String notify_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notify_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String sign_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("sign_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String trade_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("trade_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String openid = csMembercardOrder.getOpenid();

        csMembercardOrder.setSourceType(1);
        csMembercardOrder.setPaymentType(1);
        csMembercardOrder.setOrderDate(new Date());
        csMembercardOrder.setStartTime(csMembercardOrder.getOrderDate());
        csMembercardOrder.setEndTime(DateUtils.plusMonth(csMembercardOrder.getStartTime(), csMembercardOrder.getValidPeriod()));
        String outTradeNo = "card_" + UUIDUtil.getUUIDBits(24);
        csMembercardOrder.setOrderName(csMembercardOrder.getMembercardName() + '-' +
                DateUtils.getYYYYMMDDHHMMSS(csMembercardOrder.getOrderDate()) + '-' + outTradeNo);
        csMembercardOrder.setOutTradeNo(outTradeNo);

        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //商品名称
            String body = csMembercardOrder.getOrderName();
            //获取本机的ip地址
            String spbill_create_ip = IpUtil.getRequestIp(request);

            Double moneyFen = csMembercardOrder.getOrderPrice() * 100;

            String money = String.valueOf(moneyFen.intValue());

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", csMembercardOrder.getOutTradeNo());//商户订单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", notify_url);
            packageParams.put("trade_type", trade_type);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + csMembercardOrder.getOutTradeNo() + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + trade_type + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            logger.info("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(pay_url, "POST", xml);

            logger.info("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if (!StringUtils.equals(return_code, "SUCCESS")) {
                return ApiResult.fail((String) map.get("return_msg"));
            } else {
//            if (return_code == "SUCCESS" || return_code.equals(return_code)) {
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=" + sign_type + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);

                //更新订单信息
                csMembercardOrder.setPaymentStatus(0);
                boolean flag = csMembercardOrderService.saveCsMembercardOrder(csMembercardOrder);
                // 设置redis，10分钟后自动设置交易状态为关闭4
                if(flag){
                    redisTemplate.opsForValue().set("CARD_ORDER]"+csMembercardOrder.getOutTradeNo(),csMembercardOrder.getOutTradeNo(),10, TimeUnit.MINUTES);
                }else{
                    throw new Exception("生成微信支付订单失败！");
                }
                //业务逻辑代码
            }

            response.put("appid", appid);
            response.put("outTradeNo", csMembercardOrder.getOutTradeNo());

            return ApiResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.fail("发起失败");
        }
    }

    /**
     * 取消会员卡微信支付
     *
     * @param csMembercardOrder
     * @return
     */
    @RequestMapping("/cancelCardWxPay")
    @ApiOperation(value = "取消会员卡微信支付", notes = "取消会员卡微信支付", response = ApiResult.class)
    public ApiResult<Boolean> cancelCardWxPay(@ModelAttribute CsMembercardOrder csMembercardOrder) throws Exception {
       //根据outTradeNo 设置对应的订单的paymentstatus为4,取消支付
        CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
        csMembercardOrderQueryParam.setOutTradeNo(csMembercardOrder.getOutTradeNo());
        csMembercardOrderQueryParam.setPaymentStatus(3);
        boolean flag= csMembercardOrderService.updatePaymentStatus(csMembercardOrderQueryParam);
        if(flag) {
            redisTemplate.delete("CARD_ORDER_"+csMembercardOrder.getOutTradeNo());
            return ApiResult.ok("取消支付成功！");
        }else{
            return ApiResult.fail("取消支付失败！");
        }
    }

    /**
     * 会员卡微信支付失败
     *
     * @param csMembercardOrder
     * @return
     */
    @RequestMapping("/failCardWxPay")
    @ApiOperation(value = "会员卡微信支付失败", notes = "会员卡微信支付失败", response = ApiResult.class)
    public ApiResult<Boolean> failCardWxPay(@ModelAttribute CsMembercardOrder csMembercardOrder) throws Exception {
        //根据outTradeNo 设置对应的订单的paymentstatus为1,支付失败
        CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
        csMembercardOrderQueryParam.setOutTradeNo(csMembercardOrder.getOutTradeNo());
        csMembercardOrderQueryParam.setPaymentStatus(4);
        csMembercardOrderQueryParam.setPaymentMsg(csMembercardOrder.getPaymentMsg());
        boolean flag= csMembercardOrderService.updatePaymentStatus(csMembercardOrderQueryParam);
        if(flag) {
            redisTemplate.delete("CARD_ORDER_"+csMembercardOrder.getOutTradeNo());
            return ApiResult.ok("支付失败设置成功！");
        }else{
            return ApiResult.fail("支付失败设置失败！");
        }
    }

    /**
     * 发起充值微信支付
     *
     * @param csRechargeRecord
     * @param request
     * @return
     */
    @RequestMapping("/rechargeWxPay")
    @ApiOperation(value = "发起充值微信支付", notes = "发起充值微信支付", response = ApiResult.class)
    public ApiResult<Boolean> rechargeWxPay(@ModelAttribute CsRechargeRecord csRechargeRecord, HttpServletRequest request) throws Exception{
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        String appid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList()).get(0).getConfigValue();
        String appSecret = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appSecret")).collect(Collectors.toList()).get(0).getConfigValue();
        String loginUrl = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("loginUrl")).collect(Collectors.toList()).get(0).getConfigValue();
        String mch_id = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("mch_id")).collect(Collectors.toList()).get(0).getConfigValue();
        String key = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("key")).collect(Collectors.toList()).get(0).getConfigValue();
        String pay_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("pay_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String notify_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notify_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String sign_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("sign_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String trade_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("trade_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String openid = csRechargeRecord.getOpenid();

        csRechargeRecord.setPaymentStatus(0);
        csRechargeRecord.setSourceType(1);
        csRechargeRecord.setOrderDate(new Date());
        csRechargeRecord.setOrderName("rech-"+csRechargeRecord.getRechargeAmount()+DateUtils.getYYYYMMDDHHMMSS(csRechargeRecord.getOrderDate())+'-'+ UUIDUtil.getUUID());
        csRechargeRecord.setRechargeFinal(csRechargeRecord.getRechargeAmount()+csRechargeRecord.getRechargeGived());
        csRechargeRecord.setIntegral(csRechargeRecord.getRechargeAmount().intValue());
        String outTradeNo = "rech_"+UUIDUtil.getUUIDBits(24);
        csRechargeRecord.setOutTradeNo(outTradeNo);

        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //商品名称
            String body = csRechargeRecord.getOrderName();
            //获取本机的ip地址
            String spbill_create_ip = IpUtil.getRequestIp(request);

            Double moneyFen = csRechargeRecord.getRechargeAmount()*100;

            String money = String.valueOf(moneyFen.intValue());

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid",appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", csRechargeRecord.getOutTradeNo());//商户订单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url",notify_url);
            packageParams.put("trade_type", trade_type);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + csRechargeRecord.getOutTradeNo() + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + trade_type + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            logger.info("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(pay_url, "POST", xml);

            logger.info("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if (!StringUtils.equals(return_code, "SUCCESS")){
                return ApiResult.fail((String) map.get("return_msg"));
            }else{
//            if (return_code == "SUCCESS" || return_code.equals(return_code)) {
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=" + sign_type + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);

                //更新订单信息
                boolean flag = csRechargeRecordService.saveCsRechargeRecord(csRechargeRecord);
                if(flag){
                    redisTemplate.opsForValue().set("RECHARGE_ORDER]"+csRechargeRecord.getOutTradeNo(),csRechargeRecord.getOutTradeNo(),10, TimeUnit.MINUTES);
                }else{
                    throw new Exception("生成微信支付订单失败！");
                }
                //业务逻辑代码
            }

            response.put("appid", appid);
            response.put("outTradeNo", csRechargeRecord.getOutTradeNo());

            return ApiResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.fail("发起失败");
        }
    }

    /**
     * 取消充值微信支付
     *
     * @param csRechargeRecord
     * @return
     */
    @RequestMapping("/cancelRechargeWxPay")
    @ApiOperation(value = "取消充值微信支付", notes = "取消充值微信支付", response = ApiResult.class)
    public ApiResult<Boolean> cancelRechargeWxPay(@ModelAttribute CsRechargeRecord csRechargeRecord) throws Exception {
        CsRechargeRecordQueryParam csRechargeRecordQueryParam = new CsRechargeRecordQueryParam();
        csRechargeRecordQueryParam.setOutTradeNo(csRechargeRecord.getOutTradeNo());
        csRechargeRecordQueryParam.setPaymentStatus(3);
        boolean flag=csRechargeRecordService.updatePaymentStatus(csRechargeRecordQueryParam);
        if(flag) {
            redisTemplate.delete("RECHARGE_ORDER_"+csRechargeRecord.getOutTradeNo());
            return ApiResult.ok("取消支付成功！");
        }else{
            return ApiResult.fail("取消支付失败！");
        }
    }

    /**
     * 充值微信支付失败
     *
     * @param csRechargeRecord
     * @return
     */
    @RequestMapping("/failRechargeWxPay")
    @ApiOperation(value = "充值微信支付失败", notes = "充值微信支付失败", response = ApiResult.class)
    public ApiResult<Boolean> failRechargeWxPay(@ModelAttribute CsRechargeRecord csRechargeRecord) throws Exception {
        CsRechargeRecordQueryParam csRechargeRecordQueryParam = new CsRechargeRecordQueryParam();
        csRechargeRecordQueryParam.setOutTradeNo(csRechargeRecord.getOutTradeNo());
        csRechargeRecordQueryParam.setPaymentStatus(4);
        csRechargeRecordQueryParam.setPaymentMsg(csRechargeRecord.getPaymentMsg());
        boolean flag=csRechargeRecordService.updatePaymentStatus(csRechargeRecordQueryParam);
        if(flag) {
            redisTemplate.delete("RECHARGE_ORDER_"+csRechargeRecord.getOutTradeNo());
            return ApiResult.ok("支付失败设置成功！");
        }else{
            return ApiResult.fail("支付失败设置失败！");
        }
    }

    /**
     * 发起订单微信支付
     *
     * @param csMerchantOrder
     * @param request
     * @return
     */
    @RequestMapping("/orderWxPay")
    @ApiOperation(value = "发起订单微信支付", notes = "发起订单微信支付", response = ApiResult.class)
    public ApiResult<Boolean> orderWxPay(@ModelAttribute CsMerchantOrder csMerchantOrder, HttpServletRequest request) throws Exception{
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        String appid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList()).get(0).getConfigValue();
        String appSecret = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appSecret")).collect(Collectors.toList()).get(0).getConfigValue();
        String loginUrl = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("loginUrl")).collect(Collectors.toList()).get(0).getConfigValue();
        String mch_id = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("mch_id")).collect(Collectors.toList()).get(0).getConfigValue();
        String key = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("key")).collect(Collectors.toList()).get(0).getConfigValue();
        String pay_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("pay_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String notify_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notify_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String sign_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("sign_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String trade_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("trade_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String openid = csMerchantOrder.getOpenid();

        // 保存茶室订单，订单保存成功后，即扣除优惠卷和会员优惠时长、金额，在支付失败、取消时，会重新删除优惠卷和优惠时长、金额的使用
        csMerchantOrder.setSourceType(1);
        csMerchantOrder.setPaymentType(2);
        csMerchantOrder.setPaymentStatus(0);
        Date now = new Date();
        LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        Date newDate=java.sql.Date.valueOf(localDate);
        csMerchantOrder.setOrderDate(csMerchantOrder.getOrderDate());
        csMerchantOrder.setOrderName(csMerchantOrder.getRoomName()+'-'+
                DateUtils.getYYYYMMDDHHMMSS(csMerchantOrder.getOrderDate())+'-'+ UUIDUtil.getUUID());
        csMerchantOrder.setUsedStatus("0");

        String outTradeNo = "order_"+UUIDUtil.getUUIDBits(24);
        csMerchantOrder.setOutTradeNo(outTradeNo);

        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //商品名称
            String body = csMerchantOrder.getOrderName();
            //获取本机的ip地址
            String spbill_create_ip = IpUtil.getRequestIp(request);

            csMerchantOrder.setOrderPrice(new BigDecimal(csMerchantOrder.getOrderPrice()).doubleValue());
            Double moneyFen = DoubleUtils.multiply(csMerchantOrder.getOrderPrice(),Double.valueOf("100"));

            String money = String.valueOf(moneyFen.intValue());

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid",appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", csMerchantOrder.getOutTradeNo());//商户订单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url",notify_url);
            packageParams.put("trade_type", trade_type);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + csMerchantOrder.getOutTradeNo() + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + trade_type + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            logger.info("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(pay_url, "POST", xml);

            logger.info("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if (!StringUtils.equals(return_code, "SUCCESS")){
                return ApiResult.fail((String) map.get("return_msg"));
            }else{
//            if (return_code == "SUCCESS" || return_code.equals(return_code)) {
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=" + sign_type + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);

                //更新订单信息
                boolean flag = csMerchantOrderService.saveCsMerchantOrder(csMerchantOrder);

                if(flag){
                    redisTemplate.opsForValue().set("MERCHANT_ORDER]"+csMerchantOrder.getId(),csMerchantOrder.getId(),10, TimeUnit.MINUTES);

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
                }else{
                    throw new Exception("生成微信支付订单失败！");
                }

                //业务逻辑代码
            }

            response.put("appid", appid);
            response.put("outTradeNo", csMerchantOrder.getOutTradeNo());
            response.put("id",csMerchantOrder.getId());

            return ApiResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.fail("发起失败");
        }
    }

    /**
     * 已经有订单，发起支付
     *
     * @param csMerchantOrder
     * @param request
     * @return
     */
    @RequestMapping("/orderReWxPay")
    @ApiOperation(value = "已有订单发起微信支付", notes = "发起订单微信支付", response = ApiResult.class)
    public ApiResult<Boolean> orderReWxPay(@RequestBody CsMerchantOrder csMerchantOrder, HttpServletRequest request) throws Exception{
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        String appid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList()).get(0).getConfigValue();
        String appSecret = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appSecret")).collect(Collectors.toList()).get(0).getConfigValue();
        String loginUrl = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("loginUrl")).collect(Collectors.toList()).get(0).getConfigValue();
        String mch_id = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("mch_id")).collect(Collectors.toList()).get(0).getConfigValue();
        String key = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("key")).collect(Collectors.toList()).get(0).getConfigValue();
        String pay_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("pay_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String notify_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notify_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String sign_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("sign_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String trade_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("trade_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String openid = csMerchantOrder.getOpenid();

        // 这些都在订单内，不需要再赋值，怕漏先注释
//        csMerchantOrder.setSourceType(1);
//        csMerchantOrder.setPaymentType(2);
//        csMerchantOrder.setPaymentStatus(0);
//        Date now = new Date();
//        LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        Date newDate=java.sql.Date.valueOf(localDate);
//        csMerchantOrder.setOrderDate(newDate);
//        csMerchantOrder.setOrderName(csMerchantOrder.getRoomName()+'-'+
//                DateUtils.getYYYYMMDDHHMMSS(csMerchantOrder.getOrderDate())+'-'+ UUIDUtil.getUUID());
//        csMerchantOrder.setUsedStatus(0);
//
//        String outTradeNo = "order_"+UUIDUtil.getUUIDBits(24);
//        csMerchantOrder.setOutTradeNo(outTradeNo);

        // 需要加个是否订单已经关闭的控制 ****

        CsMerchantOrder csMerchantOrder1=csMerchantOrderService.getById(csMerchantOrder.getId());
        if(csMerchantOrder1.getPaymentStatus().equals(4)){
            return ApiResult.fail("订单已关闭，请重新发起订单");
        }

        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //商品名称
            String body = csMerchantOrder.getOrderName();
            //获取本机的ip地址
            String spbill_create_ip = IpUtil.getRequestIp(request);

            Double moneyFen = DoubleUtils.multiply(csMerchantOrder.getOrderPrice(),Double.valueOf("100"));

            String money = String.valueOf(moneyFen.intValue());

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid",appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", csMerchantOrder.getOutTradeNo());//商户订单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url",notify_url);
            packageParams.put("trade_type", trade_type);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + csMerchantOrder.getOutTradeNo() + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + trade_type + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            logger.info("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(pay_url, "POST", xml);

            logger.info("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if (!StringUtils.equals(return_code, "SUCCESS")){
                return ApiResult.fail((String) map.get("return_msg"));
            }else{
//            if (return_code == "SUCCESS" || return_code.equals(return_code)) {
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=" + sign_type + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);

                // 这些都在订单内，不需要再赋值，怕漏先注释
//                boolean flag = csMerchantOrderService.saveCsMerchantOrder(csMerchantOrder);
//
//                //如果有优惠卷，扣除优惠卷
//                if(csMerchantOrder.getCouponReleasedId()!=null && csMerchantOrder.getCouponReleasedId()!=0){
//                    CsCouponReleasedQueryParam csCouponReleasedQueryParam = new CsCouponReleasedQueryParam();
//                    csCouponReleasedQueryParam.setId(csMerchantOrder.getCouponReleasedId());
//                    csCouponReleasedQueryParam.setIsUsed(1);
//                    csCouponReleasedQueryParam.setUsedTime(new Date());
//                    csCouponReleasedService.updateUsedStatus(csCouponReleasedQueryParam);
//                }
//
//                //如果是会员
//                if(csMerchantOrder.getMembercardOrderId()!=null && csMerchantOrder.getMembercardOrderId()!=0){
//                    // 记录折扣金额
//                    CsMembercardConsum discountCsMembercardConsum = new CsMembercardConsum();
//                    discountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
//                    discountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
//                    //记录消费原始价格
//                    discountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
//                    discountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
//                    discountCsMembercardConsum.setConsumType(2);
//                    //优惠单价*原始总时长
//                    discountCsMembercardConsum.setConsumDiscountAmount(
//                            DoubleUtils.subtract(csMerchantOrder.getOrderOriginPrice(),DoubleUtils.multiply(csMerchantOrder.getOrderUnitPrice(),csMerchantOrder.getOrderOriginTimenum().doubleValue())));
//                    discountCsMembercardConsum.setCousumDate(new Date());
//                    csMembercardConsumService.save(discountCsMembercardConsum);
//
//                    //如果使用优惠时长，扣除优惠时长
//                    if(csMerchantOrder.getOrderMbTimenum()!=null && csMerchantOrder.getOrderMbTimenum()!=0){
//                        CsMembercardConsum timeCsMembercardConsum = new CsMembercardConsum();
//                        timeCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
//                        timeCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
//                        //记录消费原始价格
//                        timeCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
//                        timeCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
//                        timeCsMembercardConsum.setConsumType(0);
//                        //优惠单价*原始总时长
//                        timeCsMembercardConsum.setConsumTime(csMerchantOrder.getOrderMbTimenum());
//                        timeCsMembercardConsum.setCousumDate(new Date());
//                        csMembercardConsumService.save(timeCsMembercardConsum);
//                    }else{
//                        csMerchantOrder.setOrderMbTimenum(0);
//                    }
//
//                    //如果使用优惠金额，扣除优惠金额
//                    if(csMerchantOrder.getOrderMbAmount()!=null && csMerchantOrder.getOrderMbAmount()!=0){
//                        // 记录折扣金额
//                        CsMembercardConsum amountCsMembercardConsum = new CsMembercardConsum();
//                        amountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
//                        amountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
//                        //记录消费原始价格
//                        amountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
//                        amountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
//                        amountCsMembercardConsum.setConsumType(1);
//                        //优惠单价*原始总时长
//                        amountCsMembercardConsum.setConsumAmount(csMerchantOrder.getOrderMbAmount());
//                        amountCsMembercardConsum.setCousumDate(new Date());
//                        csMembercardConsumService.save(amountCsMembercardConsum);
//                    }else{
//                        csMerchantOrder.setOrderMbAmount(new Double(0));
//                    }
//
//                    //更新会员卡的剩余时长和剩余金额
//                    if(csMerchantOrder.getOrderMbAmount()!=0 || csMerchantOrder.getOrderMbTimenum()!=0) {
//                        CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
//                        csMembercardOrderQueryParam.setId(csMerchantOrder.getMembercardOrderId());
//                        csMembercardOrderQueryParam.setRestDiscountPrice(csMerchantOrder.getOrderMbAmount());
//                        csMembercardOrderQueryParam.setRestDiscountTime(csMerchantOrder.getOrderMbTimenum().doubleValue());
//                        csMembercardOrderService.reduceRest(csMembercardOrderQueryParam);
//                    }
//                }
                //业务逻辑代码
            }

            response.put("appid", appid);
            response.put("outTradeNo", csMerchantOrder.getOutTradeNo());
            response.put("id",csMerchantOrder.getId());

            return ApiResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.fail("发起失败");
        }
    }

    /**
     * 取消订单微信支付，适用余额支付和微信支付,需要传入id
     *
     * @param csMerchantOrder
     * @return
     */
    @RequestMapping("/cancelOrderWxPay")
    @ApiOperation(value = "取消订单微信支付", notes = "取消订单微信支付", response = ApiResult.class)
    public ApiResult<Boolean> cancelOrderWxPay(@ModelAttribute CsMerchantOrder csMerchantOrder) throws Exception {
        //设置订单为取消支付
        CsMerchantOrderQueryParam csMerchantOrderQueryParam = new CsMerchantOrderQueryParam();
        csMerchantOrderQueryParam.setId(csMerchantOrder.getId());
        csMerchantOrderQueryParam.setPaymentStatus(3);
        boolean flag = orderFailCommonOP(csMerchantOrderQueryParam,csMerchantOrder);

        if(flag) {
            redisTemplate.delete("MERCHANT_ORDER_"+csMerchantOrder.getId());
            return ApiResult.ok("取消支付成功！");
        }else{
            return ApiResult.fail("取消支付失败！");
        }
    }


    /**
     * 订单微信支付失败,适用余额支付和微信支付,需要传入id
     *
     * @param csMerchantOrder
     * @return
     */
    @RequestMapping("/failOrderWxPay")
    @ApiOperation(value = "订单微信支付失败", notes = "订单微信支付失败", response = ApiResult.class)
    public ApiResult<Boolean> failOrderWxPay(@ModelAttribute CsMerchantOrder csMerchantOrder) throws Exception {
        //设置订单为取消失败
        CsMerchantOrderQueryParam csMerchantOrderQueryParam = new CsMerchantOrderQueryParam();
        csMerchantOrderQueryParam.setId(csMerchantOrder.getId());
        csMerchantOrderQueryParam.setPaymentStatus(1);
        csMerchantOrderQueryParam.setPaymentMsg(csMerchantOrder.getPaymentMsg());
        boolean flag = orderFailCommonOP(csMerchantOrderQueryParam,csMerchantOrder);

        if(flag) {
            redisTemplate.delete("MERCHANT_ORDER_"+csMerchantOrder.getId());
            return ApiResult.ok("支付失败设置成功！");
        }else{
            return ApiResult.fail("支付失败设置失败！");
        }
    }


    /**
     * 订单微信支付退款,适用余额支付和微信支付,需要传入id
     *
     * @param csMerchantOrder
     * @return
     */
    @RequestMapping("/refundOrderWxPay")
    @ApiOperation(value = "订单微信支付退款", notes = "订单微信支付退款", response = ApiResult.class)
    public ApiResult<Boolean> refundOrderWxPay(@ModelAttribute CsMerchantOrder csMerchantOrder) throws Exception {

        String return_code = null;
        //判断订单是余额支付 1 还是微信支付 2, 如果是微信支付，则调用微信退款，否则就直接进行反向操作
        String outRefundNo = "";
        if(csMerchantOrder.getPaymentType()!=null && csMerchantOrder.getPaymentType().equals(2)){
            // 获取微信配置
            List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

            String appid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList()).get(0).getConfigValue();
            String mch_id = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("mch_id")).collect(Collectors.toList()).get(0).getConfigValue();
            String key = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("key")).collect(Collectors.toList()).get(0).getConfigValue();
            String sub_mch_id = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("sub_mch_id")).collect(Collectors.toList()).get(0).getConfigValue();
            String refuse_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("refund_url")).collect(Collectors.toList()).get(0).getConfigValue();
            String SSLCERT_PATH = whyySystemProperties.getRefundUrl();
            String refund_ssl_password = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("refund_ssl_password")).collect(Collectors.toList()).get(0).getConfigValue();

            outRefundNo = "reorder_"+UUIDUtil.getUUIDBits(24);
            csMerchantOrder.setOutRefundNo(outRefundNo);

            Double moneyFen = DoubleUtils.multiply(csMerchantOrder.getOrderPrice(),Double.valueOf("100"));
            String money = String.valueOf(moneyFen.intValue());

            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid",appid);
            packageParams.put("mch_id", mch_id);
//            packageParams.put("sub_mch_id", sub_mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("out_trade_no", csMerchantOrder.getOutTradeNo());//商户订单号
            packageParams.put("out_refund_no", csMerchantOrder.getOutRefundNo());//商户退款单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("refund_fee", money);//申请退款金额
            packageParams.put("refund_desc", "申请退款");

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<mch_id>" + mch_id + "</mch_id>"
//                    + "<sub_mch_id>" + sub_mch_id + "</sub_mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<out_refund_no>" + csMerchantOrder.getOutRefundNo() + "</out_refund_no>"
                    + "<out_trade_no>" + csMerchantOrder.getOutTradeNo() + "</out_trade_no>"
                    + "<refund_fee>" + money + "</refund_fee>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<refund_desc>" + "申请退款" + "</refund_desc>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            logger.info("调试模式_退款接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
//            String result = PayUtil.httpRequest(refuse_url, "POST", xml);
            String result = ClientCustomSSL.doRefund(refuse_url,xml,SSLCERT_PATH,refund_ssl_password);

            logger.info("调试模式_退款接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            return_code = (String) map.get("return_code");//返回状态码

            if (!StringUtils.equals(return_code, "SUCCESS")){
                return ApiResult.fail((String) map.get("return_msg"));
            }
        }

        //设置订单为取消支付
        CsMerchantOrderQueryParam csMerchantOrderQueryParam = new CsMerchantOrderQueryParam();
        csMerchantOrderQueryParam.setId(csMerchantOrder.getId());
        csMerchantOrderQueryParam.setPaymentStatus(5);
        csMerchantOrderQueryParam.setRefundDate(new Date());
        boolean flag = orderFailCommonOP(csMerchantOrderQueryParam,csMerchantOrder);

        if(flag) {
            // 退款成功，删除定时器
            redisTemplate.delete("ORDER_NOTIFY]"+csMerchantOrder.getId());
            redisTemplate.delete("ORDER_END_USED]"+csMerchantOrder.getId());
            redisTemplate.delete("ORDER_KONGTAI_USED]"+csMerchantOrder.getId());
            redisTemplate.delete("ORDER_SHENGYING2_USED]"+csMerchantOrder.getId());
            redisTemplate.delete("ORDER_SHENGYING3_USED]"+csMerchantOrder.getId());
            return ApiResult.ok("支付退款设置成功！");
        }else{
            return ApiResult.fail("支付退款设置失败！");
        }
    }

    public boolean orderFailCommonOP(CsMerchantOrderQueryParam csMerchantOrderQueryParam, CsMerchantOrder csMerchantOrder){

        boolean flag=csMerchantOrderService.updatePaymentStatus(csMerchantOrderQueryParam);

        //获取数据库的csMerchantOrder对象
        CsMerchantOrder newCsMerchantOrder=csMerchantOrderService.getById(csMerchantOrder.getId());
        //判断是否有优惠卷，有的话将优惠卷设置为未使用；
        if(newCsMerchantOrder.getCouponReleasedId()!=null && newCsMerchantOrder.getCouponReleasedId()!=0){
            CsCouponReleasedQueryParam csCouponReleasedQueryParam = new CsCouponReleasedQueryParam();
            csCouponReleasedQueryParam.setId(newCsMerchantOrder.getCouponReleasedId());
            csCouponReleasedQueryParam.setIsUsed(0);
            csCouponReleasedQueryParam.setUsedTime(null);
            flag = csCouponReleasedService.updateUsedStatus(csCouponReleasedQueryParam);
        }

        //判断是否有会员卡，有的话删除会员卡折扣、优惠数据，并更新会员卡的剩余优惠时长和金额
        if(newCsMerchantOrder.getMembercardOrderId()!=null && newCsMerchantOrder.getMembercardOrderId()!=0){
            QueryWrapper<CsMembercardConsum> csMembercardConsumQueryWrapper=new QueryWrapper<CsMembercardConsum>();
            csMembercardConsumQueryWrapper.eq("room_order_id",newCsMerchantOrder.getId());
            List<CsMembercardConsum> csMembercardConsumList=csMembercardConsumService.list(csMembercardConsumQueryWrapper);
            csMembercardConsumList.stream().forEach(cc-> {
                try {
                    csMembercardConsumService.deleteCsMembercardConsum(cc.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //更新会员卡的剩余时长和剩余金额
            if(newCsMerchantOrder.getOrderMbAmount()!=null && newCsMerchantOrder.getOrderMbAmount()!=0
                    || newCsMerchantOrder.getOrderMbTimenum()!=null && newCsMerchantOrder.getOrderMbTimenum()!=0) {
                CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
                csMembercardOrderQueryParam.setId(newCsMerchantOrder.getMembercardOrderId());
                csMembercardOrderQueryParam.setRestDiscountPrice(newCsMerchantOrder.getOrderMbAmount());
                csMembercardOrderQueryParam.setRestDiscountTime(newCsMerchantOrder.getOrderMbTimenum().doubleValue());
                flag = csMembercardOrderService.addRest(csMembercardOrderQueryParam);
            }
        }
        //更新余额和积分
        //如果是余额支付，则需要更新账户余额信息
        if(newCsMerchantOrder.getPaymentType().equals(1)){
            WxUserQueryParam wxUserQueryParam = new WxUserQueryParam();
            wxUserQueryParam.setId(newCsMerchantOrder.getWxuserId());
            wxUserQueryParam.setBalance(newCsMerchantOrder.getOrderPrice());
            wxUserQueryParam.setIntegral(0);
            wxUserService.updateBalanceAIntegral(wxUserQueryParam);

            CsRechargeConsum csRechargeConsum = csRechargeConsumService.getOne(new QueryWrapper<CsRechargeConsum>().eq("room_order_id", csMerchantOrder.getId()));
            csRechargeConsum.setStatus("0");
            try {
                csRechargeConsumService.updateCsRechargeConsum(csRechargeConsum);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    /**
     * 发起微信支付
     *
     * @param openid
     * @param request
     * @return
     */
    @RequestMapping("wxPay")
    public ApiResult<Boolean> wxPay(String openid, HttpServletRequest request) {
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();

        String appid = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appid")).collect(Collectors.toList()).get(0).getConfigValue();
        String appSecret = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("appSecret")).collect(Collectors.toList()).get(0).getConfigValue();
        String loginUrl = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("loginUrl")).collect(Collectors.toList()).get(0).getConfigValue();
        String mch_id = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("mch_id")).collect(Collectors.toList()).get(0).getConfigValue();
        String key = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("key")).collect(Collectors.toList()).get(0).getConfigValue();
        String pay_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("pay_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String notify_url = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("notify_url")).collect(Collectors.toList()).get(0).getConfigValue();
        String sign_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("sign_type")).collect(Collectors.toList()).get(0).getConfigValue();
        String trade_type = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("trade_type")).collect(Collectors.toList()).get(0).getConfigValue();

        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //商品名称
            String body = "测试商品名称";
            //获取本机的ip地址
            String spbill_create_ip = IpUtil.getRequestIp(request);

            String orderNo = "123456789";
            String money = "1";//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid",appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo);//商户订单号
            packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url",notify_url);
            packageParams.put("trade_type", trade_type);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtil.paraFilter(packageParams);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + orderNo + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + trade_type + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(pay_url, "POST", xml);

            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if (return_code == "SUCCESS" || return_code.equals(return_code)) {
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=" + sign_type + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                logger.info("=======================第二次签名：" + paySign + "=====================");

                response.put("paySign", paySign);

                //更新订单信息
                //业务逻辑代码
            }

            response.put("appid", appid);

            return ApiResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.fail("发起失败");
        }
    }

    /**
     * 微信服务器通知支付结果API
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/wxNotify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取微信配置
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();
        String key = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals("key")).collect(Collectors.toList()).get(0).getConfigValue();
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        logger.debug("接收到的报文：" + notityXml);

        Map map = PayUtil.doXMLParse(notityXml);

        if(map ==null){
            logger.debug("微信回调返回空，请检查微信支付！！");
            //商户处理后同步返回给微信信息，参考：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7&index=8
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        }
        String returnCode = (String) map.get("return_code");
        String outTradeNo = (String)map.get("out_trade_no");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            if (PayUtil.verify(PayUtil.createLinkString(map), (String) map.get("sign"), key, "utf-8")) {
                logger.debug("微信回调开始业务代码处理:"+outTradeNo);

                /**此处添加自己的业务逻辑代码start**/

                // 如果是会员卡订单,更新订单的付款状态
                if(StringUtils.isNotBlank(outTradeNo)&&outTradeNo.indexOf("card_")!=-1){
                    CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
                    csMembercardOrderQueryParam.setOutTradeNo(outTradeNo);
                    csMembercardOrderQueryParam.setPaymentStatus(2);
                    csMembercardOrderService.updatePaymentStatus(csMembercardOrderQueryParam);
                }
                // 如果是充值订单,更新充值订单的付款状态
                if(StringUtils.isNotBlank(outTradeNo)&&outTradeNo.indexOf("rech_")!=-1){
                    CsRechargeRecordQueryParam csRechargeRecordQueryParam = new CsRechargeRecordQueryParam();
                    csRechargeRecordQueryParam.setOutTradeNo(outTradeNo);
                    csRechargeRecordQueryParam.setPaymentStatus(2);
                    boolean flag=csRechargeRecordService.updatePaymentStatus(csRechargeRecordQueryParam);
                    if(flag){
                        //更新用户余额和积分，需要先获取用户ID
                        CsRechargeRecordQueryVo csRechargeRecordQueryVo = csRechargeRecordService.getCsRechargeRecordByOutTradeNo(outTradeNo);
                        WxUserQueryParam wxUserQueryParam = new WxUserQueryParam();
                        wxUserQueryParam.setId(csRechargeRecordQueryVo.getWxuserId());
                        wxUserQueryParam.setBalance(csRechargeRecordQueryVo.getRechargeFinal());
                        wxUserQueryParam.setIntegral(csRechargeRecordQueryVo.getIntegral());
                        wxUserService.updateBalanceAIntegral(wxUserQueryParam);
                    }
                }

                // 如果是茶室订单,更新茶室订单的付款状态
                if(StringUtils.isNotBlank(outTradeNo)&&outTradeNo.indexOf("order_")!=-1) {
                    CsMerchantOrderQueryParam csMerchantOrderQueryParam = new CsMerchantOrderQueryParam();
                    csMerchantOrderQueryParam.setOutTradeNo(outTradeNo);
                    csMerchantOrderQueryParam.setPaymentStatus(2);
                    boolean flag=csMerchantOrderService.updatePaymentStatus(csMerchantOrderQueryParam);
                    if(flag){
                        //更新用户积分，需要先获取用户ID
                        CsRechargeRecordQueryVo csRechargeRecordQueryVo = csRechargeRecordService.getCsRechargeRecordByOutTradeNo(outTradeNo);
                        if(csRechargeRecordQueryVo!=null){
                            WxUserQueryParam wxUserQueryParam = new WxUserQueryParam();
                            wxUserQueryParam.setId(csRechargeRecordQueryVo.getWxuserId());
                            wxUserQueryParam.setIntegral(csRechargeRecordQueryVo.getIntegral());
                            wxUserService.updateBalanceAIntegral(wxUserQueryParam);
                        }

                        csMerchantOrderService.orderPayRedis(outTradeNo,null);
                    }
                }

                /**此处添加自己的业务逻辑代码end**/

                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            /**此处添加自己的业务逻辑代码start**/
            // 如果是会员卡订单,更新订单的付款状态
            if(StringUtils.isNotBlank(outTradeNo)&&outTradeNo.indexOf("card_")!=-1){
                CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
                csMembercardOrderQueryParam.setOutTradeNo(outTradeNo);
                csMembercardOrderQueryParam.setPaymentStatus(1);
                csMembercardOrderService.updatePaymentStatus(csMembercardOrderQueryParam);
            }
            // 如果是充值订单,更新充值订单的付款状态
            if(StringUtils.isNotBlank(outTradeNo)&&outTradeNo.indexOf("rech_")!=-1) {
                CsRechargeRecordQueryParam csRechargeRecordQueryParam = new CsRechargeRecordQueryParam();
                csRechargeRecordQueryParam.setOutTradeNo(outTradeNo);
                csRechargeRecordQueryParam.setPaymentStatus(1);
                boolean flag = csRechargeRecordService.updatePaymentStatus(csRechargeRecordQueryParam);
            }
            // 如果是茶室订单,更新茶室订单的付款状态
            if(StringUtils.isNotBlank(outTradeNo)&&outTradeNo.indexOf("order_")!=-1) {
                CsMerchantOrderQueryParam csMerchantOrderQueryParam = new CsMerchantOrderQueryParam();
                csMerchantOrderQueryParam.setOutTradeNo(outTradeNo);
                csMerchantOrderQueryParam.setPaymentStatus(1);
                boolean flag=csMerchantOrderService.updatePaymentStatus(csMerchantOrderQueryParam);
            }
            /**此处添加自己的业务逻辑代码end**/
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        logger.info(resXml);
        logger.info("微信支付回调数据结束");

        //商户处理后同步返回给微信信息，参考：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7&index=8
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
