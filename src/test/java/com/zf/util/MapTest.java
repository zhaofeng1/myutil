package com.zf.util;

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
}
