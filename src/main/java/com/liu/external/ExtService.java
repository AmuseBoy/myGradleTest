package com.liu.external;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import com.liu.util.Exchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.liu.person.user.User;
import com.liu.person.user.UserRepository;
import com.liu.util.CommonProperties;
import com.liu.util.RestTemplateUtil;

@Service
public class ExtService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestTemplate restTemplate;
	
	public Log logger = LogFactory.getLog(ExtService.class);



	@Transactional	//加上事务，如果接口调用不成功，就会回滚；
	public void testRestTemplate(){
		Exchange.get((String) CommonProperties.get("address.extUrl") + "/app/crm/cust/custMerchs?userId=00021667",restTemplate);
	}


	public void timeOut(){
		RestTemplate restTemplate =  new RestTemplate();
		Exchange.SetTime(5000,5000,restTemplate);
		Exchange.get((String) CommonProperties.get("address.extUrlIp") + "/app/crmw/cust/custMerchs?userId=00021667",restTemplate);
	}
}
