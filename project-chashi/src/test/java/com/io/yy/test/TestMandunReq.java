package com.io.yy.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.io.yy.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.weixin4j.util.MD5;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class TestMandunReq {

    public static void main(String[] args) throws Exception{

        TestMandunReq tmr = new TestMandunReq();
        String token = tmr.getToken(tmr.getCode());
//        tmr.GET_BOXES(token);
//        tmr.GET_BOX_CHANNELS_OC(token);
        tmr.PUT_BOX_CONTROL_Switch(token,"close");
//        tmr.GET_BOX_CHANNELS_OC(token);
//        log.info(tmr.getToken(tmr.getCode()));

    }

    private String getCode() throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "response_type=code&client_id=O000009991&redirect_uri=http://open.snd02.com/demo.jsp&uname=rjgxcs01&passwd=gxcs888");
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

    private String getToken(String code) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String client_secret = MD5("O000009991" + "authorization_code" + "http://open.snd02.com/demo.jsp" + code + "CB6BAB4164ABD5236079F9A6CFEB9CE5");
        log.info(client_secret);
        RequestBody body = RequestBody.create(mediaType, "client_id=O000009991" +
                "&client_secret=" +client_secret+
                "&grant_type=authorization_code" +
                "&redirect_uri=http://open.snd02.com/demo.jsp" +
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
        String token = null;
        JSONObject dataObject = JSON.parseObject(jsonObject.getString("data"));
        if("true".equals(success)){
            token= dataObject.getString("accessToken");
        }else{
            log.error(dataObject.getString("description")+":"+dataObject.getString("error"));
        }
        return token;
    }

    private void GET_BOXES(String token) throws Exception{

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("client_id", "O000009991");
        packageParams.put("method", "GET_BOXES");
        packageParams.put("access_token", token);
        packageParams.put("timestamp", sdf.format(nowDate));
        packageParams.put("projectCode", "P00000020772");

        String reqMessage = concatSignString(packageParams);
        String sigeMessage = concatMessageString(packageParams);
        log.info(sigeMessage);
        sigeMessage+="CB6BAB4164ABD5236079F9A6CFEB9CE5";
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

    }

    private void GET_BOX_CHANNELS_OC(String token) throws Exception{

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("client_id", "O000009991");
        packageParams.put("method", "GET_BOX_CHANNELS_OC");
        packageParams.put("access_token", token);
        packageParams.put("timestamp", sdf.format(nowDate));
        packageParams.put("projectCode", "P00000020772");
        packageParams.put("mac", "187ED5340D3C");

        String reqMessage = concatSignString(packageParams);
        String sigeMessage = concatMessageString(packageParams);
        log.info(sigeMessage);
        sigeMessage+="CB6BAB4164ABD5236079F9A6CFEB9CE5";
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

    }

    private void PUT_BOX_CONTROL_Switch(String token,String operation) throws Exception{

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("client_id", "O000009991");
        packageParams.put("method", "PUT_BOX_CONTROL");
        packageParams.put("access_token", token);
        packageParams.put("timestamp", sdf.format(nowDate));
        packageParams.put("projectCode", "P00000020772");
        packageParams.put("mac", "187ED5340D3C");
        packageParams.put("cmd", "OCSWITCH");
        packageParams.put("value1", operation);
        packageParams.put("value2", "1,5,10");

        String reqMessage = concatSignString(packageParams);
        String sigeMessage = concatMessageString(packageParams);
        log.info(sigeMessage);
        sigeMessage+="CB6BAB4164ABD5236079F9A6CFEB9CE5";
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
