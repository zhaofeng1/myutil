package com.zf.util;

import java.util.Properties;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class OkHttpTest {

	public static void main(String[] args) {
		Properties prop = System.getProperties();
		//		prop.setProperty("socksProxyHost", "localhost");
		//		prop.setProperty("socksProxyPort", "1080");
		prop.setProperty("socksProxyHost", "119.59.123.30");
		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "210.247.242.71");
		//		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "103.30.246.43");
		//		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "103.12.211.226");
		//		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "52.66.4.48");
		//		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "223.25.245.11");
		//		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "103.19.16.167");
		//		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "125.212.224.20");
		//		prop.setProperty("socksProxyPort", "10800");
		//		prop.setProperty("socksProxyHost", "52.67.30.221");
		//		prop.setProperty("socksProxyPort", "10801");
		//		prop.setProperty("socksProxyHost", "192.241.254.131");
		//		prop.setProperty("socksProxyPort", "8080");

		//		System.setProperty("http.proxyHost", "154.46.204.34");
		//		System.setProperty("http.proxyPort", "80");
		//		System.setProperty("http.proxyHost", "125.212.224.20");
		//		System.setProperty("http.proxyPort", "10800");

		try {
			//			System.out.println(getResponseCode("http://www.google.com"));
			//			System.out.println(getResponseCode("http://ip.com"));
			okhttpGet("http://apps.applift.com/aff_c?offer_id=26875&aff_id=23425&aff_sub4=1012221&source=shareit&aff_sub=shareit&aff_sub2=12701102301011284411487382185100&aff_sub3=10032_12701102301011284411487382185100_US_6056163_0.42_463&android_id=755bfedb-108d-4e69-a270-087f2914ba41");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void okhttpGet(String url) throws Exception {
		OkHttpClient client = new OkHttpClient();
		String userAgent = "Mozilla/5.0 (Linux; Android 4.4.4; HM NOTE 1LTE Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.109 Mobile Safari/537.36";
		//		String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
		Request request = new Request.Builder().url(url).header("User-Agent", userAgent).build();

		//		client.setConnectTimeout(600, TimeUnit.SECONDS);
		//		client.setReadTimeout(600, TimeUnit.SECONDS);
		//		MediaType media = MediaType.parse("application/json; charset=utf-8");
		//		RequestBody body = RequestBody.create(media, JSON.toJSONString(map));
		//		post
		//		Request request1 = new Request.Builder().url(url).header("User-Agent", userAgent).post(body).build();

		client.setFollowRedirects(false);
		Response response = client.newCall(request).execute();

		System.out.println("code:" + response.code());

		System.out.println("Result: " + response.isSuccessful());
		System.out.println("Server: " + response.header("Server"));
		System.out.println("ResponseBody: " + response.body().string());
		System.out.println("location:" + response.header("Location"));
	}
	//	public static int getResponseCode(String url) throws IOException {
	//		OkHttpClient okHttpClient = new OkHttpClient();
	//		okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
	//		Request request = (new Request.Builder()).url(url).build();
	//		Response response = okHttpClient.newCall(request).execute();
	//		if (!response.isSuccessful()) {
	//			throw new IOException("Unexpected code " + response);
	//		} else {
	//			int code = 0;
	//
	//			try {
	//				code = response.code();
	//				System.out.println(response.body().string());
	//				response.body().close();
	//			} catch (Exception var8) {
	//				var8.printStackTrace();
	//			}
	//
	//			return code;
	//		}
	//	}

}
