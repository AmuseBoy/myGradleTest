package com.liu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 功能/模块：Exchange.java
 * @author 刘培振 liupeizhen@bestvike.com
 * @version 1.0 2017年8月1日 下午1:22:03
 * 类描述  接口交互公共类
 * 修订历史：
 * 日期  作者  参考  描述
 * 相关类连接
 * 海尔消费金融版权所有.
 */
public abstract class Exchange {
	
	public static Log logger = LogFactory.getLog(Exchange.class);
	
	/**
	 * @Title: exchange
	 * @Description: post请求
	 * @author 刘培振 liupeizhen@bestvike.com
	 * @date 2017年8月1日 下午1:44:08
	 * @param url
	 * @param requestMap
	 * @param restTemplate
	 * @return
	 */
	public static Map<String,Object> postForObject(String url, Map<String, Object> requestMap,RestTemplate restTemplate){
		Map<String,Object> map = new HashMap<>();
		logger.info("请求【url】"+url);
		logger.info("请求【内容】"+requestMap);
		try{
			map = restTemplate.postForObject(url,requestMap, Map.class);
		}catch(Exception e){
			logger.error("接口交互异常"+e,e);
			return null;
		}
        logger.info("返回【结果】"+map.toString());
        return map;
	}
	
	/**
	 * @Title: postForEntity
	 * @Description: entity请求
	 * @author 刘培振 liupeizhen@bestvike.com
	 * @date 2017年8月1日 下午2:18:01
	 * @param url
	 * @param requestMap
	 * @param restTemplate
	 * @return
	 */
	public static Map<String,Object> postForEntity(String url, Map<String, Object> requestMap,RestTemplate restTemplate){
		logger.info("请求【url】"+url);
		logger.info("请求【内容】"+requestMap);
		ResponseEntity<Map> resMap;
		try{
			resMap = restTemplate.postForEntity(url,requestMap, Map.class);
			if(HttpStatus.OK.value() == resMap.getStatusCode().value()){
				logger.info("返回【结果】"+resMap);
			}else{
				logger.info("接口交互异常:"+resMap);
				return null;
			}
		}catch(Exception e){
			logger.info("接口交互异常:"+e,e);
			return null;
		}
		return resMap.getBody();
	}
	
	/**
	 * @Title: postForExchange
	 * @Description: exchange请求
	 * @author 刘培振 liupeizhen@bestvike.com
	 * @date 2017年8月1日 下午2:18:01
	 * @param url
	 * @param requestMap
	 * @param restTemplate
	 * @return
	 */
	public static Map<String,Object> postForExchange(String url,Map<String, Object> requestMap,RestTemplate restTemplate){
		logger.info("请求【url】"+url);
		logger.info("请求【内容】"+requestMap);
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(mediaType);
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestMap, headers);
		ResponseEntity<Map> resMap = null;
		try{
			resMap = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
			if(HttpStatus.OK.value() == resMap.getStatusCode().value()){
				logger.info("返回【结果】"+resMap);
			}else{
				logger.info("接口交互异常:"+resMap);
				return null;
			}
		}catch(Exception e){
			logger.info("接口交互异常:"+e,e);
			return null;
		}
		return resMap.getBody();
	}

	/**
	 * 返回text/html
	 * @param url
	 * @param requestMap
	 * @param restTemplate
	 * @return
	 */
	public static String postForEntityString(String url, Map<String, Object> requestMap,RestTemplate restTemplate){
		logger.info("请求【url】"+url);
		logger.info("请求【内容】"+new JSONObject(requestMap).toString());
		ResponseEntity<String> res = null;
		try{
			res = restTemplate.postForEntity(url,requestMap, String.class);
			if(200 == res.getStatusCode().value()){
				logger.info("返回【结果】"+res.getBody());
			}else{
				logger.info("接口交互异常:"+res.getStatusCode());
			}
		}catch(Exception e){
			logger.info("接口交互异常:"+e,e);
			return null;
		}
		return res.getBody();
	}

	/**
	 * @Title: get
	 * @Description: get请求
	 * @author 刘培振 liupeizhen@bestvike.com
	 * @date 2017年8月1日 下午1:37:35
	 * @param url
	 * @param restTemplate
	 * @return
	 */
	public static Map<String,Object> get(String url,RestTemplate restTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		logger.info("请求【url】"+url);
		try{
			map = restTemplate.getForObject(url, Map.class);
		}catch(Exception e){
			logger.error("接口交互异常"+e,e);
			return null;
		}
        logger.info("返回【结果】"+map.toString());
        return map;
	}

	/**
	 * 设置超时时间等
	 * @param readTime
	 * @param connectTime
	 * @param restTemplate
	 */
	public static void SetTime(int readTime, int connectTime,RestTemplate restTemplate) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setReadTimeout(readTime);
		requestFactory.setConnectTimeout(connectTime);
		restTemplate.setRequestFactory(requestFactory);
	}

}
