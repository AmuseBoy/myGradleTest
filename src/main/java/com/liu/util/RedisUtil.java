package com.liu.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis读写功能类
 * Created by Liuhongbin on 2016/4/7.
 */
public class RedisUtil {
    private static Log logger = LogFactory.getLog(RedisUtil.class);

    private static final int DEFAULT_EXPIRE_TIME = 4 * 60 * 60;
    private static final int MAX_TOTAL = 10000;
    private static final int MAX_IDLE = 2000;
    private static final int MAX_WAIT_MILLIS = 1000 * 60;

//    通过注解获取配置信息
//    private static String redisHost = RedisProperties.getHost();
//    private static int redisPort = Integer.parseInt(RedisProperties.getPort());



    private static String redisHost = "10.164.194.142";
    private static int redisPort = 6379;

    private static JedisPool pool = null;

    private synchronized static JedisPool getPool() {
        if (pool == null) {
//            redisHost = RedisProperties.getHost();
//            redisPort = Integer.parseInt(RedisProperties.getPort());
            JedisPoolConfig config = new JedisPoolConfig();
            //可用连接实例的最大数目，默认值为8；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(MAX_TOTAL);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(MAX_IDLE);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, redisHost, redisPort);
        }
        return pool;
    }

    public synchronized static void flush() {
        if (pool != null) {
            if (!pool.isClosed()) {
                pool.close();
            }
            pool = null;
        }
    }

    public synchronized static Jedis getJedis() {
        Jedis j;
        try {
            logger.debug(String.format("获取Jedis实例: active:%d, idle:%d, waiters:%d.",
                    getNumActive(), getNumIdle(), getNumWaiters()));
            j = getPool().getResource();
        } catch (Exception e) {
            logger.error(String.format("获取Jedis实例异常: active:%d, idle:%d, waiters:%d. - %s",
                    getNumActive(), getNumIdle(), getNumWaiters(), e.getMessage()));
            flush();
            j = getPool().getResource();
        }
        return j;
    }

    public synchronized static void returnResource(Jedis redis) {
        if (redis != null) {
            try {
                redis.close();
            } catch (Exception e) {
                logger.warn("RedisUtil.returnResource - " + e.getMessage());
            }
        } else {
//            logger.warn("RedisUtil.returnResource - 无法释放Jedis实例");
        }
    }

