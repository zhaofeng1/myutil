package com.zf.util;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class StringTest {

	@Test
	public void test() {
		boolean b = false;
		System.out.println(b ? "a" : "b");
		System.out.println(UUID.randomUUID().toString());
		String a = null;
		System.out.println(a.hashCode());
	}

	@Test
	public void testString() {
		String str = "Android 5.1.1";
		//		if (str.contains("android")) {
		//			System.out.println("1");
		//		}
		//		if (str.contains("Android")) {
		//			System.out.println("2");
		//		}
		Pattern p = Pattern.compile("\\d|\\.");
		Matcher m = p.matcher(str);
		ArrayList<String> strs = new ArrayList<String>();
		String result = "";
		while (m.find()) {
			//			strs.add(m.group());
			result += m.group();
		}
		System.out.println(result);
		//		for (String s : strs) {
		//			System.out.println(s);
		//		}
	}

	@Test
	public void testSplit() {
		String str = "Nantong Shi (Chongchuan Qu)";
		str = "Beijing (Haidian Qu)";
		System.out.println(str.contains("("));
		String city = str.substring(0, str.indexOf("(")).trim();
		//		if (city.contains("Shi")) {
		//			
		//		}
		System.out.println(city);
		String area = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
		System.out.println(area);
	}

	/**
	 * 中文
	 */
	@Test
	public void testChinese() {

	}
}
