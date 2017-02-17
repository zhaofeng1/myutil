package com.zf.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * httpcliet 请求
 * 
 * @author ZhaoFeng
 * @date 2017年2月10日
 */
public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	private static final int MAX_CONNECTIONS = 1000;
	private static final int API_MAX_CONN = 500;
	private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
	private static final int DEFAULT_READ_TIMEOUT = 2000;
	private static HttpClientBuilder httpClientBuilder = HttpClients.custom();
	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

	static {
		cm.setMaxTotal(MAX_CONNECTIONS);
		cm.setDefaultMaxPerRoute(API_MAX_CONN);
		httpClientBuilder.setConnectionManager(cm);
	}

	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient client = httpClientBuilder.build();
		return client;
	}

	public static String requestPost(List<NameValuePair> params, String apiPath) throws ClientProtocolException, IOException {
		return requestPost(params, apiPath, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_READ_TIMEOUT);
	}

	public static String requestGet(String apiPath) throws Exception {
		return requestGet(apiPath, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
	}

	/**
	 * 发送Post请求,默认超时时间2000ms
	 * 
	 * @param params
	 *            参数列表 <K,V>
	 * @param apiPath
	 *            请求ApiPath
	 * @param connectionTimeout
	 *            连接超时时间 in millsecond
	 * @param readTimeout
	 *            响应时间 in millsecond
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String requestPost(List<NameValuePair> params, String apiPath, int connectionTimeout, int readTimeout) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = getHttpClient();
		String responseJsonStr = "";
		CloseableHttpResponse response = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			HttpPost httppost = new HttpPost(apiPath);
			httppost.setConfig(requestConfig);
			if (!CollectionUtils.isEmpty(params))
				httppost.setEntity(new UrlEncodedFormEntity(params));
			httppost.addHeader("Connection", "keep-alive");
			httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("httppost response stauts code:" + response.getStatusLine().getStatusCode());
				httppost.releaseConnection();
				return "";
			}
			HttpEntity entity = response.getEntity();
			responseJsonStr = EntityUtils.toString(entity, "utf-8");
			httppost.releaseConnection();
		} finally {
			if (response != null)
				response.close();
		}
		return responseJsonStr;
	}

	/**
	 * 发送Post请求,默认超时时间2000ms
	 * 
	 * @param params
	 *            参数列表 <K,V>
	 * @param apiPath
	 *            请求ApiPath
	 * @param connectionTimeout
	 *            连接超时时间 in millsecond
	 * @param readTimeout
	 *            响应时间 in millsecond
	 * @param ip
	 *            代理ip
	 * @param port
	 *            代理端口
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String requestPost(List<NameValuePair> params, String apiPath, int connectionTimeout, int readTimeout, String ip, int port) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = getHttpClient();
		String responseJsonStr = "";
		CloseableHttpResponse response = null;
		try {
			HttpHost proxy = new HttpHost(ip, port, "http");
			RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			HttpPost httppost = new HttpPost(apiPath);
			httppost.setConfig(requestConfig);
			if (!CollectionUtils.isEmpty(params))
				httppost.setEntity(new UrlEncodedFormEntity(params));
			httppost.addHeader("Connection", "keep-alive");
			httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("httppost response stauts code:" + response.getStatusLine().getStatusCode());
				httppost.releaseConnection();
				return "";
			}
			HttpEntity entity = response.getEntity();
			responseJsonStr = EntityUtils.toString(entity, "utf-8");
			httppost.releaseConnection();
		} finally {
			if (response != null)
				response.close();
		}
		return responseJsonStr;
	}

	/**
	 * 发送get请求,默认超时时间2000ms
	 * 
	 * @param apiPath
	 *            请求ApiPath
	 * @param connectionTimeout
	 *            连接超时时间 in millsecond
	 * @param readTimeout
	 *            响应时间 in millsecond
	 * @return
	 * @throws Exception
	 */
	public static String requestGet(String apiPath, int connectionTimeout, int readTimeout) throws Exception {
		String responseJsonStr = "";
		CloseableHttpClient httpclient = getHttpClient();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpget = new HttpGet(apiPath);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			httpget.setConfig(requestConfig);
			httpget.addHeader("Connection", "keep-alive");
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() != 200) {
				httpget.releaseConnection();
				throw new Exception("httpget response stauts code:" + response.getStatusLine().getStatusCode());
			}
			HttpEntity entity = response.getEntity();
			responseJsonStr = EntityUtils.toString(entity, "utf-8");
			httpget.releaseConnection();
		} finally {
			if (response != null)
				response.close();
		}
		return responseJsonStr;
	}

	/**
	 * 发送get请求,默认超时时间2000ms 使用代理
	 * 
	 * @param apiPath
	 *            请求ApiPath
	 * @param connectionTimeout
	 *            连接超时时间 in millsecond
	 * @param readTimeout
	 *            响应时间 in millsecond
	 * @param ip
	 *            代理ip
	 * @param port
	 *            代理端口
	 * @return
	 * @throws Exception
	 */
	public static String requestGet(String apiPath, int connectionTimeout, int readTimeout, String ip, int port) throws Exception {
		String responseJsonStr = "";
		CloseableHttpClient httpclient = getHttpClient();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpget = new HttpGet(apiPath);
			HttpHost proxy = new HttpHost(ip, port, "http");
			RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			httpget.setConfig(requestConfig);
			httpget.addHeader("Connection", "keep-alive");
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() != 200) {
				httpget.releaseConnection();
				throw new Exception("httpget response stauts code:" + response.getStatusLine().getStatusCode());
			}
			HttpEntity entity = response.getEntity();
			responseJsonStr = EntityUtils.toString(entity, "utf-8");
			httpget.releaseConnection();
		} finally {
			if (response != null)
				response.close();
		}
		return responseJsonStr;
	}

	public static void main(String[] args) {
		String result = "";
		try {
			//			result = requestGet("http://www.google.com", 30000, 30000, "192.241.254.131", 8080);
			//			result = requestGet("http://www.google.com", 30000, 30000, "localhost", 1080);
			//			result = requestGet(
			//					"http://pixel.admobclick.com/v1/ad/click?subsite_id=30222&transaction_id=&id=162&offer_id=13653614&geo=US&aid=a77d7d94853bab75&client_version=&gaid=6c373aba-f756-413f-ac78-f1339f6a1acc&tmark=1487311806351&p=&app_name=server.client&sdk_version=3.1&app_version=1.0.0",
			//					30000, 30000, "localhost", 1080);
			result = requestPost(
					new ArrayList<NameValuePair>(),
					"http://pixel.admobclick.com/v1/ad/click?subsite_id=30222&transaction_id=&id=162&offer_id=13653614&geo=US&aid=a77d7d94853bab75&client_version=&gaid=6c373aba-f756-413f-ac78-f1339f6a1acc&tmark=1487311806351&p=&app_name=server.client&sdk_version=3.1&app_version=1.0.0",
					30000, 30000, "localhost", 1080);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
	}
}
