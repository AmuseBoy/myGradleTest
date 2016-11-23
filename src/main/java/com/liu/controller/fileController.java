package com.liu.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class fileController {

	
	
	@RequestMapping(value="/file/upload", method=RequestMethod.GET)
	public String upload(MultipartFile file,HttpServletRequest  req){
		return "this.is.userId";
	}
	
	
}
