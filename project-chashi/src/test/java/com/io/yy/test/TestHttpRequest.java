package com.io.yy.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.io.yy.util.lang.StringUtils;
import okhttp3.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestHttpRequest {

    public static void main(String[] args) throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "clientId=a2a4925348c84f55921331391bf9f800" +
                "&accessToken=13373d03617e6de7e73d0774cadb2d75" +
                "&lockId=3731369" +
                "&pageNo=1" +
                "&pageSize=100" +
                "&date=1633362111111");
        Request request = new Request.Builder()
                .url("https://api.ttlock.com/v3/lock/listKeyboardPwd")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
        System.out.println(response.code());
        System.out.println(response.body().string());

//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "client_id=a2a4925348c84f55921331391bf9f800" +
//                "&client_secret=d213a28bca6df5a29ffad6deda0ad353" +
//                "&username=13788825258" +
//                "&password=8a6f2805b4515ac12058e79e66539be9");
//        Request request = new Request.Builder()
//                .url("https://api.ttlock.com/oauth2/token")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response);
//        String responseBody = response.body().string();
//        System.out.println(responseBody);
//        JSONObject jsonObject = JSON.parseObject(responseBody);
//        System.out.println(jsonObject.getString("access_token"));
//        System.out.println(jsonObject.getLong("expires_in"));

//        long random = Math.abs(new Random().nextLong());
//        System.out.println(random);
//        Integer substring = Integer.valueOf(String.valueOf(random).substring(0, 6));
//        System.out.println(substring);

//        DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date=null;
//        date = sdf.parse("2021-08-30 00:00:00");
//
//        String timeRages = "14:00-15:00,15:00-16:00";
//        String[] times=timeRages.split("-");
//        System.out.println(times[0].split(":")[0]);
//        int startMin = Integer.valueOf(times[times.length-1].split(":")[0]).intValue();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.set(Calendar.HOUR_OF_DAY, startMin);
//
//        System.out.println(sdf.format(cal.getTime()));
    }
}
