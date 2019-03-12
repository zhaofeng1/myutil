package com.zf.mobvista;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class MobimaxTest {

	@Test
	public void test() {
		String appid = "1049";
		String url = "http://api.mobimax.cn/api_offline";
		long time = System.currentTimeMillis();
		String param = "appid=" + appid + "&time=" + time;
		String sign = md5(param + "shareit");

		System.out.println(url + "?" + param + "&sign=" + sign);
	}

	@Test
	public void test1() {
		//http://api.mobimax.cn/api_offline/click?appid=1049&time=1511421601711&adid=2178785
		String url = "http://api.mobimax.cn/api_offline/click";
		String param = "appid=1049&time=1511421601711&adid=2178785";
		String sign = md5(param + "shareit");
		System.out.println(url + "?" + param + "&sign=" + sign);
	}

	/**
	 * 使用md5的算法进行加密
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
}
