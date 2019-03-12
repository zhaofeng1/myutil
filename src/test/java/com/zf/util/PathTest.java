package com.zf.util;

import org.junit.Test;

public class PathTest {

	@Test
	public void test() {
		String path = this.getClass().getClassLoader().getResource("").toString();
		System.out.println(path);
	}
}
