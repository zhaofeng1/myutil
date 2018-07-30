package com.zf.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zf.util.HttpRequestUtils.ResponseEntity;

public class HttputilsTest {

	@Test
	public void test() {

		//		ResponseEntity entity = HttpRequestUtils.request("http://api.mobra.in/v1/auth/login?user=liuyanwei@altamob.com&password=altamobandroid2018", "POST", null);
		String cookie = requestPostAndGetCookie("http://api.mobra.in/v1/auth/login", "user=liuyanwei@altamob.com&password=altamobandroid2018");
		if (StringUtils.isNotBlank(cookie)) {
			if (cookie.contains(";")) {
				cookie = cookie.substring(0, cookie.indexOf(";"));
			}
		}
		System.out.println(JSON.toJSONString(cookie));
		test1(cookie);
	}

	@Test
	public void testGetToken() {

		//		ResponseEntity entity = HttpRequestUtils.request("http://api.mobra.in/v1/auth/login?user=liuyanwei@altamob.com&password=altamobandroid2018", "POST", null);
		String cookie = requestPostAndGetToken("http://offers.mct.moblin.com/token", "grant_type=password&username=liuyanwei%40altamob.com&password=Pass12345!");
		JSONObject jsonObj = JSON.parseObject(cookie);
		System.out.println(jsonObj.getString("access_token"));
		//		test1(cookie);
	}

	//	@Test
	public void test1(String cookie) {
		Header[] headers = new Header[1];
		headers[0] = new BasicHeader("cookie", cookie);
		ResponseEntity entity = HttpRequestUtils.request("https://api.mobra.in/v1/campaign/feed", "GET", headers);
		System.out.println(JSON.toJSONString(entity));
	}

	@Test
	public void test2() {
		Header[] headers = new Header[1];
		headers[0] = new BasicHeader(
				"cookie",
				"mobrain_api=eyJpdiI6Im0rTjY3TWJBWURQNlRGckUrRWZ5TVRRUmZuUzJ1YURJd3NHOXg2XC9DK1RzPSIsInZhbHVlIjoiQ3BNOFNHbG1IVEo1bU5RV09tcmtaR282TStkZXV6RW9lNnRpR2tnYWF4V2dvcVVjTzFwdytLU0RVelwvV2wxSmJPa3FrTXhjVjlNdlVHQkU1ZGhTNnF3PT0iLCJtYWMiOiI2ZDI4NGIwNTQ2MGMwZTVlYTM0MDI5MTc3MDRjNTRkMWFhZDEyN2EzNWI5OGFhNGJmYjVmMjQ5N2Y4NmFjN2IxIn0%3D");
		ResponseEntity entity = HttpRequestUtils.request("https://api.mobra.in/v1/campaign/feed", "GET", headers);
		System.out.println(JSON.toJSONString(entity));
	}

	public String requestPostAndGetCookie(String apiPath, String data) {
		String cookie = "";
		ResponseEntity resEntity = new ResponseEntity();
		String responseJsonStr = "";
		CloseableHttpClient httpclient = HttpClients.custom().build();
		CloseableHttpResponse response = null;
		int connectionTimeout = 60 * 1000;
		int readTimeout = 60 * 1000;
		int statusCode = 0;
		try {
			HttpPost httpPost = new HttpPost(apiPath);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("Connection", "keep-alive");
			httpPost.setEntity(new UrlEncodedFormEntity(URLEncodedUtils.parse(data, Consts.UTF_8), Consts.UTF_8));
			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			resEntity.setStatusCode(statusCode);
			HttpEntity entity = response.getEntity();
			responseJsonStr = EntityUtils.toString(entity, "utf-8");
			if (statusCode != 200) {
				System.out.println("httpget response stauts code:" + response.getStatusLine().getStatusCode() + ";responseJsonStr:" + responseJsonStr);
				//				throw new Exception("httpget response stauts code:" + response.getStatusLine().getStatusCode());
				//				for(Header header:response.getHeaders("Set-Cookie"))
			}
			Header[] heads = response.getHeaders("Set-Cookie");
			//			System.out.println(heads[0].getName());
			//			System.out.println(heads[0].getValue());
			cookie = heads[0].getValue();
			//			System.out.println(JSON.toJSONString(response.getHeaders("Set-Cookie")));
			httpPost.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return cookie;
	}

	public String requestPostAndGetToken(String apiPath, String data) {
		ResponseEntity resEntity = new ResponseEntity();
		String responseJsonStr = "";
		CloseableHttpClient httpclient = HttpClients.custom().build();
		CloseableHttpResponse response = null;
		int connectionTimeout = 60 * 1000;
		int readTimeout = 60 * 1000;
		int statusCode = 0;
		try {
			HttpPost httpPost = new HttpPost(apiPath);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("Connection", "keep-alive");
			httpPost.setEntity(new UrlEncodedFormEntity(URLEncodedUtils.parse(data, Consts.UTF_8), Consts.UTF_8));
			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			resEntity.setStatusCode(statusCode);
			HttpEntity entity = response.getEntity();
			responseJsonStr = EntityUtils.toString(entity, "utf-8");
			if (statusCode != 200) {
				System.out.println("httpget response stauts code:" + response.getStatusLine().getStatusCode() + ";responseJsonStr:" + responseJsonStr);
				//				throw new Exception("httpget response stauts code:" + response.getStatusLine().getStatusCode());
				//				for(Header header:response.getHeaders("Set-Cookie"))
			}
			System.out.println(responseJsonStr);
			httpPost.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return responseJsonStr;
	}

	@Test
	public void test11() throws Exception {

		String url = "http://localhost:8080/adserver/v1/admin/countRpcTest";
		for (int i = 0; i < 200; i++) {
			String r = HttpUtil.requestGet(url);
			System.out.println(r);
		}
	}
}
