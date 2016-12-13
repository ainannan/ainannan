package cn.ainannan.sys;

public class State {

	public static final int YES = 1; // 是、有效、通过 已付款 降序
	public static final int NO = 0; // 否、无效、未通过 未付款 升序

	/**
	 * 日期格式，以"yyyy-MM-dd"
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd"; // 日期格式
	public static String[] FILTER_URLS = { "car.do", "caidan.do", "tizhong.do",
											"lijin.do" };// filter过滤器

}
