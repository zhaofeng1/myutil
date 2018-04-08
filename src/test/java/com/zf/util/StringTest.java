package com.zf.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
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

	@Test
	public void test11() {
		String url = "https://app.appsflyer.com/com.cmplay.dancingline?pid=inplayable_int&c={region}&advertising_id={gaid}&clickid={transaction_id}&af_siteid={affiliate_id}&af_sub_siteid={parameter}";
	}

	@Test
	public void test31() {
		String path = "C:/Users/dell/Desktop/adserver/adapter/tmp";
		try {
			List<String> list = FileUtils.readLines(new File(path));
			for (String s : list) {
				int i = Integer.valueOf(s.split("\t")[1]);
				System.out.println(s);
				System.out.println(s.split("\t")[0] + "\t" + (i + 1));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test32() {
		System.out.println(System.currentTimeMillis());
	}

	private static Pattern ANDROID_PKG_PATTERN = Pattern.compile("[a-zA-Z0-9_]*\\.[a-zA-Z0-9_\\.]*");
	private static final Pattern ITUNES_APP_ID_PATTERN = Pattern.compile("([0-9]{1,})");

	@Test
	public void testPattenAndroidAndIos() {
		String android_appid = "123.";
		String ios_appid = "";
		System.out.println(validateAndroidPkg(android_appid));
	}

	public static boolean validateAndroidPkg(String pkg) {
		Matcher m = ANDROID_PKG_PATTERN.matcher(pkg);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static boolean validateIosPkg(String pkg) {
		Matcher m = ITUNES_APP_ID_PATTERN.matcher(pkg);
		if (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public void test33() {
		String ss = "10914,10982,11200,11202,11210,11220,11230,11250,11260,11270,11290,11300,11310,11320,";
		for (String s : ss.split(",")) {
			System.out.println(s);
			System.out.println(Integer.valueOf(s) + 1);
		}

	}

	@Test
	public void test34() {
		String ss = "30694,30590,30358,30639,30474,30534,30648,30717,30651,30222,30441,30521,30475,30419,30706,30525,30145,30496,30520,30075,30653,30633,30405,30698,30214,30200,30431,30205,30628,30309";
		for (String s : ss.split(",")) {
			System.out.println("cat s2sclklocationlog|grep subsite_id=" + s + "|awk  '{print $2}'|head -10 > /export/fhf/zhaofeng/" + s + ".txt");
		}

	}

	@Test
	public void test35() {
		String ss = "http://tracking.vcommission.com/aff_r?offer_id=6964&aff_id=71138&url=https%3A%2F%2Fad.apsalar.com%2Fapi%2Fv1%2Fad%3Fre%3D0%26a%3Dtimesinternet%26i%3Dfree.mobile.internet.data.recharge%26ca%3DVcommission_DB%26an%3DvCommission%26p%3DAndroid%26pl%3DVcommission_DB%26andi%3D%26and1%3D%26s%3D71138%26aifa%3D%26cl%3D%7Bvcomm%7D%26cr%3D6964%26g%3D%7Bgoal_id%7D%26transaction_id%3D10298d13d02eaed6a0840f08aa2068%26h%3De3bad0e63425eeaa710800d9d7bf8215aff2eca1&urlauth=651900837165769317274076733241";
		System.out.println(URLDecoder.decode(ss));

	}

	@Test
	public void test36() {
		String ss = "in|id";
		System.out.println(JSON.toJSONString(ss.split("\\|")));

	}

	@Test
	public void test37() {
		String str = "0.23";
		System.out.println(str.substring(0, str.length() > 5 ? 5 : str.length()));
	}


}
