package com.zf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * random 测试
 * @author ZhaoFeng
 * @date 2017年3月28日
 */
public class RandomTest {

	@Test
	public void test() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			System.out.println(r.nextInt(list.size()));
		}
	}

	/**
	 * 
	 */
	@Test
	public void test1() {
		int a = 4;
		int b = 4;
		System.out.println(a >= b);
	}

	/**
	 * 
	 */
	@Test
	public void test2() {
		for (int i = 0; i < 10; i++) {
			System.out.println(new Random().nextInt(2));
		}
	}

	@Test
	public void test3() {
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			System.out.println(r.nextBoolean());
		}
	}
}
