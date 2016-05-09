package com.etl.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 是否为空或者空字符串
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/**
	 * 是否为空、空字符串或者空格字符串
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {
		return s == null || s.trim().length() == 0;
	}

	/**
	 * 当第一个参数为空时，返回第二个，否则直接返回第一个
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Object firstNotNull(Object a, Object b) {
		return a == null ? b : a;
	}

	/**
	 * 驼峰字符串转换成下划线
	 * 
	 * @param s
	 * @return
	 */
	public static String toUnderlineName(String s) {
		if (s == null)
			return null;

		String regexStr = "[A-Z]";
		Matcher matcher = Pattern.compile(regexStr).matcher(s);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String g = matcher.group();
			matcher.appendReplacement(sb, "_" + g.toLowerCase());
		}
		matcher.appendTail(sb);
		if (sb.charAt(0) == '_') {
			sb.delete(0, 1);
		}
		return sb.toString();
	}

	/**
	 * 下划线转驼峰字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		if (s == null)
			return null;

		StringBuilder sb = new StringBuilder(s);
		Matcher mc = Pattern.compile("_").matcher(s);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 生成UUID字符串，已经去掉了"-"
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
}
