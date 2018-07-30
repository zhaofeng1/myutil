package com.zf.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;


public class RedisUtils {

	private JedisPool jedisPool;

	private static final Logger log = Logger.getLogger(RedisUtils.class);

	private static final String SUCCESS_RESPONSE_FLAG = "OK";

	// seconds unit
	private static final int EXPIRE_TIME = 2 * 24 * 60 * 60;

	private RedisUtils() {
		init();
	}

	private void init() {
		//		redis.ip=sg-redis-new.jo1mjq.0001.apse1.cache.amazonaws.com
		//		redis.port=6379
		//		redis.pool.maxTotal=1024
		//		redis.pool.maxIdle=200
		//		redis.pool.maxWait=1000
		//		redis.pool.testOnBorrow=false
		//		redis.pool.testOnReturn=true
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1024);
		config.setMaxIdle(200);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(true);
		String ip = "sg-redis-new.jo1mjq.0001.apse1.cache.amazonaws.com";
		int port = 6379;
		jedisPool = new JedisPool(config, ip, port);
		log.info("init redis pool[ip:" + ip + ",port:" + port + "]");
	}

	private static class RedisUtilsHolder {
		private static final RedisUtils INSTANCE = new RedisUtils();
	}

	public static RedisUtils getInstance() {
		return RedisUtilsHolder.INSTANCE;
	}

	public Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		// log.info("get jedis");
		return jedis;
	}

	public void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResourceObject(jedis);
			// log.info("return jedis");
		}
	}

	public boolean setMap(Jedis jedis, Map<String, String> map, String jedisKey) {
		boolean flag = false;
		if (map != null) {
			String result = jedis.hmset(jedisKey, map);
			jedis.expire(jedisKey, EXPIRE_TIME);
			if (SUCCESS_RESPONSE_FLAG.equals(result)) {
				flag = true;
			}
		}
		return flag;
	}

	public Map<String, String> getMap(Jedis jedis, String jedisKey) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> keysIterator = jedis.hkeys(jedisKey).iterator();
		while (keysIterator.hasNext()) {
			String key = keysIterator.next();
			String value = jedis.hget(jedisKey, key);
			map.put(key, value);
		}
		return map.size() == 0 ? null : map;
	}

	public boolean delete(Jedis jedis, String jedisKey) {
		boolean result = false;
		Long affactedRows = jedis.del(jedisKey);
		if (affactedRows > 0)
			result = true;
		return result;
	}

	public boolean setString(Jedis jedis, String jedisKey, String value) {
		String result = jedis.set(jedisKey, value);
		jedis.expire(jedisKey, EXPIRE_TIME);
		return SUCCESS_RESPONSE_FLAG.equals(result) ? true : false;
	}

	public String getString(Jedis jedis, String jedisKey) {
		return jedis.get(jedisKey);
	}

	public static void main(String[] args) {
		Jedis jedis = RedisUtils.getInstance().getJedis();
		jedis.select(4);
		Map<String, String> map = jedis.hgetAll("cache_offer_161791053");
		System.out.println(JSON.toJSONString(map));
		RedisUtils.getInstance().returnResource(jedis);
	}

}
