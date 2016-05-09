package com.etl.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 * Author     Version       Date        Changes
 * rock      1.0           2016年3月1日     Created
 * </pre>
 */

public abstract class DateUtils {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String ES_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String ES_DATE_TIME_FORMAT_ZONE = "yyyy-MM-dd'T'HH:mm:ssZZ";

	public static final Locale DEFAULT_LOCALE = Locale.CHINA;

	private static final String[] sADatePatterns = { "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
			"MM/dd/yyyy HH:mm:ss", "yyyy.MM.dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd", "MM/dd/yyyy", "yyyy.MM.dd" };

	public static Date pownerParseDate(String sPDateStr) throws ParseException {
		for (int i = 0; i < sADatePatterns.length; ++i)
			try {
				SimpleDateFormat oFmt = new SimpleDateFormat(sADatePatterns[i]);
				Date oDate = oFmt.parse(sPDateStr);
				if (oDate.getTime() >= 0L) {
					return oDate;
				}
			} catch (Exception ex) {
			}
		throw new ParseException("can't parse date:" + sPDateStr, 0);
	}

	public static Date getCurrent() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param format
	 *            yyyy-MM-dd
	 * @param locale
	 *            地域
	 * @return
	 */
	public static String formatDate(Date Date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT, DEFAULT_LOCALE);
		return sdf.format(Date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            HH:mm:ss
	 * @param locale
	 *            地域
	 * @return
	 */
	public static String formatTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_FORMAT, DEFAULT_LOCALE);
		return sdf.format(date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            HH:mm:ss
	 * @param locale
	 *            地域
	 * @return
	 */
	public static String formatEsTime(Long date) {
		SimpleDateFormat sdf = new SimpleDateFormat(ES_DATE_TIME_FORMAT, DEFAULT_LOCALE);
		return sdf.format(getDate(new Timestamp(date)));
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            HH:mm:ss
	 * @param locale
	 *            地域
	 * @return
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, DEFAULT_LOCALE);
		return sdf.format(date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param calendar
	 *            时间
	 * @param format
	 *            自定义格式
	 * @param locale
	 *            地域
	 * @return
	 */
	public static String formatDateTime(Calendar calendar, String format, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式
	 * @param locale
	 *            地域
	 * @return
	 */
	public static String formatDateTime(Date date, String format, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(date);
	}

	/**
	 * 取当前服务器上的日期。使用默认日期格式。<br />
	 * 在没有改变的情况下.默认格式为"yyyy-MM-dd".
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return formatDateTime(getCurrent(), DEFAULT_DATE_FORMAT, DEFAULT_LOCALE);
	}

	/**
	 * 取当前服务器上的时间.使用默认时间格式.<br />
	 * 在没有改变的情况下.默认格式为"HH:mm:ss".
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return formatDateTime(getCurrent(), DEFAULT_TIME_FORMAT, DEFAULT_LOCALE);
	}

	/**
	 * 取当前服务器上的日期时间.使用默认日期时间格式.<br />
	 * 在没有改变的情况下.默认格式为"yyyy-MM-dd HH:mm:ss".
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return formatDateTime(getCurrent(), DEFAULT_DATE_TIME_FORMAT, DEFAULT_LOCALE);
	}

	/**
	 * 转换Date为Timestamp.
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 转换long为Timestamp.
	 * 
	 * @param time
	 * @return
	 */
	public static Timestamp getTimestamp(long time) {
		return new Timestamp(time);
	}

	/**
	 * 转换Current Date为Timestamp.
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(getCurrent().getTime());
	}

	/**
	 * 转换Timestamp为Date.
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Date getDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	/**
	 * 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getTime(Date date) {
		return date.getTime();
	}

	/**
	 * 使用指定的格式和地域解析字段串为时间
	 * 
	 * @param format
	 *            格式
	 * @param locale
	 *            地域
	 * @param str
	 *            字段串
	 * @return
	 */
	public static Date parseDateTime(String format, Locale locale, String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// 初始化一个例外
			return null;
		}
	}

	/**
	 * 使用默认值解析表示日期时间的字符
	 * 
	 * @param str
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parseDateTime(String str) {
		return parseDateTime(DEFAULT_DATE_TIME_FORMAT, DEFAULT_LOCALE, str);
	}

	/**
	 * 使用默认值解析表示日期的字符
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate(String str) {
		return parseDateTime(DEFAULT_DATE_FORMAT, DEFAULT_LOCALE, str);
	}

	/**
	 * 使用默认值解析表示时间的字符
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseTime(String str) {
		return parseDateTime(DEFAULT_TIME_FORMAT, DEFAULT_LOCALE, str);
	}

	/**
	 * 使用指定格式取指定时间的月首
	 * 
	 * @param format
	 * @return
	 */
	public static String getMonthStart(Date date, String format) {
		Calendar localTime = Calendar.getInstance(DEFAULT_LOCALE);
		localTime.setTime(date);
		localTime.set(Calendar.DAY_OF_MONTH, 1);
		return formatDateTime(localTime, format, DEFAULT_LOCALE);
	}

	/**
	 * 使用指定格式取指定时间的月末
	 * 
	 * @return
	 */
	public static String getMonthEnd(Date date, String format) {
		Calendar localTime = Calendar.getInstance(DEFAULT_LOCALE);
		localTime.setTime(date);
		localTime.add(Calendar.MONTH, 1);
		localTime.set(Calendar.DAY_OF_MONTH, 1);
		localTime.add(Calendar.DAY_OF_MONTH, -1);
		return formatDateTime(localTime, format, DEFAULT_LOCALE);
	}

	/**
	 * 对指定时间进行增减操作后返回指定格式的时间串
	 * 
	 * @param date
	 *            指定的时间
	 * @param calendarField
	 *            在{@link java.util.Calendar}类定义的字段
	 * @param amount
	 *            增加数量
	 * @param format
	 *            指定格式
	 * @return
	 */
	public static String addDateTime(Date date, int calendarField, int amount, String format) {
		Calendar localTime = Calendar.getInstance(DEFAULT_LOCALE);
		localTime.setTime(date);
		localTime.add(calendarField, amount);
		return formatDateTime(localTime, format, DEFAULT_LOCALE);
	}

	/**
	 * 比较两个时间的顺序
	 * 
	 * @param src
	 * @param dst
	 * @return 如果src在dst之前则 小于0<br />
	 *         如果src在dst之后则 大于0<br />
	 *         如果相同则 等于0
	 */
	public static int comparaDateTime(Date src, Date dst) {
		return src.compareTo(dst);
	}

	/**
	 * 将日期的时分秒部分去掉
	 * 
	 * @param date
	 * @return
	 */
	public static Date truncateTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 将java.util.Date转换成java.sql.Date
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date transfer2SqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * java.util.Date转成java.sql.Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp transfer2Timestamp(java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 时间计算
	 * 
	 * @param date
	 * @param DateField
	 *            计算间隔 单位的定义（天/月/年……）Calendar.*
	 * @param num
	 *            间隔变量
	 * @return
	 */
	public static Date addDate(Date date, int DateField, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(DateField, num);
		return cal.getTime();
	}

	/**
	 * 获取 格式为yyyy-MM-dd 00:00:00的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateBegin(Date current) {
		return parseDateTime(formatDate(current) + " 00:00:00");
	}

	public static Date getDateEnd(Date current) {
		return parseDateTime(formatDate(current) + " 23:59:59");
	}
}