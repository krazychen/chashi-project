package com.io.yy.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.constant.CommonRedisKey;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.system.param.QRcCodeQueryParam;
import com.io.yy.system.vo.AccessTokenVo;
import com.io.yy.system.vo.UserInfoVo;
import com.io.yy.system.vo.WxQRCodeResult;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.util.wx.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/21
 **/
@Component
public class SignUtil {

    private static String token = "Cv0s88n7Lff8Kh7RfiiGoaaQNots1t9v";

    @Autowired
    private static RestTemplate restTemplate;

    private static RedisTemplate redisTemplate;

    @Autowired
    private static WhyySystemProperties whyySystemProperties;

    @Autowired
    public void setWhyySystemProperties(WhyySystemProperties whyySystemProperties) {
        SignUtil.whyySystemProperties = whyySystemProperties;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        SignUtil.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        SignUtil.restTemplate = restTemplate;
    }

    /**
     * ????????????????????????
     */
    public static final String QR_SCENE = "QR_SCENE";

    /**
     * ???????????????????????????
     */
    public static final String QR_STR_SCENE = "QR_STR_SCENE";

    /**
     * ????????????????????????
     */
    public static final String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";

    /**
     * ???????????????????????????
     */
    public static final String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";

    /**
     * ???????????????????????????
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * ???????????????subscribe(??????)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * ???????????????unsubscribe(????????????)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * ???????????????unsubscribe(????????????)
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";

    /**
     * ???????????????unsubscribe(????????????)
     */
    public static final String EVENT_TYPE_TEXT = "text";


