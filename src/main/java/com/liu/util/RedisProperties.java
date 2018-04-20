package com.liu.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用于获取配置文件的参数
 * @author 刘培振 liupeizhen@bestvike.com
 *
 */
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private static String host;
    private static String port;
    
    public static String getHost() {
        return host;
    }
    public static void setHost(String host) {
        RedisProperties.host = host;
    }
    public static String getPort() {
        return port;
    }
    public static void setPort(String port) {
        RedisProperties.port = port;
    }
}