    public static String set(String key, String value) {
        Jedis j = null;
        try {
            validateValue(key, value);
            j = getJedis();
            return j.set(key, value);
        } catch (Exception e) {
            logger.error("RedisUtil.set(${key},${value}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String get(String key) {
        Jedis j = null;
        try {
            j = getJedis();
            return j.get(key);
        } catch (Exception e) {
            logger.error("RedisUtil.get(${key}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String mset(String... keyvalues) {
        Jedis j = null;
        try {
            validateValue(keyvalues);
            j = getJedis();
            return j.mset(keyvalues);
        } catch (Exception e) {
            logger.error("RedisUtil.mset(${keyvalues}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public List<String> mget(String... keys) {
        Jedis j = null;
        try {
            j = getJedis();
            return j.mget(keys);
        } catch (Exception e) {
            logger.error("RedisUtil.mget(${keys}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long del(String... keys) throws IOException {
        Jedis j = null;
        try {
            validateValue(keys);
            j = getJedis();
            Long v = j.del(keys);
            for (int i = 0; i < keys.length; i++) {
                j.del(serialize(keys[i]));
            }
            return v;
        } catch (Exception e) {
            logger.error("RedisUtil.del(${keys}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Set<String> keys() {
        Jedis j = null;
        try {
            j = getJedis();
            return j.keys("*");
        } catch (Exception e) {
            logger.error("RedisUtil.keys() - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Set<String> keys(String key) {
        Jedis j = null;
        try {
            j = getJedis();
            return j.keys("*" + key + "*");
        } catch (Exception e) {
            logger.error("RedisUtil.keys(${key}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String hget(String key, String field) {
        Jedis j = null;
        try {
            j = getJedis();
            return j.hget(key, field);
        } catch (Exception e) {
            logger.error("RedisUtil.hget(${key},${field}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long hset(String key, String field, String value) {
        Jedis j = null;
        try {
            validateValue(key, field, value);
            j = getJedis();
            return j.hset(key, field, value);
        } catch (Exception e) {
            logger.error("RedisUtil.hset(${key},${field},${value}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static byte[] hget(byte[] key, byte[] field) {
        Jedis j = null;
        try {
            j = getJedis();
            return j.hget(key, field);
        } catch (Exception e) {
            logger.error("RedisUtil.hget(${key},${field}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long hset(byte[] key, byte[] field, byte[] value) {
        Jedis j = null;
        try {
            validateValue(key, field, value);
            j = getJedis();
            return j.hset(key, field, value);
        } catch (Exception e) {
            logger.error("RedisUtil.hset(${key},${field},${value}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String hmset(String key, Map<String, String> hash) {
        Jedis j = null;
        try {
            validateValue(key, hash);
            j = getJedis();
            return j.hmset(key, hash);
        } catch (Exception e) {
            logger.error("RedisUtil.hmset(${key},${hash}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String hmsetPexpireAt(String key, Map<String, String> hash, long l) {
        Jedis j = null;
        try {
            validateValue(key, hash);
            j = getJedis();
            String v = j.hmset(key, hash);
            j.pexpireAt(key, l);
            return v;
        } catch (Exception e) {
            logger.error("RedisUtil.hmsetPexpireAt(${key},${hash},${l}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String hmsetExpire(String key, Map<String, String> hash, int i) {
        Jedis j = null;
        try {
            validateValue(key, hash);
            j = getJedis();
            String v = j.hmset(key, hash);
            j.expire(key, i);
            return v;
        } catch (Exception e) {
            logger.error("RedisUtil.hmsetExpire(${key},${hash},${i}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static List<String> hmget(String key, String... fields) {
        Jedis j = null;
        try {
            validateValue(key, fields);
            j = getJedis();
            return j.hmget(key, fields);
        } catch (Exception e) {
            logger.error("RedisUtil.hmget(${key},${fields}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Map<String, String> hgetAll(String key) {
        Jedis j = null;
        try {
            j = getJedis();
            return j.hgetAll(key);
        } catch (Exception e) {
            logger.error("RedisUtil.hgetAll(${key}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long hmdel(String key, String... fields) {
        Jedis j = null;
        try {
            validateValue(key, fields);
            j = getJedis();
            return j.hdel(key, fields);
        } catch (Exception e) {
            logger.error("RedisUtil.hmdel(${key},${fields}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long hmdel(final byte[] key, final byte[]... field) {
        Jedis j = null;
        try {
            validateValue(key, field);
            j = getJedis();
            return j.hdel(key, field);
        } catch (Exception e) {
            logger.error("RedisUtil.hmdel(${key},${field}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long rpush(String key, String... strings) {
        Jedis j = null;
        try {
            validateValue(key, strings);
            j = getJedis();
            return j.rpush(key, strings);
        } catch (Exception e) {
            logger.error("RedisUtil.rpush(${key},${strings}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long lpush(String key, String... strings) {
        Jedis j = null;
        try {
            validateValue(key, strings);
            j = getJedis();
            return j.lpush(key, strings);
        } catch (Exception e) {
            logger.error(String.format("RedisUtil.lpush(%s, %s) - %s}", key, strings, e.getMessage()));
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String rpop(String key) {
        Jedis j = null;
        try {
            validateValue(key);
            j = getJedis();
            return j.rpop(key);
        } catch (Exception e) {
            logger.error(String.format("RedisUtil.rpop(%s) - %s", key, e.getMessage()));
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String lpop(String key) {
        Jedis j = null;
        try {
            validateValue(key);
            j = getJedis();
            return j.lpop(key);
        } catch (Exception e) {
            logger.error("RedisUtil.lpop(${key}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long llen(String key) {
        Jedis j = null;
        try {
            validateValue(key);
            j = getJedis();
            return j.llen(key);
        } catch (Exception e) {
            logger.error("RedisUtil.llen(${key}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static List<String> lrange(String key, long start, long end) {
        Jedis j = null;
        try {
            validateValue(key);
            j = getJedis();
            return j.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("RedisUtil.lrange(${key},${start},${end}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static Long lrem(String key, long count, String value) {
        Jedis j = null;
        try {
            validateValue(key, value);
            j = getJedis();
            return j.lrem(key, count, value);
        } catch (Exception e) {
            logger.error("RedisUtil.lrem(${key},${count},${value}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String lset(String key, long index, String value) {
        Jedis j = null;
        try {
            validateValue(key, value);
            j = getJedis();
            return j.lset(key, index, value);
        } catch (Exception e) {
            logger.error("RedisUtil.lset(${key},${index},${value}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String ltrim(String key, long start, long end) {
        Jedis j = null;
        try {
            validateValue(key);
            j = getJedis();
            String v = j.ltrim(key, start, end);
            return j.ltrim(key, start, end);
        } catch (Exception e) {
            logger.error("RedisUtil.ltrim(${key},${start},${end}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String rpoplpush(String srckey, String dstkey) {
        Jedis j = null;
        try {
            validateValue(srckey, dstkey);
            j = getJedis();
            return j.rpoplpush(srckey, dstkey);
        } catch (Exception e) {
            logger.error("RedisUtil.rpoplpush(${srckey},${dstkey}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static String setObject(String key, Object o, int i) throws IOException {
        Jedis j = null;
        try {
            validateValue(key, o);
            j = getJedis();
            String v = j.set(serialize(key), serialize(o));
            if (i != -1) {
                j.expire(serialize(key), i);
            }
            logger.warn("redis保存对象");
            return v;
        } catch (Exception e) {
            logger.error("RedisUtil.setObject(${key},${o},${i}) - ${e.getMessage()}");
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static String setObject(String key, Object o) throws IOException {
        return setObject(key, o, DEFAULT_EXPIRE_TIME);
    }

    public static Object getObject(String key) throws IOException, ClassNotFoundException {
        Jedis j = null;
        try {
            validateValue(key);
            j = getJedis();
            byte[] v = j.get(serialize(key));
            if (v == null) {
                return null;
            }
            return unserialize(v);
        } catch (Exception e) {
            logger.error(String.format("RedisUtil.getObject(%s) - %s", key, e.getMessage()));
        } finally {
            returnResource(j);
        }
        return null;
    }

    public static void validateValue(Object... value) throws Exception {
        if (value == null) {
            throw new Exception("Redis无法处理值为null的数据");
        }
        for (Object obj : value) {
            if (obj == null) {
                throw new Exception("Redis无法处理值为null的数据");
            }
            if (obj instanceof Map) {
                Iterator iterator = ((Map) obj).entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> e = (Map.Entry<String, Object>) iterator.next();
                    if (e.getValue() == null) {
                        throw new Exception("Redis无法处理值为null的数据");
                    }
                }
            }
        }
    }

    public static int getNumActive() {
        if (pool != null && !pool.isClosed()) {
            return pool.getNumActive();
        }
        return -1;
    }

    public static int getNumIdle() {
        if (pool != null && !pool.isClosed()) {
            return pool.getNumIdle();
        }
        return -1;
    }

    public static int getNumWaiters() {
        if (pool != null && !pool.isClosed()) {
            return pool.getNumWaiters();
        }
        return -1;
    }

    public static String getRedisHost() {
        return redisHost;
    }

    public static void setRedisHost(String redisHost) {
        RedisUtil.redisHost = redisHost;
    }

    public static int getRedisPort() {
        return redisPort;
    }

    public static void setRedisPort(int redisPort) {
        RedisUtil.redisPort = redisPort;
    }
    
    
    
    
    
    public static void main(String[] args) throws IOException {
//    	JSONObject json = new JSONObject("{'success':false}");
//    	
////        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////        ObjectOutputStream oos = new ObjectOutputStream(baos);
////        oos.writeObject(json.toString());
////        System.out.println(baos.toByteArray().toString());
//    	String v = RedisUtil.setObject("ihaier-b2b:450602197711010119", json.toString(), 100);
//    	System.out.println(v);
    	
//    	RedisUtil.set("batchNo:1", "100");
//    	RedisUtil.get("");


        RedisUtil.setObject("hehehe","sdsd",500);
	}
    
    
    
    
}
