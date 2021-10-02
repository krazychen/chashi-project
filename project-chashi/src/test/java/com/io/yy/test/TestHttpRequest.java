package com.io.yy.test;


import okhttp3.*;

import java.io.IOException;

public class TestHttpRequest {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "clientId=a2a4925348c84f55921331391bf9f800&accessToken=13373d03617e6de7e73d0774cadb2d75&lockId=3731369&pageNo=1&pageSize=100&date=1633191753746");
        Request request = new Request.Builder()
                .url("https://api.ttlock.com/v3/lock/listKeyboardPwd")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
