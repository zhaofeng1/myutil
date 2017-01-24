package com.zf.util;

import java.util.Random;

import org.junit.Test;

public class SwitchCaseTest {

	@Test
	public void test() {
		Random r = new Random();
		int i = r.nextInt(5);
		System.out.println("i:" + i);

		switch (i) {
		case 1:
			System.out.println("a");
			break;
		case 2:
		case 3:
		case 4:
			System.out.println("c");
			break;
		default:
			System.out.println("b");
			break;
		}
	}
}