    /**
     * ????????????
     *
     * @param signature ??????
     * @param timestamp ?????????
     * @param nonce     ?????????
     * @return ?????????
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String checktext = null;
        if (null != signature) {
            // ???ToKen,timestamp,nonce ???????????????
            String[] paramArr = new String[]{token, timestamp, nonce};
            Arrays.sort(paramArr);
            // ??????????????????????????????????????????
            String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                // ???????????????????????????sha1??????
                byte[] digest = md.digest(content.toString().getBytes());
                checktext = byteToStr(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        // ???????????????????????????signature????????????
        return checktext != null ? checktext.equals(signature.toUpperCase()) : false;
    }

    /**
     * ????????????????????????16???????????????
     *
     * @param byteArrays ????????????
     * @return ?????????
     */
    private static String byteToStr(byte[] byteArrays) {
        String str = "";
        for (int i = 0; i < byteArrays.length; i++) {
            str += byteToHexStr(byteArrays[i]);
        }
        return str;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param myByte ??????
     * @return ?????????
     */
    private static String byteToHexStr(byte myByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tampArr = new char[2];
        tampArr[0] = Digit[(myByte >>> 4) & 0X0F];
        tampArr[1] = Digit[myByte & 0X0F];
        String str = new String(tampArr);
        return str;
    }

    public static WxQRCodeResult getQRCode(QRcCodeQueryParam qRcCodeQueryParam) {
        if(whyySystemProperties.isEnableHwXueyuanWx()){
            String accessToken = getAccessToken();
            Setting setting = new Setting("wxProperties/wxWeb.properties");
            String qrCodeUrl = setting.getStr("qrCodeUrl") + "?access_token=" + accessToken;
            HttpEntity<QRcCodeQueryParam> requestEntity = new HttpEntity<>(qRcCodeQueryParam);
            HttpMethod method = HttpMethod.POST;
            ResponseEntity<WxQRCodeResult> response = restTemplate.exchange(qrCodeUrl, method, requestEntity, WxQRCodeResult.class);
            WxQRCodeResult wxQRCodeResult = response.getBody();
            if(StrUtil.isBlank(wxQRCodeResult.getTicket())){
                accessToken = getAccessTokenRealTime();
                qrCodeUrl = setting.getStr("qrCodeUrl") + "?access_token=" + accessToken;
                response = restTemplate.exchange(qrCodeUrl, method, requestEntity, WxQRCodeResult.class);
            }
            return response.getBody();
        }
        return new WxQRCodeResult();
    }

    public static String getAccessToken() {
        //??????redis???????????????????????????????????????????????????????????????????????????redis??????????????????????????????????????????????????????redis
        AccessTokenVo accessTokenVo = (AccessTokenVo) redisTemplate.opsForHash().get(CommonRedisKey.WX_ACCESS_TOKEN, "access_token");
        Long date = new Date().getTime();
        Boolean flag = false;
        //??????token????????????
        if (ObjectUtil.isNotNull(accessTokenVo) && ObjectUtil.isNotNull(accessTokenVo.getInvalid_time())) {
            //????????????5?????????????????????
            if (date <= ( accessTokenVo.getInvalid_time() - (1000 * 60 * 5))) {
                return accessTokenVo.getAccess_token();
            }
        }
        Setting setting = new Setting("wxProperties/wxWeb.properties");
        String accessTokenUrl = setting.getStr("accessTokenUrl") + "?grant_type=client_credential&appid=" + setting.getStr("appid") + "&secret=" + setting.getStr("appSecret");
        accessTokenVo = restTemplate.getForObject(accessTokenUrl, AccessTokenVo.class);
        if(StrUtil.isNotBlank(accessTokenVo.getErrcode()) && !accessTokenVo.getErrcode().equals("0")){
            throw new BusinessException("access_token????????????");
        }
        String expires_in = accessTokenVo.getExpires_in();
        Long invalidTime = Long.parseLong(expires_in);
        invalidTime = date+ (invalidTime*1000);
        accessTokenVo.setInvalid_time(invalidTime);
        Map<String, AccessTokenVo> map = MapUtil.newHashMap();
        map.put("access_token", accessTokenVo);
        redisTemplate.opsForHash().putAll(CommonRedisKey.WX_ACCESS_TOKEN, map);

        return accessTokenVo.getAccess_token();
    }

    /**
     * ????????????access_token??????????????????????????????
     * @return
     */
    public static String getAccessTokenRealTime() {
        Long date = new Date().getTime();
        Setting setting = new Setting("wxProperties/wxWeb.properties");
        String accessTokenUrl = setting.getStr("accessTokenUrl") + "?grant_type=client_credential&appid=" + setting.getStr("appid") + "&secret=" + setting.getStr("appSecret");
        AccessTokenVo accessTokenVo = restTemplate.getForObject(accessTokenUrl, AccessTokenVo.class);
        if(StrUtil.isNotBlank(accessTokenVo.getErrcode()) && !accessTokenVo.getErrcode().equals("0")){
            throw new BusinessException("access_token????????????");
        }
        String expires_in = accessTokenVo.getExpires_in();
        Long invalidTime = Long.parseLong(expires_in);
        invalidTime = date+ (invalidTime*1000);
        accessTokenVo.setInvalid_time(invalidTime);
        Map<String, AccessTokenVo> map = MapUtil.newHashMap();
        map.put("access_token", accessTokenVo);
        redisTemplate.opsForHash().putAll(CommonRedisKey.WX_ACCESS_TOKEN, map);

        return accessTokenVo.getAccess_token();
    }

    // ????????????????????????????????????????????????
    public static String inputStream2String(InputStream inputStream, String charset) throws Exception {
        // ????????????????????????
        InputStreamReader reader = new InputStreamReader(inputStream, charset);
        // ??????????????????????????????
        char[] data = new char[512];
        int dataSize = 0;
        // ????????????
        StringBuilder stringBuilder = new StringBuilder();
        while ((dataSize = reader.read(data)) != -1) {
            stringBuilder.append(data, 0, dataSize);
        }
        return stringBuilder.toString();
    }

    /**
     * ????????????????????????xml
     *
     * @param in
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String decrypt(InputStream in, String timestamp, String nonce) {
        String result = "";
        try {
            if (in.available() > 0) {
                String mingwen = SignUtil.inputStream2String(in, "UTF-8");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                StringReader sr = new StringReader(mingwen);
                InputSource is = new InputSource(sr);
                Document document = db.parse(is);
                Element root = document.getDocumentElement();
                NodeList nodelist1 = root.getElementsByTagName("Encrypt");
                String encrypt = nodelist1.item(0).getTextContent();
                String msgSignature = "";
                String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
                String fromXML = String.format(format, encrypt);
                Setting setting = new Setting("wxProperties/wxWeb.properties");
                // ?????????????????????????????????????????????
                WXBizMsgCrypt pc = new WXBizMsgCrypt(setting.get("token"), setting.get("encodingAESKey"), setting.get("appid"));
                result = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
            }
        } catch (Exception e) {
            result = "";
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

    /**
     * xmljin
     *
     * @return
     */
    public static String encryption(String xml, String nonce, String timestamp) {
        // ?????????????????????
        String mingwen = "";
        try {
            Setting setting = new Setting("wxProperties/wxWeb.properties");
            String encodingAesKey = setting.get("encodingAESKey");
            String token = setting.get("token");
            String appId = setting.get("appid");
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            mingwen = pc.encryptMsg(xml, timestamp, nonce);
        } catch (Exception e) {

        }
        return mingwen;
    }

    /**
     * xml?????????map
     *
     * @param xml
     * @return
     */
    public static Map<String, String> xmlToMap(String xml) throws Exception{
        Map<String, String> map = MapUtil.newHashMap();
            org.dom4j.Document doc = DocumentHelper.parseText(xml);
            org.dom4j.Element root = doc.getRootElement();
            List<org.dom4j.Element> list = root.elements();
            for (org.dom4j.Element e : list) {
                map.put(e.getName(), e.getText());
            }
        return map;
    }

    /**
     * xml??????bean????????????
     *
     * @param resultXml
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String resultXml, Class clazz) {
        // XStream?????????????????????????????????????????????????????????
        XStream stream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(stream);
        stream.allowTypes(new Class[]{clazz});
        stream.processAnnotations(new Class[]{clazz});
        stream.setMode(XStream.NO_REFERENCES);
        stream.alias("xml", clazz);
        return (T) stream.fromXML(resultXml);
    }

    /**
     * bean???????????????xml????????????
     *
     * @param object
     * @return
     */
    public static String beanToXml(Object object) {
        XStream xStream = getMyXStream();
        xStream.alias("xml", object.getClass());
        xStream.processAnnotations(object.getClass());
        String xml = xStream.toXML(object);
        if (!StringUtils.isEmpty(xml)) {
            return xml;
        } else {
            return null;
        }
    }


    //xstream??????,bean???xml????????????![CDATA[]]
    public static XStream getMyXStream() {
        return new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // ?????????xml???????????????CDATA??????
                    boolean cdata = true;

                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
    }

    /**
     * ???????????????
     */
    public static UserInfoVo getUserInfo(String openid) {
        Setting setting = new Setting("wxProperties/wxWeb.properties");
        UserInfoVo userInfoVo = null;
        String access_token = getAccessToken();
        String userInfoUrl = setting.get("userInfoUrl") + "?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        userInfoVo = restTemplate.getForObject(userInfoUrl, UserInfoVo.class);
        return userInfoVo;
    }
}