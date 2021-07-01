/**
 * 2019-12-04
 */
package com.io.yy.util.excel.fieldtype;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;


/**
 * 金额类型转换（保留两位）
 * @author kris
 * @version 2019-12-04
 * @example fieldType = MoneyType.class
 */
public class MoneyType {

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
		NumberFormat nf = new DecimalFormat(",##0.00"); 
		return val == null ? "" : nf.format(val);
	}
	
	/**
	 * 获取对象值格式（导出）
	 */
	public static String getDataFormat() {
		return "0.00";
	}
	
	/**
	 * 清理缓存
	 */
	public static void clearCache(){
		
	}
	
}
