package com.zf.jedis;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;

public class JedisTest {

	private JedisPool jedisPool;

	@Before
	public void init() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(100);
		String ip = "10.200.10.221";
		int port = 6379;
		jedisPool = new JedisPool(config, ip, port);
	}

	@Test
	public void test() {
		Set<String> set = getKeys("*");
		System.out.println("set:" + JSON.toJSONString(set));
	}

	public Set<String> getKeys(String reg) {
		Jedis jedis = getJedis();
		try {
			if (StringUtils.isNotEmpty(reg)) {
				return jedis.keys(reg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	public Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		jedis.select(0);
		return jedis;
	}

	public void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResourceObject(jedis);
			// log.info("return jedis");
		}
	}
}
