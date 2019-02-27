package com.zf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


public class Test {
//	public static String getHttpResult(String url) {
//		CloseableHttpClient httpCilent2 = HttpClients.createDefault();
//		HttpGet httpget = new HttpGet(url);
//		String json = null;
//		try {
//			HttpResponse response = httpCilent2.execute(httpget);
//			HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				json = EntityUtils.toString(entity, "UTF-8").trim();
//
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			httpget.abort();
//		}
//		return json;
//	}
	



	public static void main(String[] args) throws Exception {
		String url = "http://api.appflood.com/s2s_get_p_ads?token=85a33e071bd30c1b&pagesize=100&platform=Android&pricetype=cpi%7Ccpa&creatives=0&page=1";
		System.out.println(JSON.toJSONString(doGet()));
		//		String s = okGet();
		//		System.out.println(getHttpResult(url));
		//		String url = "http://api.mobilekris.com/index.php?m=advert&p=getoffer&app_id=5&app_key=c4b76f4a25e95bb50d4e0a59bdc91a5b&page=1&pagesize=50";
		//		CloseableHttpClient httpclient = HttpClients.custom().addInterceptorFirst(new HttpResponseInterceptor() {
		//
		//			@Override
		//			public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
		//				HttpEntity resEntity = response.getEntity();
		//				// 在此处消费InputStream
		//				BufferedInputStream ins = new BufferedInputStream(resEntity.getContent());
		//				FileOutputStream fos = new FileOutputStream("water1.png");
		//				byte[] buffer = new byte[4096];
		//				int len = 0;
		//				while ((len = ins.read(buffer)) > -1) {
		//					fos.write(buffer, 0, len);
		//				}
		//				fos.close();
		//			}
		//		}).build();

		//		CloseableHttpClient httpCilent2 = HttpClients.createDefault();
		//		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50000) //设置连接超时时间
		//				.setConnectionRequestTimeout(50000) // 设置请求超时时间
		//				.setSocketTimeout(50000)//默认允许自动重定向
		//				.build();
		//		HttpGet httpGet2 = new HttpGet(url);
		//		//		httpGet2.setConfig(requestConfig);
		//		String srtResult = "";
		//		try {
		//			HttpResponse httpResponse = httpCilent2.execute(httpGet2);
		//			if (httpResponse.getStatusLine().getStatusCode() == 200) {
		//				srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
		//				System.out.println(srtResult);
		//			} else if (httpResponse.getStatusLine().getStatusCode() == 400) {
		//				//..........
		//			} else if (httpResponse.getStatusLine().getStatusCode() == 500) {
		//				//.............
		//			}
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		} finally {
		//			try {
		//				httpCilent2.close();
		//			} catch (IOException e) {
		//				e.printStackTrace();
		//			}
		//		}
	}

	public static void okGet() throws IOException {
		String url = "http://api.appflood.com/s2s_get_p_ads?token=85a33e071bd30c1b&pagesize=10&platform=Android&pricetype=cpi%7Ccpa&creatives=0&page=1";
		//		String url = "http://api.mobilekris.com/index.php?m=advert&p=getoffer&app_id=5&app_key=c4b76f4a25e95bb50d4e0a59bdc91a5b&page=1&pagesize=50";
		//URL带的参数
		HashMap<String, String> params = new HashMap<>();
		//GET 请求带的Header
		HashMap<String, String> headers = new HashMap<>();
		//HttpUrl.Builder构造带参数url
		HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
		if (params != null) {
			for (String key : params.keySet()) {
				urlBuilder.setQueryParameter(key, params.get(key));
			}
		}
		OkHttpClient mOkHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(urlBuilder.build()).headers(headers == null ? new Headers.Builder().build() : Headers.of(headers)).get().build();
		 //同步
		Call call = mOkHttpClient.newCall(request);
		Response response = call.execute();
		if(response.isSuccessful()){
		     //响应成功
			System.out.println("aaa" + response.body().string().trim());
		}
	}

	public static String doGet() throws Exception {
		//		String url = "http://api.mobilekris.com/index.php?m=advert&p=getoffer&app_id=5&app_key=c4b76f4a25e95bb50d4e0a59bdc91a5b&page=1&pagesize=50";
		//		String url = "http://api.appflood.com/s2s_get_p_ads?token=85a33e071bd30c1b&pagesize=100&platform=Android&pricetype=cpi%7Ccpa&creatives=0&page=1";
		String url = "https://api.polymob.com/cmp?v=1.0&token=eanpIi1PourZA23FGj5TnyUUwCL07h8Z";
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}
}
