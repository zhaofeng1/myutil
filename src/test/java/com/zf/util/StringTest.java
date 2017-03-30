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

	@Test
	public void testLog() {
		String s = "{\"platform\":\"android\",\"app_version\":\"1.7.4\",\"robotId\":\"79818082567d86fe\",\"trackingUrl\":\"http://app.appsflyer.com/id.co.babe?pid=appcoachs_int&c=Appcoach250_21069&clickid=fc-00051CA43281A1490142872388316&android_id=&advertising_id=&af_enc_data=pqEQ7Yves8DpTKDGKVVHX2bgfPUrhZ5frgqYqeNPqwCvlkVUN7ioQXkyUfljIt1Usxz0kRkO%0AVTIQfVJmlYfBjg%3D%3D%0A\",\"sdk_version\":\"1.3\",\"clickId\":\"130151023010154221490142855390\",\"referer\":\"af_tranid=YFxrayrF2pA6uOKkkFDyqg&pid=appcoachs_int&c=Appcoach250_21069&clickid=fc-00051CA43281A1490142872388316&af_enc_data=pqEQ7Yves8DpTKDGKVVHX2bgfPUrhZ5frgqYqeNPqwCvlkVUN7ioQXkyUfljIt1Usxz0kRkO\nVTIQfVJmlYfBjg==\n&utm_source=appcoachs_int&utm_campaign=Appcoach250_21069\",\"clickResult\":\"1\",\"finalUrl\":\"https://play.google.com/store/apps/details?id=id.co.babe&referrer=af_tranid=YFxrayrF2pA6uOKkkFDyqg&pid=appcoachs_int&c=Appcoach250_21069&clickid=fc-00051CA43281A1490142872388316&af_enc_data=pqEQ7Yves8DpTKDGKVVHX2bgfPUrhZ5frgqYqeNPqwCvlkVUN7ioQXkyUfljIt1Usxz0kRkO\nVTIQfVJmlYfBjg==\n&utm_source=appcoachs_int&utm_campaign=Appcoach250_21069\",\"app_name\":\"com.wifiup\"}";
		System.out.println(s.replace("\n", ""));
	}

}
