package com.zf.util;

import java.math.BigInteger;

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
		int a = 1400;
		int b = 500;
		System.out.println(a / b);
	}
}
