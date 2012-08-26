package org.yftx.wzd.utils;


import java.util.Calendar;


public class DateTimeHelper {
	public static String getNowTime() {
		return Calendar.getInstance().getTime().toLocaleString();
	}
}
