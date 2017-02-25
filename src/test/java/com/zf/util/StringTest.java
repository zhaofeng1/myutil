package com.zf.util;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class StringTest {

	public static void main(String[] args) {
		// ios:
		// 200*200
		// 210*140
		// 250*200
		// 250*250
		// 532*108
		// 600*400
		// 640*100
		// 640*120
		// 640*160
		// 640*240
		// 640x744
		// 640x920
		// 640*1136
		// 720x1038
		//
		//
		// android:
	}

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
}
