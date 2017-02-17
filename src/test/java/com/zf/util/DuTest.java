package com.zf.util;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class DuTest {

	public static void main(String[] args) {
		Properties prop = System.getProperties();
		//		prop.setProperty("socksProxyHost", "localhost");
		//		prop.setProperty("socksProxyPort", "1080");
		//		prop.setProperty("socksProxyHost", "119.59.123.30");
		//		prop.setProperty("socksProxyPort", "10800");
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
		prop.setProperty("socksProxyHost", "52.67.30.221");
		prop.setProperty("socksProxyPort", "10801");
		//		prop.setProperty("socksProxyHost", "192.241.254.131");
		//		prop.setProperty("socksProxyPort", "8080");

		//		System.setProperty("http.proxyHost", "154.46.204.34");
		//		System.setProperty("http.proxyPort", "80");
		//		System.setProperty("http.proxyHost", "125.212.224.20");
		//		System.setProperty("http.proxyPort", "10800");

		try {
			//			System.out.println(getResponseCode("http://www.google.com"));
			//			System.out.println(getResponseCode("http://ip.com"));
			System.out
					.println(getResponseCode("http://pixel.admobclick.com/v1/ad/click?subsite_id=30222&transaction_id=&id=162&offer_id=13653614&geo=US&aid=a77d7d94853bab75&client_version=&gaid=6c373aba-f756-413f-ac78-f1339f6a1acc&tmark=1487311806351&p=&app_name=server.client&sdk_version=3.1&app_version=1.0.0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getResponseCode(String url) throws IOException {
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
		Request request = (new Request.Builder()).url(url).build();
		Response response = okHttpClient.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		} else {
			int code = 0;

			try {
				code = response.code();
				System.out.println(response.body().string());
				response.body().close();
			} catch (Exception var8) {
				var8.printStackTrace();
			}

			return code;
		}
	}
}
