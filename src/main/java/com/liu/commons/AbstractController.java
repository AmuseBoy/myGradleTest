package com.liu.commons;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.liu.util.CommonProperties;
import com.liu.util.ResultHead;

@RestController
public abstract class AbstractController {

	public Log logger = LogFactory.getLog(AbstractController.class);
	
	
	protected String getExtUrl(){
		return (String) CommonProperties.get("address.extUrl");
	}
	
	
    /**
     * 其他未处理的异常
     * @param <T>
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResultHead> handleException(Exception e) {
        logger.error("未处理的异常:"+e,e);
    	ResultHead resultHead = new ResultHead("9903","网络通讯异常");
        return new ResponseEntity<>(resultHead,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
