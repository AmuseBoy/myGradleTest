package com.liu.util2;

public class StrConvertUtil {

	/**
	 * byte[]==>16进制
	 * @param arr
	 * @return
	 */
	public static String byteArrToHexStr(byte[] arr){
		StringBuffer sb = new StringBuffer();
		for(byte b : arr){
			if(b<0xf){//小于16补0
				sb.append("0");
			}
			sb.append(Integer.toHexString(b & 0xff));
		}
		return sb.toString();
	}
	
	/**
	 * 16进制字符串 返回 byte数组
	 * @param strIn
	 * @return
	 */
	public static byte[] hexStrToByteArr(String strIn){
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		// 两个(16进制)字符表示一个字节(8位)，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen/2];
		for(int i=0;i<iLen;i=i+2){
			String strTmp = new String(arrB,i,2);
			System.out.println(strTmp);
			arrOut[i/2] = (byte) Integer.parseInt(strTmp, 16);//string 转 byte
		}
		return arrOut;
	}
	
	
	public static void main(String[] args) {
		byte[] b = {3,43,32};
		String str = byteArrToHexStr(b);
		System.out.println("str:"+str);
		
		
		
		String str1 = "032b20";
		byte[] c = hexStrToByteArr(str1);
		System.out.println("c:"+c[2]);
	}
	
	
	
	
	
	
	
	
	
}
