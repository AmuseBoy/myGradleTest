package com.liu.util2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 返回日期格式字符串
 * @author Administrator
 *
 */
public class DateUtil {

	public static String getLongDate(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return year + "年"+month +"月"+day+"日";
	}
	
	
	public static String getDateTime(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	
	public static void main(String[] args) {
		//System.out.println(DateUtil.getDateTime());
		System.out.println(DateUtil.getLongDate());
	}
}
