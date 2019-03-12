package com.zf.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
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

	@Test
	public void md5Test1() {
		System.out.println(md5("3bbeb2d3f1e29dd61357268ba56286a0"));
	}

	@Test
	public void fileMd5Test() {

		String path = "E:/logs/filemd5/a.jpg";
		String path1 = "E:/logs/filemd5/a1.jpg";
		String path2 = "E:/logs/filemd5/b.png";
		String path3 = "E:/logs/filemd5/b1.jpg";
		try {
			String md5 = DigestUtils.md5Hex(new FileInputStream(path));
			String md51 = DigestUtils.md5Hex(new FileInputStream(path1));
			System.out.println("path md5:" + md5);
			System.out.println("path1 md5:" + md51);
			String md52 = DigestUtils.md5Hex(new FileInputStream(path2));
			String md53 = DigestUtils.md5Hex(new FileInputStream(path3));
			System.out.println("path2 md5:" + md52);
			System.out.println("path3 md5:" + md53);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void fileMd5Test1() {

		String path = "E:/logs/filemd5/a.jpg";

		try {
			InputStream is = new FileInputStream(path);
			String md5 = DigestUtils.md5Hex(is);
			System.out.println("md5:" + md5);
			BufferedImage src = ImageIO.read(new File(path));

			System.out.println("width:" + src.getWidth());
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
