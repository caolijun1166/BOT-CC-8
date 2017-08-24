package com.cn.bot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {
	/**
	 * 设置Memcache缓存时间(单位分钟)
	 * @param minutes
	 * @return
	 */
	public static Date setExpire(int minutes){
		return new Date(minutes*60*1000);
	}
	
	/**
	 *获取当前时间
	 *时间格式为 yy-mm-dd hh:mm:ss
	 * @return
	 */
	public static String getCurrentTime(){
		return getCurrentTime(true);
	}
	
	/**
	 * 获取当前时间
	 * @param hyphen
	 * 当参数为true时，时间格式为yyyy-MM-dd hh:mm:ss
	 * 当参数为false时，时间格式为yyyyMMddhhmmss
	 * @return
	 */
	public static String getCurrentTime(boolean hyphen){
		Date date = new Date();
		SimpleDateFormat formatter;
		if(hyphen){
			formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String currentTime = formatter.format(date);
			return currentTime;
		}else{
			formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String currentTime = formatter.format(date);
			return currentTime;
		}
	}
}
