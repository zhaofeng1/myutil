package com.zf.util;

import java.math.BigInteger;
import java.text.DecimalFormat;

import org.junit.Test;

public class IntTest {

	@Test
	public void testInt() {
		int i = 100_000;
		System.out.println(100 * i);
	}

	@Test
	public void test1() {

		System.out.println(Integer.MAX_VALUE);
		System.out.println(7 * 24 * 60 * 60);
	}

	@Test
	public void test2() {
		String a = "3111111111";
		BigInteger b = new BigInteger(a);
		System.out.println(b);
		System.out.println(b.intValue());
	}

	@Test
	public void test3() {
		//		int a = 1400;
		//		int b = 500;
		//		System.out.println(a / b);
		Integer test = 123;
		System.out.println(Double.valueOf(test));
	}

	@Test
	public void test4() {
		String cap = "100";
		double payout = 0.68;
		DecimalFormat df = new DecimalFormat("#.##");
		if (cap != null && Double.valueOf(cap) > 0) {
			System.out.println(Double.valueOf(cap) * Double.valueOf(payout));
			System.out.println(df.format(Double.valueOf(cap) * Double.valueOf(payout)));
		}
	}
}
