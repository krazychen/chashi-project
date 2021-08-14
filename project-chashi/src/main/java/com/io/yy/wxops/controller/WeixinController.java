package com.io.yy.wxops.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.marketing.entity.CsMembercardOrder;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.IpUtil;
import com.io.yy.util.UUIDUtil;
import com.io.yy.util.lang.DateUtils;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.wxops.utils.PayUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weixin4j.WeixinSupport;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private CsMembercardOrderService csMembercardOrderService;

    /**
     * 发起会员卡微信支付
     *
     * @param csMembercardOrder
     * @param request
     * @return
     */
    @PostMapping("cardWxPay")
    @ApiOperation(value = "发起会员卡微信支付", notes = "发起会员卡微信支付", response = ApiResult.class)
    public ApiResult<Boolean> cardWxPay(@ModelAttribute CsMembercardOrder csMembercardOrder, HttpServletRequest request) throws Exception{
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
        csMembercardOrder.setOrderDate(new Date());
        csMembercardOrder.setStartTime(csMembercardOrder.getOrderDate());
        csMembercardOrder.setEndTime(DateUtils.plusMonth(csMembercardOrder.getStartTime(),csMembercardOrder.getValidPeriod()));
        csMembercardOrder.setOrderName(csMembercardOrder.getMembercardName()+'-'+
                DateUtils.getYYYYMMDDHHMMSS(csMembercardOrder.getOrderDate())+'-'+ UUIDUtil.getUUID());
        csMembercardOrder.setOutTradeNo(UUIDUtil.getUUID());

        try {
            //生成的随机字符串
            String nonce_str = StringUtils.getRandomStringByLength(32);
            //商品名称
            String body = csMembercardOrder.getOrderName();
            //获取本机的ip地址
            String spbill_create_ip = IpUtil.getRequestIp(request);

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid",appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", csMembercardOrder.getOutTradeNo());//商户订单号
            packageParams.put("total_fee", String.valueOf(csMembercardOrder.getOrderPrice()*100));//支付金额，这边需要转成字符串类型，否则后面的签名会失败
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
                    + "<out_trade_no>" + csMembercardOrder.getOutTradeNo() + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + String.valueOf(csMembercardOrder.getOrderPrice()*100) + "</total_fee>"
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
                csMembercardOrder.setPaymentStatus(0);
                boolean flag = csMembercardOrderService.saveCsMembercardOrder(csMembercardOrder);
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
        System.out.println("接收到的报文：" + notityXml);

        Map map = PayUtil.doXMLParse(notityXml);

        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            if (PayUtil.verify(PayUtil.createLinkString(map), (String) map.get("sign"), key, "utf-8")) {
                /**此处添加自己的业务逻辑代码start**/


                /**此处添加自己的业务逻辑代码end**/

                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");

        //商户处理后同步返回给微信信息，参考：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7&index=8
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
