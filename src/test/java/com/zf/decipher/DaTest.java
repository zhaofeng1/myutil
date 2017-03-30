package com.zf.decipher;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class DaTest {

	@Test
	public void test() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform", "android");
		map.put("app_version", "1.0.0");
		map.put("sdk_version", "1.3");
		map.put("aid", "56b08e70a1d89672");
		map.put("app_name", "server.client");

		System.out.println(DataEn.encrypt(JSON.toJSONString(map)));
	}
}
