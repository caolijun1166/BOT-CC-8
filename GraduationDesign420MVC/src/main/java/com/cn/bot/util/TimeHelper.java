package com.cn.bot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {
	/**
	 * ����Memcache����ʱ��(��λ����)
	 * @param minutes
	 * @return
	 */
	public static Date setExpire(int minutes){
		return new Date(minutes*60*1000);
	}
	
	/**
	 *��ȡ��ǰʱ��
	 *ʱ���ʽΪ yy-mm-dd hh:mm:ss
	 * @return
	 */
	public static String getCurrentTime(){
		return getCurrentTime(true);
	}
	
	/**
	 * ��ȡ��ǰʱ��
	 * @param hyphen
	 * ������Ϊtrueʱ��ʱ���ʽΪyyyy-MM-dd hh:mm:ss
	 * ������Ϊfalseʱ��ʱ���ʽΪyyyyMMddhhmmss
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
