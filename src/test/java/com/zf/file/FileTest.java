package com.zf.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FileTest {

	@Test
	public void test() throws IOException {

		String path = "E:/tmp/all-data.txt";
		List<String> list = FileUtils.readLines(new File(path));

		for (String s : list) {
			String[] array = s.split("\t");
			System.out.println(JSON.toJSON(array));
		}
	}

	@Test
	public void test1() throws IOException {
		String path = "E:/tmp/nginx_click/txt.log";
		List<String> list = FileUtils.readLines(new File(path));

		for (String s : list) {
			if (s.contains("/v1/ad/click?") && s.contains("HTTP/1.1")) {
				s = s.substring(s.indexOf("/v1/ad/click?"), s.indexOf("HTTP/1.1"));
				System.out.println(s);
			}
		}
	}

	public static int connectionTimeout = 30 * 1000;
	public static int readTimeout = 30 * 1000;

	@Test
	public void clickLog() throws Exception {
		String clientVersion = "6.5.8";
		Map<String, Set<String>> map = getTempMap();
		System.out.println("map:" + JSON.toJSONString(map));
		List<String> list = getClickList();
		for (String s : list) {
			for (int i = 0; i < 2; i++) {
				String tempUrl = s;
				if (tempUrl.contains("{transaction_id}")) {
					tempUrl = tempUrl.replace("{transaction_id}", getFromMap("transaction_id", map));
				}
				if (tempUrl.contains("{gaid}")) {
					tempUrl = tempUrl.replace("{gaid}", getFromMap("gaid", map));
				}
				if (tempUrl.contains("{aid}")) {
					tempUrl = tempUrl.replace("{aid}", getFromMap("aid", map));
				}
				if (tempUrl.contains("{client_version}")) {
					tempUrl = tempUrl.replace("{client_version}", getFromMap("client_version", map));
				}
				if (tempUrl.contains("{p}")) {
					tempUrl = tempUrl.replace("{p}", getFromMap("p", map));
				}
				if (tempUrl.contains("{tmark}")) {
					tempUrl = tempUrl.replace("{tmark}", getFromMap("tmark", map));
				}
				System.out.println(tempUrl);
				requestGet(tempUrl);
				Thread.sleep(1000);
				//是否请求postback
			}
		}
	}

	private String getFromMap(String key, Map<String, Set<String>> map) {
		String result = "";
		if (map.containsKey(key)) {
			Iterator<String> it = map.get(key).iterator();
			if (it.hasNext()) {
				result = it.next();
				map.get(key).remove(result);
			}
		}
		return result;
	}

	private List<String> getClickList() {
		List<String> list = new ArrayList<String>();//从appstore 接口中获取clickurl  需要把根据id去数据(feed_offer_target)库查 offerid 对应的地区
		list.add("http://52.77.237.237/v1/ad/click?subsite_id=30037&transaction_id={transaction_id}&id=66377412&appinfoid=31713&geo=IN&aid={aid}&client_version={client_version}&gaid={gaid}&tmark={tmark}&p={p}");
		//		list.add("http://52.77.237.237/v1/ad/click?subsite_id=30037&transaction_id={transaction_id}&id=63010976&appinfoid=1765&geo=US&aid={aid}&client_version={client_version}&gaid={gaid}&tmark=1495869570964&p={p}");
		return list;
	}

	private Map<String, Set<String>> getTempMap() throws IOException {
		//去新加坡 101 拉nginx 日志
		//more access.log |grep 'ad/click'|grep subsite_id=30037|head -100 >txt.log
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		String path = "E:/tmp/nginx_click/txt.log";
		List<String> list = FileUtils.readLines(new File(path));

		for (String s : list) {
			String httpTemp = "";
			if (s.contains("HTTP/1.0")) {
				httpTemp = "HTTP/1.0";
			} else if (s.contains("HTTP/1.1")) {
				httpTemp = "HTTP/1.1";
			}
			if (s.contains("/v1/ad/click?") && s.contains(httpTemp)) {
				s = s.substring(s.indexOf("/v1/ad/click?"), s.indexOf(httpTemp));
				//				System.out.println(s);

				Map<String, String> paramsMap = pramsMap(s);
				//				System.out.println(JSON.toJSONString(paramsMap));
				if (paramsMap.get("transaction_id") != null && StringUtils.isNotBlank(paramsMap.get("transaction_id"))) {
					if (map.containsKey("transaction_id")) {
						map.get("transaction_id").add(paramsMap.get("transaction_id"));
					} else {
						Set<String> tempSet = new HashSet<String>();
						tempSet.add(paramsMap.get("transaction_id"));
						map.put("transaction_id", tempSet);
					}
				}
				if (paramsMap.get("gaid") != null && StringUtils.isNotBlank(paramsMap.get("gaid"))) {
					if (map.containsKey("gaid")) {
						map.get("gaid").add(paramsMap.get("gaid"));
					} else {
						Set<String> tempSet = new HashSet<String>();
						tempSet.add(paramsMap.get("gaid"));
						map.put("gaid", tempSet);
					}
				}
				if (paramsMap.get("tmark") != null && StringUtils.isNotBlank(paramsMap.get("tmark"))) {
					if (map.containsKey("tmark")) {
						map.get("tmark").add(paramsMap.get("tmark"));
					} else {
						Set<String> tempSet = new HashSet<String>();
						tempSet.add(paramsMap.get("tmark"));
						map.put("tmark", tempSet);
					}
				}
			}
		}
		return map;
	}

	// Pattern 编译比较耗性能
	private static final String regEx = "(\\?|&+)(.+?)=([^&]*)";
	private static final Pattern p = Pattern.compile(regEx);

	public static Map<String, String> pramsMap(String str) {
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		Matcher m = p.matcher(str);
		while (m.find()) {
			String paramName = m.group(2);
			String paramVal = m.group(3);
			paramMap.put(paramName, paramVal);
		}
		return paramMap;
	}

	private static HttpClientBuilder httpClientBuilder = HttpClients.custom();

	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient client = httpClientBuilder.build();
		return client;
	}

	public String requestGet(String apiPath) {
		String responseJsonStr = "";
		CloseableHttpClient httpclient = getHttpClient();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpget = new HttpGet(apiPath);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			httpget.setConfig(requestConfig);
			httpget.addHeader("Connection", "keep-alive");
			response = httpclient.execute(httpget);
			//			System.out.println("header:" + JSON.toJSONString(response.getAllHeaders()));
			//			for (Header header : response.getAllHeaders()) {
			//				System.out.println(header.getName() + "||" + header.getValue());
			//			}
			if (response.getStatusLine().getStatusCode() != 200) {
				httpget.releaseConnection();
				throw new Exception("httpget response stauts code:" + response.getStatusLine().getStatusCode());
			}
			HttpEntity entity = response.getEntity();
			responseJsonStr = EntityUtils.toString(entity, "utf-8");
			httpget.releaseConnection();
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
	public void statsSubsiteDomain() throws IOException {
		String path = "E:\\logs\\20180408\\subsite_ref.log";
		List<String> list =  FileUtils.readLines(new File(path));
		for(String s :list){
			System.out.println(s);
		}
	}
}
