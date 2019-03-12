package com.zf.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

public class UrlEncodeTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		String str = "{\"ads\": [{\"excludedCreatives\": [],\"adViewId\": \"smartAdwall\",\"isAdWall\": \"false\",\"sendRepeat\": false,\"size\": \"500\",\"startIndex\": \"0\"}],\"apiVersion\": \"3\",\"deviceLanguage\": \"en\",\"deviceX\": \"768\",\"deviceY\": \"1192\",\"hashCode\": \"QEfsvwoQFwnjC0VCU8H8PEBLHzjv%2BTlMTjjhQklKBuj0PUBDGQm0vQsP\",\"isMobile\": \"true\",\"directImageUrl\":\"1\",\"currencyCode\":\"USD\",\"requestSource\": \"PUBLIC\"}";
		str = "5lko7jG+ybpuf4J8kGHu1jmxYHFsluxMMPZCgDFnpUc3+F1/6l1LJB/ZrwhI67rm";

		String result = URLEncoder.encode(str, "gbk");
		System.out.println(result);
	}

	@Test
	public void test1() throws UnsupportedEncodingException {
		String url = "http://api.inplayable.com/api/offline/postback?subsite={affiliate_id}&transaction_id={transaction_id}&parameter={parameter}";
		System.out.println(URLEncoder.encode(url, "gbk"));
	}

	@Test
	public void test2() throws UnsupportedEncodingException {
		//		String url = "{\"affId\":\"22681\",\"placementId\":\"1535\",\"packageName\":\"\",\"subid1\":\"\",\"subid2\":\"\",\"subid3\":\"\",\"subid4\":\"\",\"subid5\":\"\",\"requestSource\":\"PUBLIC\"}";
		//		String url = "{\"ads\":[{\"adViewId\":\"1601\",\"excludedCreatives\":[],\"startIndex\":\"0\"}],\"apiVersion\":\"3\",\"deviceLanguage\":\"en\",\"hashCode\":\"QEfsvwoTEQvpC0VCU8H4OEpMSEXoxTlPTzcVQAhASfS6OD1FSDu9twwRFAw%3D\",\"isMobile\":\"true\",\"requestSource\":\"PUBLIC\",\"currencyCode\":\"USD\"}";
		String url = "{\"affId\":\"22681\",\"placementId\":\"1535\",\"packageName\":\"\",\"subid1\":\"\",\"subid2\":\"\",\"subid3\":\"\",\"subid4\":\"\",\"subid5\":\"\",\"requestSource\":\"PUBLIC\"}";
		System.out.println(URLEncoder.encode(url, "gbk"));
	}

	@Test
	public void test3() throws UnsupportedEncodingException {
		//https://cpactions.com/api/v1.0/feed/public/offers?site_id=41812&hash=154c5edc37d884937b3baf4d61423c7573237c8b&items_per_page=499&page=1&traffic_type=0&filters[os][and][]=android&filters[avg_cr][gt]=0.001
		//		String url = "site_id=41812&hash=154c5edc37d884937b3baf4d61423c7573237c8b&items_per_page=499&page=1&traffic_type=0&filters[os][and][]=android&filters[avg_cr][gt]=0.001";
		String url = "{\"ads\":[{\"adViewId\":\"1601\",\"excludedCreatives\":[],\"startIndex\":\"0\"}],\"apiVersion\":\"3\",\"deviceLanguage\":\"en\",\"hashCode\":\"QEfsvwoTEQvpC0VCU8H4OEpMSEXoxTlPTzcVQAhASfS6OD1FSDu9twwRFAw%3D\",\"isMobile\":\"true\",\"requestSource\":\"PUBLIC\",\"currencyCode\":\"USD\"}";
		System.out.println(URLEncoder.encode(url, "gbk"));
	}

	@Test
	public void test4() throws UnsupportedEncodingException {
		String s = "https%3A%2F%2Fapis.cashcash.id%2Fappsflyer%2Finstall-postback%3Fapp_id%3D%7Bapp_id%7D%26install_time%3D%7Binstalled_at%7D%26android_id%3D%7Bandroid_id%7D%26site_id%3D%20fd3b0134b625a47f%26clickid%3D{transaction_id}%26advertising_id%3D{gaid}";

		s = "{}";
		System.out.println(URLEncoder.encode(s, "gbk"));
	}

	@Test
	public void decode() throws UnsupportedEncodingException {
		String s = "http%3A%2F%2Fapi.altamob.com%2Fadserver%2Fv1%2Fpostback%3Fsubsite%3D{affiliate_id}%26transaction_id%3D{transaction_id}%26parameter%3D{parameter}";
		System.out.println(URLDecoder.decode(s, "gbk"));

		String newPostback = "http://callback.altamob.com/api/offline/postback?subsite={affiliate_id}&transaction_id={transaction_id}&type=imp";

		System.out.println(URLEncoder.encode(newPostback, "gbk"));
	}

	@Test
	public void decode2() throws UnsupportedEncodingException {
		//		String s = "https%3A%2F%2Fapis.cashcash.id%2Fappsflyer%2Finstall-postback%3Fapp_id%3D%7Bapp_id%7D%26install_time%3D%7Binstalled_at%7D%26android_id%3D%7Bandroid_id%7D%26site_id%3Dfd3b0134b625a47f%26clickid%3D{transaction_id}%26advertising_id%3D{gaid}";
		//		String s = "http%3A%2F%2Fcallback.altamob.com%2Fapi%2Foffline%2Fpostback%3Fsubsite%3D{affiliate_id}%26transaction_id%3D{transaction_id}";
		//		String s = "http%3A%2F%2Fcallback.altamob.com%2Fadserver%2Fv1%2Fpostback%3Fsubsite%3D{affiliate_id}%26transaction_id%3D{transaction_id}&adgroup={affiliate_id}";
		//		String s = "http%3A%2F%2Fcallback.altamob.com%2Fapi%2Foffline%2Fpostback%3Fsubsite%3D{affiliate_id}%26transaction_id%3D{transaction_id}";
		String s = "http%3A%2F%2Ftemplates.glispa.com%2Fad%3FtemplateName%3Dwk-banner-480x75%26productType%3DANDROID_APP%26productId%3Dcom.taskbucks.taskbucks%26categoryProductId%3D7%26locale%3Ddefault%26clickUrl%3D%26impressionUrl%3D&ttl=0&viewport=480x75";
		System.out.println(URLDecoder.decode(s, "gbk"));

	}

	@Test
	public void decode1() throws UnsupportedEncodingException {
		String s = "http://callback.altamob.com/api/offline/postback?transaction_id={cid}";
		System.out.println(URLDecoder.decode(s, "utf-8"));
		System.out.println(URLEncoder.encode(s, "utf-8"));

		//		String newPostback = "http://callback.altamob.com/api/offline/postback?subsite={affiliate_id}&transaction_id={transaction_id}";
		//
		//		System.out.println(URLEncoder.encode(newPostback, "gbk"));
	}

	@Test
	public void encodetest() throws UnsupportedEncodingException {
		String s = "https://api.hasoffers.com/Apiv3/json?NetworkId=volomobile&Target=Affiliate_Offer&Method=findMyApprovedOffers&api_key=d2ff7fd1bf7aeedd0319a3c31e72759c7db4e14875d5a526d8db370042ac9480&contain[]=Country&contain[]=Thumbnail&contain[]=OfferCategory&filters[OfferCategory.id][]=8&filters[OfferCategory.id][]=10&status=active&format=json&contain[]=TrackingLink&limit=100&page=1";
		System.out.println(URLEncoder.encode(s, "utf-8"));

	}

	@Test
	public void testSdkInstallExtras() throws UnsupportedEncodingException {
		String s = "nXeQmyof%2B%2BaMUKbVjr0ZMLb4m6X7dfMNqQ9JAV6M3s4bLqvHI4RRyoG3ecX8joDr";
		System.out.println(URLDecoder.decode(s, "utf-8"));
	}
}
