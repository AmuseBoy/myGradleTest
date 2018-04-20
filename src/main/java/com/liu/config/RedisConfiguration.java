package com.liu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfiguration {

//	@Bean
//	@ConfigurationProperties(prefix = "spring.redis")
//	public JedisConnectionFactory connectionFactory(){
//		return new JedisConnectionFactory();
//	}
	
	
	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory){
		return new StringRedisTemplate(connectionFactory);
	}
}
