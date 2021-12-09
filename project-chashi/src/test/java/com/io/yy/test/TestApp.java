package com.io.yy.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.io.yy.util.lang.DateUtils;
import okhttp3.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestApp {

    public static void main(String[] args) throws ParseException, IOException {
//        String orderDate = "2021-11-18 00:00:00";
//        String timeRange = "14:00-15:00,15:00-16:00";
//
//        String[] timeRangeArr = timeRange.split("-");
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date date = sdf.parse(orderDate);
//
//        // 设置开始时间:
//        Calendar startC = Calendar.getInstance();
//        // 清除所有:
//        startC.clear();
//        startC.setTime(date);
//        String[] startTimeRangeArr = timeRangeArr[0].split(":");
//        startC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeRangeArr[0]));
//        startC.set(Calendar.MINUTE, Integer.parseInt(startTimeRangeArr[1]));
//        startC.set(Calendar.SECOND, 00);
//        System.out.println(sdf.format(startC.getTime()));
//
//        // 设置结束时间:
//        Calendar endC = Calendar.getInstance();
//        // 清除所有:
//        endC.clear();
//        endC.setTime(date);
//        String[] endTimeRangeArr = timeRangeArr[timeRangeArr.length-1].split(":");
//        endC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeRangeArr[0]));
//        endC.set(Calendar.MINUTE, Integer.parseInt(endTimeRangeArr[1]));
//        endC.set(Calendar.SECOND, 00);
//        System.out.println(sdf.format(endC.getTime()));
//
//        System.out.println(DateUtils.differentMinute(new Date(),startC.getTime()));
        String jsonStr = "{\n" +
                "        &quot;first&quot;: {\n" +
                "            &quot;value&quot;:&quot;恭喜你购买成功！&quot;,\n" +
                "            &quot;color&quot;:&quot;#173177&quot;\n" +
                "        },\n" +
                "        &quot;keyword1&quot;:{\n" +
                "            &quot;value&quot;:&quot;巧克力&quot;,\n" +
                "            &quot;color&quot;:&quot;#173177&quot;\n" +
                "        },\n" +
                "        &quot;keyword2&quot;: {\n" +
                "            &quot;value&quot;:&quot;39.8元&quot;,\n" +
                "            &quot;color&quot;:&quot;#173177&quot;\n" +
                "        },\n" +
                "        &quot;keyword3&quot;: {\n" +
                "            &quot;value&quot;:&quot;2014年9月22日&quot;,\n" +
                "            &quot;color&quot;:&quot;#173177&quot;\n" +
                "        },\n" +
                "        &quot;remark&quot;:{\n" +
                "            &quot;value&quot;:&quot;欢迎再次购买！&quot;,\n" +
                "            &quot;color&quot;:&quot;#173177&quot;\n" +
                "        }\n" +
                "    }";
//        String newJson = StringEscapeUtils.unescapeHtml4(jsonStr);
//        System.out.println(newJson);
//        JSONObject jb= JSON.parseObject(newJson);
//        System.out.println(jb.get("first"));
//        System.out.println(jb.toJSONString());
//
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("touser","oY2SAjnmDPBf3QkzndAVMtTOw9pE");
//        map.put("template_id","wCGTnTmnEQKP_jJ4pXchAYQF2HoXkvmRkUUKQredmH8");
//        map.put("url","http://weixin.qq.com/download");
////        map.put("data",jb.toJSONString());
//
//        Map<String,String> minMap = new HashMap();
//        minMap.put("appid","wxd7f07fdf11d9e7f4");
//        minMap.put("pagepath","/");
//        map.put("miniprogram",minMap);
//
//        Map<String,Object> dataMap = new HashMap<String,Object>();
//        Map<String,String> firstMap = new HashMap<String,String>();
//        firstMap.put("value","您好，有一间保洁服务需求时间即将到，请及时处理！");
//        Map<String,String> key1Map = new HashMap<String,String>();
//        key1Map.put("value","您好，有一间保洁服务需求时间即将到，请及时处理！");
//        Map<String,String> key2Map = new HashMap<String,String>();
//        key2Map.put("value","您好，有一间保洁服务需求时间即将到，请及时处理！");
//        Map<String,String> key3Map = new HashMap<String,String>();
//        key3Map.put("value","您好，有一间保洁服务需求时间即将到，请及时处理！");
//        Map<String,String> remarkMap = new HashMap<String,String>();
//        remarkMap.put("value","您好，有一间保洁服务需求时间即将到，请及时处理！");
//        dataMap.put("first",firstMap);
//        dataMap.put("keyword1",key1Map);
//        dataMap.put("keyword2",key2Map);
//        dataMap.put("keyword3",key3Map);
//        dataMap.put("remark",remarkMap);
//
//        map.put("data",dataMap);
//
//        System.out.println(JSON.toJSONString(map));
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(map));
//        Request request = new Request.Builder()
//                .url("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=51_bfN2U6WyqzTyIvzPB2tgHvCvuFow0dqVgLsgdCK3lPFkAV5ZnBaT_vCBCixDMbBnM3cIZ0H_vsTyqY3YGvhS1ccsTdfSGlVu8ZlbhxZMdqL2a6dMIaw3J5KSrE6hkMSlLT4rYDtyk_2JmUNsCMGaAIAQAU")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();

//        String testS="ORDER_NOTIFY]3333[4444";
//        String t1=testS.substring(testS.lastIndexOf("]")+1,testS.lastIndexOf("["));
//        String t2=testS.substring(testS.lastIndexOf("[")+1);
//
//        System.out.println(t1+":"+t2);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://test.520178.net/radio.php?sid=2&ProKey=a1aQWO3O1fv&sname=861714055496545")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONObject jsonObject = JSON.parseObject(responseBody);
        String Success = jsonObject.getString("Success");
        if("true".equals(Success)){
            System.out.println(": 声音提醒正常");
        }else{
            System.out.println("声音提醒api存在问题，请检查！");
        }

    }
}
