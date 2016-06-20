package com.simple.common.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class PrimaryKeyUtil {
	
	private final static String STATIC_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	/**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	/**
	 * 获得指定数目的UUID
	 * @param number 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number){
		if(number < 1){
			return null;
		}
		String[] uuids = new String[number];
		for(int i=0;i<number;i++){
			uuids[i] = getUUID();
		}
		return uuids;
	}
	
	public static String getRandomString()
	{
		String randomStr = "";
		Random random = new Random();
		
		for(int i=0;i<25;i++)
		{
			randomStr += STATIC_STR.charAt(random.nextInt(62));
		}
		String time = System.currentTimeMillis()+"";
		randomStr += "-" + time.substring(time.length()-4);
		return randomStr;
	}

}
