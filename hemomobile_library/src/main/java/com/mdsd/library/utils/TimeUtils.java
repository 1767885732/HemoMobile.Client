package com.mdsd.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	/**
	 * judge the string is date?
	 * 
	 * @param strDate
	 * @param dateFormat
	 * @return
	 */
	public static boolean isDate(String strDate, SimpleDateFormat dateFormat) {
		Date d = null;
		try {
			d = (Date) dateFormat.parseObject(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (d == null)
			return false;

		String tmp = dateFormat.format(d);

		return tmp.equals(strDate);
	}

	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 日期
	 * @throws java.text.ParseException
	 */
	public static Date strToDate(final String str, final String format) {
		String ft = format;

		if (null == str || "".equals(str)) {
			return null;
		}
		SimpleDateFormat sdf = null;
		// 如果没有指定字符串转换的格式，则用默认格式进行转换
		if (null == ft || "".equals(ft)) {
			sdf = DATE_FORMAT_DATE;
		}
		sdf = new SimpleDateFormat(ft);
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 * 
	 * @param str
	 *            字符串
	 * @return 日期
	 * @throws java.text.ParseException
	 */
	public static Date strToDate(final String str) {
		if (null == str || "".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = DATE_FORMAT_DATE.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String dateToStr(Date date) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 日期转成字符串
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date date,String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 计算时间差
	 *
	 * @param d1
	 * @param d2
	 * @return "day,hour, min,s"
	 */
	public static long[] getTimeDiff(Date d1, Date d2) {
		long l = d2.getTime() - d1.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		
		long[] rArray = new long[]{day,hour,min,s};
		return rArray;
	}

}
