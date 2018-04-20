package com.liu.util;

import java.util.List;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisUtil2 {

	/**
	 * 压栈
	 * @param key
	 * @param value
	 * @param redisTemplate
	 * @return
	 */
	public static Long push(String key, String value,StringRedisTemplate redisTemplate){
		return redisTemplate.opsForList().leftPush(key, value);
	}
	
	/**
	 * 出栈
	 * @param key
	 * @param redisTemplate
	 * @return
	 */
	public static String pop(String key,StringRedisTemplate redisTemplate){
		return redisTemplate.opsForList().leftPop(key);
	}
	
	/**
	 * 入队
	 * @param key
	 * @param value
	 * @param redisTemplate
	 * @return
	 */
	public Long in(String key,String value,StringRedisTemplate redisTemplate){
		return redisTemplate.opsForList().rightPush(key, value);
	}
	
	/**
	 * 出队
	 * @param key
	 * @param redisTemplate
	 * @return
	 */
	public String out(String key,StringRedisTemplate redisTemplate){
		return redisTemplate.opsForList().rightPop(key);
	}
	
	/**
	 * 栈/队列长
	 * @param key
	 * @return
	 */
	public Long length(String key,StringRedisTemplate redisTemplate){
		return redisTemplate.opsForList().size(key);
	}
	
	/**
	 * 范围检索
	 * @param key
	 * @param start
	 * @param end
	 * @param redisTemplate
	 * @return
	 */
	public List<String> range(String key ,int start,int end,StringRedisTemplate redisTemplate){
		return redisTemplate.opsForList().range(key, start, end);
	}
	
	/**
	 * 移除
	 * @param key
	 * @param i
	 * @param value
	 * @param redisTemplate
	 */
	public void remove(String key ,long i ,String value,StringRedisTemplate redisTemplate){
		redisTemplate.opsForList().remove(key, i, value);
	}
	
	/**
	 * 检索
	 * @param key
	 * @param index
	 * @param redisTemplate
	 * @return
	 */
	public String index(String key,long index,StringRedisTemplate redisTemplate){
		return redisTemplate.opsForList().index(key, index);
	}
	
	/**
	 * 置值
	 * @param key
	 * @param index
	 * @param value
	 * @param redisTemplate
	 */
	public void set(String key,long index,String value,StringRedisTemplate redisTemplate){
		redisTemplate.opsForList().set(key, index, value);
	}
	
	/**
	 * 裁剪
	 * @param key
	 * @param start
	 * @param end
	 * @param redisTemplate
	 */
	public void trim(String key,long start,int end,StringRedisTemplate redisTemplate){
		redisTemplate.opsForList().trim(key, start, end);
	}
	
	
	
	/**
	 * 测试代码
	 * @param args
	 */
	public static void main(String[] args) {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("10.164.194.142");//可以通过redisProperties类获取
		connectionFactory.setPort(6379);
		//connectionFactory.setPassword("redispasswd");
		connectionFactory.afterPropertiesSet();//初始化
		StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
		redisTemplate.afterPropertiesSet();//初始化
//		redisTemplate.opsForList().leftPush("list", "小刘");
//		redisTemplate.opsForList().leftPush("list", "xiaoliu"); // 这是先进 后出 原则
//		redisTemplate.opsForList().remove("list", 0,"xiaoliu");//删除，key和value要一样
		
		redisTemplate.opsForList().rightPush("list", "hehe");//先进先出
		redisTemplate.opsForList().rightPush("list", "wawa");
		System.out.println(redisTemplate.opsForList().index("list", 0));
		
	}
}
