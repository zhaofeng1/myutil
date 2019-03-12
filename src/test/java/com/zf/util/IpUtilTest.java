package com.zf.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class IpUtilTest {

	@Test
	public void test() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();

		System.out.println(ip);
	}
}
