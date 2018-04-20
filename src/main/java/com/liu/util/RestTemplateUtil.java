package com.liu.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * 接口公共方法类
 * @author 刘培振 liupeizhen@bestvike.com
 *
 */
public class RestTemplateUtil {

	public static Log logger = LogFactory.getLog(RestTemplateUtil.class);
	
	public static Map<String, Object> get(String requestUrl,RestTemplate restTemplate){
		return restTemplate.getForObject(requestUrl, Map.class);
	}
	
	
	public static Map<String, Object> exchange(String requestUrl,Map<String,Object> requestMap,RestTemplate restTemplate) {
		logger.info("to request :"+requestUrl);
		logger.info("requestMap :"+requestMap);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		HttpEntity<Map<String, Object>> reqE = new HttpEntity<Map<String,Object>>(requestMap,headers);
		responseMap = restTemplate.postForObject(requestUrl, reqE, Map.class);
		logger.info("responseMap "+responseMap);
		return responseMap;
	}
	
	
	public static String exchange(String requestUrl,String requestStr,RestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		HttpEntity<String> reqE = new HttpEntity<String>(requestStr,headers);
		return restTemplate.postForObject(requestUrl, reqE, String.class);
	}
}
