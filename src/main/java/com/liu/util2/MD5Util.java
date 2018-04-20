package com.liu.util2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * @author Administrator
 *
 */
public class MD5Util {

	
	/**
	 * 根据输入的字符串生成32位的MD5码
	 * @param str
	 * @return
	 */
	public static String getMd5(String str){
		MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		mdInst.update(str.getBytes());
		byte[] md = mdInst.digest();
		return StrConvertUtil.byteArrToHexStr(md);
		
	}
	
	public static void main(String[] args){
		System.out.println(MD5Util.getMd5("haier123"));
	}
}
