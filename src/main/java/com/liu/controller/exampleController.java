package com.liu.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class exampleController {

	/*
	 * example合并前master合并前1.1
	 */
	@RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
	public String queryAll(@PathVariable String userId){
		return "this.is.userId"+userId;
	}
	
	
	@RequestMapping(value="/user2/{userId}", method=RequestMethod.GET)
	public String queryAll2(@PathVariable("userId") String userId2){
		return "this.is.userId"+userId2;
	}
	
	
	@RequestMapping(value="/user/{userId}/class/{classId}", method=RequestMethod.GET)
	public String queryAll3(@PathVariable String userId,@PathVariable String classId){
		return "this.is.userId:"+userId+";this.is.classId:"+classId;
	}
	
	
	
}
