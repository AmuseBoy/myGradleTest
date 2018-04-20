package com.liu.external;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.liu.util.Exchange;
import com.liu.util2.CheckUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.liu.commons.AbstractController;
import com.liu.person.user.User;
import com.liu.util.CommonProperties;
import com.liu.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对外接口
 * @author 刘培振 liupeizhen@bestvike.com
 *
 */
@RestController
public class ExtController extends AbstractController{

	public Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ExtService extService;

	/**
	 * 测试RestTemplate
	 * @return
	 */
	@RequestMapping(value="/pub/test/testRestTemplate",method=RequestMethod.GET)
	public Map<String, Object> testRestTemplate(){
		extService.testRestTemplate();
		return ResultUtil.success();
	}

	/**
	 * 测试RestTemplate超时时间
	 * @return
	 */
	@RequestMapping(value="/pub/restTemplate/timeOut",method=RequestMethod.GET)
	public Map<String, Object> timeOut(){
		extService.timeOut();
		return ResultUtil.success();
	}



	/**
	 *
	 * appid:wx6c1c3bb3a2480566
	 * appsecret:d6d5bbbedabe6441050a15c2f923d1d3
	 */
	@RequestMapping(value="/pub/weixin/getAccessToken",method=RequestMethod.GET)
	public Map<String,Object> getAccessToken(){
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx6c1c3bb3a2480566&secret=d6d5bbbedabe6441050a15c2f923d1d3 ";
		RestTemplate restTemplate = new RestTemplate();
		Map<String,Object> map = Exchange.get(url,restTemplate);
		return map;
	}



	/**
	 * http://ngroklilm2.ngrok.cc/pub/weixin/token
	 * amuseboy
	 * 微信功能测试
	 * @return
	 */
	@RequestMapping(value="/pub/weixin/token",method=RequestMethod.GET)
	public void token(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		logger.info(signature+"-"+timestamp+"-"+nonce+"-"+echostr);
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature,timestamp,nonce)){
			out.write(echostr);
		}
	}


}
