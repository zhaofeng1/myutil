package com.zf.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class UrlTest {

	@Test
	public void test() throws MalformedURLException {

		String s = "http://ssp.nantmob.com/aff/ssp/click?channel=5482&id=410920729&aoid=21022&uuid=246481023010111401321513923390153&country=SA&aid={aid}&";

		URL clickURL = new URL(s);
		String query = clickURL.getQuery();
		System.out.println("query:" + query);
		System.out.println(clickURL.getHost());
	}
}
