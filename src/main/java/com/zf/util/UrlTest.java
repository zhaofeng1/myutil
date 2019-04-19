package com.zf.util;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlTest {

	@Test
	public void test() throws MalformedURLException {

		String s = "http://ssp.nantmob.com/aff/ssp/click?channel=5482&id=410920729&aoid=21022&uuid=246481023010111401321513923390153&country=SA&aid={aid}&";

		URL clickURL = new URL(s);
		String query = clickURL.getQuery();
		System.out.println("query:" + query);
		System.out.println(clickURL.getHost());
	}

	@Test
	public void testUrlbuild() {

		String url = "http://ssp.nantmob.com/aff/ssp/click?channel=5482&id=410920729&aoid=21022&uuid=246481023010111401321513923390153&country=SA";

		try {
			URIBuilder builder = new URIBuilder(url);
			System.out.println(builder.build().toString());
			System.out.println(builder.getQueryParams().get(0).getName());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
