package com.liu.person.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liu.commons.AbstractController;
import com.liu.commons.BusinessException;
import com.liu.util.ResultUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
public class UserController extends AbstractController{

	@Autowired
	private UserService userService;
	@Autowired
	private StringRedisTemplate redisTemplate;

	@PersistenceContext(unitName = "demo")
	private EntityManager entityManager;
	
	/**
	 * 测试redis
	 * @return
	 */
	@RequestMapping(value="/api/redis/test",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> RedisTest(){
		Map<String, Object> map = new HashMap<String ,Object>();
		Long l =redisTemplate.opsForList().leftPush("list", "xiaoliu");
		map.put("success", l);
		return map;
	}
	
	@RequestMapping(value="/api/person/findByUserId",method=RequestMethod.GET)
	@ResponseBody
	public User findById(@RequestParam(value="id")String id){
		return userService.findById(id);
	}
	
	
	@RequestMapping(value="/api/person/saveUser",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUser(@RequestBody User user){
		if(StringUtils.isEmpty(user.getId())){//"id":null或不传值，都为null,"id":""则是空串
			System.out.println(user.getId());
			return ResultUtil.fail();
		}
		userService.saveUser(user);
		return ResultUtil.success();
	}
	
	/**
	 * 分页请求方式：
	 * http://localhost:8088/api/person/querPageUser?sort=name,desc&size=4&page=3
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value="/api/person/querPageUser",method=RequestMethod.GET)
	@ResponseBody
	public Page<User> querPageUser(Pageable pageable){
		return userService.querPageUser(pageable);
	}
	/**
	 * 模糊查询
	 * @param name
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value="/api/person/findPageUserByLike",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findPageUserByLike(String name ,Pageable pageable){
		return userService.findPageUserByLike(name,pageable);
	}
	
	
	/**
	 * 事务问题测试
	 * {"id":"0023","name":"小白","sex":"女","birth":"1992-02-04","email":"yyyy@bestvike.com"}
	 * @param user
	 * @return 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/api/person/testTransactional",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testTransactional(@RequestBody User user){
//		try{
			User userQ = userService.findById("0023");
			System.out.println("电子邮箱（保存前）："+userQ.getEmail());
			userService.saveTestUser1(user);
			System.out.println("电子邮箱（保存后）："+userQ.getEmail());
//		}catch(BusinessException e){
//			return ResultUtil.busiFail(e.getRetFlag(),e.getRetMsg());
//		}
		return ResultUtil.success();
	}
	
	
	/**
	 * 返回ModelAndView
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/api/person/returnModelAndView",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView returnModelAndView(@RequestParam(value="id")String id){
		User user = userService.findById(id);
		ModelAndView mav = new ModelAndView("User");
		mav.addObject("user", user);
		return mav;
	}
}
