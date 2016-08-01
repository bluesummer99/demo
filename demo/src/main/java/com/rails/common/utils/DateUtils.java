package com.rails.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static String formatter19 = "yyyy-MM-dd HH:mm:ss";

	public static String formatter10 = "yyyy-MM-dd";

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}
	public static String getCurrentDate19() {
		return date2String(new Date(System.currentTimeMillis()),formatter19);
	}
	public static String getCurrentDate10() {
		return date2String(new Date(System.currentTimeMillis()),formatter10);
	}

	public static String date2String(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static Date String2Date(String string, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = formatter.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static long getDiffDays(Date startDate, Date endDate) {
		long difftime = endDate.getTime() - startDate.getTime();
		return difftime / (24L * 60L * 60L * 1000L);
	}


}
