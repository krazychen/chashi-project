package com.io.yy.test;


import com.io.yy.util.lang.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestApp {

    public static void main(String[] args) throws ParseException {
        String orderDate = "2021-11-18 00:00:00";
        String timeRange = "14:00-15:00,15:00-16:00";

        String[] timeRangeArr = timeRange.split("-");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = sdf.parse(orderDate);

        // 设置开始时间:
        Calendar startC = Calendar.getInstance();
        // 清除所有:
        startC.clear();
        startC.setTime(date);
        String[] startTimeRangeArr = timeRangeArr[0].split(":");
        startC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeRangeArr[0]));
        startC.set(Calendar.MINUTE, Integer.parseInt(startTimeRangeArr[1]));
        startC.set(Calendar.SECOND, 00);
        System.out.println(sdf.format(startC.getTime()));

        // 设置结束时间:
        Calendar endC = Calendar.getInstance();
        // 清除所有:
        endC.clear();
        endC.setTime(date);
        String[] endTimeRangeArr = timeRangeArr[timeRangeArr.length-1].split(":");
        endC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeRangeArr[0]));
        endC.set(Calendar.MINUTE, Integer.parseInt(endTimeRangeArr[1]));
        endC.set(Calendar.SECOND, 00);
        System.out.println(sdf.format(endC.getTime()));

        System.out.println(DateUtils.differentMinute(new Date(),startC.getTime()));
    }
}
