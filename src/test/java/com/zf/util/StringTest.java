package com.zf.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

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

	@Test
	public void test1() {
		String str = "123{a}basd";
		System.out.println(str.contains("{a}"));
		if (str.contains("{a}")) {
			System.out.println(str.replace("{a}", "tt"));
		}
	}

	@Test
	public void test2() {
		String t = "12";
		String[] array = t.split(",");
		System.out.println(JSON.toJSONString(array));
	}

	@Test
	public void replace() {
		String payout = "$123";
		String str = String.valueOf((char) 36);
		System.out.println(str);
		if (payout.contains(String.valueOf((char) 36))) {
			payout = payout.replace(str, "");
		}
		System.out.println(payout);
	}

	@Test
	public void test3() {
		String url = "http://apptrknow.com/dir/redirect?c=559&offerId=20331830&affid=345&cid=[cid]&data1=&data2=[data2]&data3=[data3]&data4=[data4]&affsub1=[affsub1]&device_id=[device_id]&idfa=[idfa]&gaid=[gaid]";
		if (url.contains("?")) {
			int qIndex = url.indexOf("?") + 1;
			String tempUrl = url.substring(0, qIndex);
			System.out.println(tempUrl);
			String param = url.substring(qIndex);
			System.out.println(param);

			Map<String, String> paramMap = parseMap(param);

			List<String> list = new ArrayList<String>();
			list.add("cid");
			list.add("data1");
			list.add("data2");
			list.add("data3");
			list.add("data4");
			list.add("affsub1");
			list.add("device_id");
			list.add("idfa");
			list.add("gaid");

			for (String s : list) {
				if (paramMap.containsKey(s)) {
					paramMap.remove(s);
				}
			}

		}
	}

	public Map<String, String> parseMap(String param) {
		Map<String, String> map = new HashMap<String, String>();
		String[] array = param.split("&");
		for (String s : array) {
			String[] keyvalue = s.split("=");
			if (keyvalue.length == 2) {
				map.put(keyvalue[0], keyvalue[1] == null ? "" : keyvalue[1]);
			} else if (keyvalue.length == 1) {
				map.put(keyvalue[0], "");
			}
		}
		System.out.println(JSON.toJSONString(map));
		return map;
	}

	@Test
	public void testUrl() throws MalformedURLException {
		String clickUrl = "http://52.77.99.53/acs.php?sid=106157&adid=6120174";
		URL clickURL = new URL(clickUrl);
		String domain = clickURL.getHost();
		System.out.println(domain);
	}

	@Test
	public void testBoolean() {

		List<String> list = new ArrayList<String>();
		handle(list);
		System.out.println(list.size());
	}

	private void handle(List<String> list) {
		list.add("a");
	}
}
