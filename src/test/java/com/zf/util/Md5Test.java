package com.zf.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class Md5Test {

	@Test
	public void test() {
		long l = System.currentTimeMillis();
		System.out.println(l / 1000);
	}

	@Test
	public void md5Test() {

		//token e63e957ef23e982c997449a88a17d780
		//		long time = 1497006088;
		long time = System.currentTimeMillis() / 1000;
		String key = "30B7IV1U9X4EAAHXQLIG";

		//		System.out.println(md5);
		System.out.println("time：" + time);
		System.out.println(md5(key + md5(String.valueOf(time))));
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
