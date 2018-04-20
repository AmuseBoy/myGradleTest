package com.liu.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class exampleController {

	public Log logger = LogFactory.getLog(this.getClass());


	/*
	 * example合并前master合并前1.1
	 */
	@RequestMapping(value="/api/user/{userId}", method=RequestMethod.GET)
//	@ResponseBody
	public Map<String,Object> queryAll(@PathVariable String userId,HttpServletRequest req,HttpServletResponse resp){
		logger.info(req.getRequestURL());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("json", "要返回json格式的，前台才能处理");
		return map;
	}
	
	
	@RequestMapping(value={"/api/user2/{userId}","/api/user3/{userId}"}, method=RequestMethod.GET)
//	@ResponseBody
	public String queryAll2(@PathVariable("userId") String userId2){
		return "this.is.userId:"+userId2;
	}

	
	
	
}
