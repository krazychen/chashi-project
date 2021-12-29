package com.io.yy.util.excel.fieldtype;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期类型转换（日期时间类型转换 yyyy-MM-dd HH:mm:ss）
 * @author kris
 * @example fieldType = DateTimeType.class
 */
public class DateTimeType {

    /**
     * 获取对象值（导入）
     */
    public static Object getValue(String val) {
        return val == null ? "" : StringUtils.replace(val, ",", "");
    }

    /**
     * 获取对象值（导出）
     */
    public static String setValue(Object val) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return val == null ? "" : sdf.format(((Date)val).getTime());
    }

    /**
     * 获取对象值格式（导出）
     */
    public static String getDataFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 清理缓存
     */
    public static void clearCache(){

    }
}
