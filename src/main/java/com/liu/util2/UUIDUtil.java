package com.liu.util2;

import java.util.UUID;

/**
 * UUID生成
 * @author Administrator
 *
 */
public class UUIDUtil {

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		//System.out.println(UUIDUtil.getUUID());
		
		
		String s = "513128199309134329";
		if(s.length() ==  18){
			s = s.replace(s.substring(11,14), "___");
		}
		
		System.out.println(s);
		
	}
}
