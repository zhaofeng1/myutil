package com.zf.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

public class UrlEncodeTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		String str = "{\"ads\": [{\"excludedCreatives\": [],\"adViewId\": \"smartAdwall\",\"isAdWall\": \"false\",\"sendRepeat\": false,\"size\": \"500\",\"startIndex\": \"0\"}],\"apiVersion\": \"3\",\"deviceLanguage\": \"en\",\"deviceX\": \"768\",\"deviceY\": \"1192\",\"hashCode\": \"QEfsvwoQFwnjC0VCU8H8PEBLHzjv%2BTlMTjjhQklKBuj0PUBDGQm0vQsP\",\"isMobile\": \"true\",\"directImageUrl\":\"1\",\"currencyCode\":\"USD\",\"requestSource\": \"PUBLIC\"}";

		String result = URLEncoder.encode(str, "gbk");
		System.out.println(result);
	}

	@Test
	public void test1() throws UnsupportedEncodingException {
		String url = "http://api.inplayable.com/api/offline/postback?subsite={affiliate_id}&transaction_id={transaction_id}&parameter={parameter}";
		System.out.println(URLEncoder.encode(url, "gbk"));
	}
}
