package com.zf.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

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

	@Test
	public void test2() {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		Map<String, Double> countryMap = new HashMap<String, Double>();
		countryMap.put("us", 1.5);
		countryMap.put("cs", 1.2);
		countryMap.put("tw", 1.5);
		countryMap.put("in", 1.5);

		for (String s : countryMap.keySet()) {
			String value = countryMap.get(s) + "";
			if (map.containsKey(value)) {
				map.get(value).add(s);
			} else {
				Set<String> temp = new HashSet<String>();
				temp.add(s);
				map.put(value, temp);
			}
		}
		System.out.println(JSON.toJSONString(map));

	}

	@Test
	public void test3() {
		Map<String, String> map = new HashMap<>();
		map.put("a", "b");
		String str = map.get("a");
		str = "";
		System.out.println(JSON.toJSONString(map));
	}

	@Test
	public void test4() {
		Map<String, String> map = new HashMap<>();
		map.put("a", "b");
		map.put("c", "b");
		map.put("b", "b");
		System.out.println(JSON.toJSONString(map));
		Map<String, String> treemap = new LinkedHashMap();
		treemap.put("a", "b");
		treemap.put("c", "b");
		treemap.put("b", "b");
		System.out.println(JSON.toJSONString(treemap));
	}
}
