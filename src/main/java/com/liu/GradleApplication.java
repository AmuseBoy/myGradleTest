package com.liu;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.liu.person.user.User;
import com.liu.person.user.UserRepository;
import com.liu.util.CommonProperties;
import com.liu.util.RedisProperties;
//import com.liu.util.RedisProperties;
import com.liu.util.ResultUtil;


@SpringBootApplication
@RestController
//@EnableRedisHttpSession  //首先配置reids,然后加上这个注解就能使用redis
@EnableConfigurationProperties({RedisProperties.class,CommonProperties.class})
//@EnableAutoConfiguration
@EnableEurekaClient
@EntityScan(basePackages = {"com.liu"})
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class GradleApplication {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpSession httpSession;
	@Autowired
	private UserRepository userRepository;
	
	@LoadBalanced//对请求接口的服务器 负载均衡
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GradleApplication.class, args);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/user",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> user(Principal user){
		User loginUser = userRepository.findOne(user.getName());
		if(null != loginUser){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", loginUser.getId());
			map.put("password", loginUser.getPassWord());
			map.put("sessionId", httpServletRequest.getSession().getId());  
			httpSession.setAttribute("user",map);
			return (Map<String, Object>) httpSession.getAttribute("user");
		}
		return ResultUtil.fail();
	}
	
}
