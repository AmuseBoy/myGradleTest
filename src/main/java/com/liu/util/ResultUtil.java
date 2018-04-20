package com.liu.util;

import java.util.HashMap;
import java.util.Map;

public abstract class ResultUtil {

    private static String SUCCESS_CODE = "00000";
    private static String SUCCESS_MSG = "处理成功";
    
    private static String FAIL_CODE = "error";
    private static String FAIL_MSG = "处理失败";
    
    public static Map<String, Object> success(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("head", new ResultHead(SUCCESS_CODE, SUCCESS_MSG));
    	return map;
    }
    
    public static Map<String, Object> fail(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("head", new ResultHead(FAIL_CODE, FAIL_MSG));
    	return map;
    }
    
    public static Map<String, Object> busiFail(String retFlag, String retMsg){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("head", new ResultHead(retFlag, retMsg));
    	return map;
    }
}
