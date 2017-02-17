package com.zf.util;

import java.util.UUID;

import org.junit.Test;

public class StringTest {

	public static void main(String[] args) {
		// ios:
		// 200*200
		// 210*140
		// 250*200
		// 250*250
		// 532*108
		// 600*400
		// 640*100
		// 640*120
		// 640*160
		// 640*240
		// 640x744
		// 640x920
		// 640*1136
		// 720x1038
		//
		//
		// android:
	}

	@Test
	public void test() {
		boolean b = false;
		System.out.println(b ? "a" : "b");
		System.out.println(UUID.randomUUID().toString());
		String a = null;
		System.out.println(a.hashCode());
	}
}
