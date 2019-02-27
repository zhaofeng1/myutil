package com.zf.mobvista;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class mobvistaTest {

	@Test
	public void test() {
		String appid = "92389";
		String apiKey = "d3a1cd6dfd1bff3c5e392b9b6d37894d";
		String unitid = "20060";

		System.out.println(md5(appid + apiKey));
	}

	@Test
	public void test1() {
		long time = (System.currentTimeMillis() / 1000);
		//		String apiKey = "5ZOAOUOMCWI84SK6ANFC";
		//		String apiKey = "2VMMOHWSXB01VE1RNNRA";
		//		String url = "http://3s.mobvista.com/v4.php?m=index&cb=cb12663&time={0}&token={1}";
		//		String apiKey = "BAM2IGWNJUHAZ5CETRN7";
		//		String url = "http://3s.mobvista.com/v4.php?m=index&cb=cb12867&time={0}&token={1}";
		//		String apiKey = "GZ06YSNTYVLSCRXJSZ59";//10822
		//		String url = "http://3s.mobvista.com/v4.php?m=index&cb=cb12465&time={0}&token={1}&offset={2}";
		String apiKey = "2VMMOHWSXB01VE1RNNRA";//10144
		String url = "http://3s.mobvista.com/v4.php?m=index&cb=cb9864&time={0}&token={1}&platform=android&offset={2}";
		//		String apiKey = "CFBMLRDDF8LSYVCZDQFI";//10824
		//		String url = "http://3s.mobvista.com/v4.php?m=index&cb=cb9864&time={0}&token={1}&platform=android&offset={2}";
		
		String token = md5(apiKey + md5(time + ""));
		System.out.println(time);
		System.out.println(token);
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
