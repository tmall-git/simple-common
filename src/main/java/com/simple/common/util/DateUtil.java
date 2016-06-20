package com.simple.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String date2AllString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
}
