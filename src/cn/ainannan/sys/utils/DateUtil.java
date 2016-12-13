package cn.ainannan.sys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 获得以指定格式下的时间字符串
	 * @param format
	 * @return
	 */
	public static String getCurrDateString(String format){
		
		return new SimpleDateFormat(format).format(new Date());
	}
	
	/**
	 * 获得以yyyy-MM-dd格式的日期字符串
	 * @return
	 */
	public static String getCurrDateString(){
		return getCurrDateString("yyyy-MM-dd");
	}
	
	/**
	 * 将字符串格式的时间转换成Date类型
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getDate(String str,String format) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
