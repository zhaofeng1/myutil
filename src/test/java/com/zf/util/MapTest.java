package com.zf.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class MapTest {

	@Test
	public void test() {
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> map = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();

		Map<String, String> appMap = map.putIfAbsent("a", new ConcurrentHashMap<String, String>());
		System.out.println(appMap);
	}

	@Test
	public void test1() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "1");
		map.put("b", "1");

		if (map.containsKey("a")) {
			System.out.println("1");
		} else if (map.containsKey("b")) {
			System.out.println("2");
		}
	}
}
