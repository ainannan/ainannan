package cn.ainannan.sys.utils;

import java.text.DecimalFormat;

public class NumberUtil {

	
	/**
	 * 保留两位小数点的方法，将double值转换成保留小数两位，并返回
	 * @param d
	 * @return
	 */
	public static double getDoubleByTwo(double d){
		DecimalFormat df = new DecimalFormat("#.00");
		
		return Double.parseDouble(df.format(d));
	}
	
	/**
	 * 返回保留两位小数的Double类型
	 * @param d
	 * @return
	 */
	public static double changeDouble(double d){
		return Double.parseDouble(new DecimalFormat("#.00").format(d));	// 转换成小数点保留2位
	}
}
