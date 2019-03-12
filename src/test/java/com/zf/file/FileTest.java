package com.zf.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
import com.alibaba.fastjson.JSONObject;

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
		Map<String, Set<String>> map = new HashMap<>();
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
	public void getFromDir() throws IOException {

		String path = "C:/Users/dell/Desktop/subsite_txt";
		File f = new File(path);

		File[] fArray = f.listFiles();

		for (File fT : fArray) {
			//			List<String> lines = FileUtils.readLines(fT, "GBK");
			List<String> lines = forTest(fT);
			System.out.println(fT.getName().substring(0, fT.getName().indexOf(".")));
			for (String s : lines) {
				System.out.println(s);
			}
			System.out.println("");
		}
	}

	@Test
	public void getFromDir1() throws IOException {

		String path = "C:/Users/dell/Desktop/subsite_txt/30075.txt";
		File f = new File(path);
		List<String> list = forTest(f);
		for (String s : list) {
			System.out.println(s);
		}
	}

	@Test
	public void getFromDir2() throws IOException {

		String path = "E:/logs/tmp/pid2_tid_tmp.txt";
		File f = new File(path);
		List<String> list = forTest(f);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		String day = "";
		for (String s : list) {
			if (s.length() > 15) {
				//				System.out.println(s);
				String date = s.substring(s.length() - 13, s.length());
				c.setTimeInMillis(Long.valueOf(date));
				//				System.out.println(sdf.format(c.getTime()));
				day = sdf.format(c.getTime());
				if (!map.containsKey(day)) {
					map.put(day, new HashSet<String>());
				}
				map.get(day).add(s);
			}
		}
		for (String s : map.keySet()) {
			System.out.println("s:" + s);
			String str = "";
			for (String t : map.get(s)) {
				str += "'" + t + "',";
			}
			System.out.println(str.substring(0, str.length() - 1));
		}
	}

	private static List<String> forTest(File f) throws IOException {
		List<String> result = new ArrayList<String>();
		InputStreamReader read = new InputStreamReader(new FileInputStream(f), "GBK");
		BufferedReader reader = new BufferedReader(read);
		String line;
		while ((line = reader.readLine()) != null) {
			result.add(line);
		}
		return result;
	}

	@Test
	public void getFromDir11() throws IOException {

		String path = "E:/logs/subsite_s2s_clk";
		File f = new File(path);

		File[] fArray = f.listFiles();

		for (File fT : fArray) {
			//			List<String> lines = FileUtils.readLines(fT, "GBK");
			List<String> lines = forTest(fT);
			if (lines.size() == 0) {
				System.out.println(fT.getName());
			}
		}
	}

	@Test
	public void getS2sClick() throws IOException {
		String path = "E:/logs/subsite_s2s_clk";
		File f = new File(path);

		File[] fArray = f.listFiles();
		Set<String> set = new HashSet<String>();

		for (File fT : fArray) {
			List<String> lines = forTest(fT);
			for (String s : lines) {
				if (StringUtils.isNotBlank(s)) {
					String tempStr = s.split("\t")[1];
					//					System.out.println(tempStr);
					Map<String, String> tempMap = parseMap(tempStr);
					System.out.print(tempMap.get("subsite_id") == null ? "" : tempMap.get("subsite_id"));
					System.out.print("\t");
					System.out.print(tempMap.get("geo") == null ? "nogeo" : "geo=" + tempMap.get("geo"));
					System.out.print("\t");
					System.out.println(tempMap.get("gaid") == null ? "nogaid" : "gaid=" + tempMap.get("gaid"));
					set.add(tempMap.get("subsite_id"));
				}
			}
		}
		System.out.println(set.size());
	}

	/**
	 * 将参数转为map
	 * @param param
	 * @return
	 */
	public Map<String, String> parseMap(String param) {
		Map<String, String> map = new HashMap<String, String>();
		String[] array = param.split("&");
		for (String s : array) {
			String[] keyvalue = s.split("=");
			if (keyvalue.length == 2) {
				map.put(keyvalue[0], keyvalue[1] == null ? "" : keyvalue[1]);
			} else if (keyvalue.length == 1) {
				map.put(keyvalue[0], "");
			}
		}
		return map;
	}

	@Test
	public void getTid() throws IOException {
		String path = "E:/logs/tmp/tid_0203.txt";
		File f = new File(path);

		List<String> lines = forTest(f);
		String str = "";
		int i = 0;
		for (String s : lines) {
			str += "'" + s + "',";
			i++;
			if (i > 5000) {
				System.out.println(str);
				str = "";
				i = 0;
			}
		}
	}

	@Test
	public void getPlacement() throws IOException {
		String path = "E:/logs/tmp/result1.txt";
		File f = new File(path);
		List<String> list = FileUtils.readLines(f);
		Set<String> set = new HashSet<String>();
		for (String s : list) {
			if (s.contains("p63")) {
				//				System.out.println(s);
				String[] array = s.split("\\|");
				JSONObject jsonObj = JSON.parseObject(array[2].trim());
				System.out.println(jsonObj.getString("p5"));
				set.add(jsonObj.getString("p5"));
			}
		}
		System.out.println(JSON.toJSONString(set));
	}

	@Test
	public void statsSubsiteDomain() throws IOException {
		String path = "E:\\logs\\20180408\\subsite_ref.log";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, Map<String, AtomicInteger>> map = new HashMap<String, Map<String, AtomicInteger>>();
		String[] array = null;
		String subsite = "";
		String clickurl = "";
		String domain = "";
		int errornum = 0;
		for (String s : list) {
			array = s.split(" ");
			if (array != null) {
				subsite = array[0];
				clickurl = array[1];
				URL url = null;
				try {
					url = new URL(clickurl);
				} catch (Exception e) {
					errornum++;
					System.out.println("clickurl:" + clickurl);
				}
				if (url != null) {
					domain = url.getHost();
					if (map.containsKey(domain)) {
						if (map.get(domain).containsKey(subsite)) {
							map.get(domain).get(subsite).incrementAndGet();
						} else {
							map.get(domain).put(subsite, new AtomicInteger(1));
						}
					}else{
						map.put(domain, new HashMap<String, AtomicInteger>());
						map.get(domain).put(subsite, new AtomicInteger(1));
					}
				}
				//				System.out.println(JSON.toJSONString(array));
				//				System.out.println("subsite:" + subsite + ";domian:" + domain);
				//				break;
			}
		}
		//		System.out.println(JSON.toJSONString(map));
		for (String s : map.keySet()) {
			for (String s1 : map.get(s).keySet()) {
				System.out.println(s + "\t" + s1 + "\t" + map.get(s).get(s1));
			}
		}
		System.out.println("errornum:" + errornum);
	}

	@Test
	public void testTid() throws IOException {
		String path = "E:/logs/10088.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			if (s.contains("NOT_FOUND_TID")) {
				System.out.println(s.substring(s.indexOf("transaction_id%3D") + 17, s.indexOf("%26parameter%")));
			}
		}
	}

	@Test
	public void testClickClk() throws IOException {
		String path = "E:/logs/clickclk.txt";
		List<String> list = FileUtils.readLines(new File(path));
		int i = 0;
		for (String s : list) {
			if (!s.contains("source")) {
				i++;
				if (i == 1) {
					System.out.print(s + "\t");
				} else {
					System.out.println(s);
				}
			} else {
				i = 0;
			}
		}
	}

	@Test
	public void testStr() throws IOException {
		String path = "E:/logs/channel.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();
		for (String s : list) {
			if (!map.containsKey(s)) {
				map.put(s, new AtomicInteger(0));
			}
			map.get(s).incrementAndGet();
		}
		System.out.println(JSON.toJSONString(map));
	}

	@Test
	public void getSqlFromFile() throws IOException {
		String path = "E:/logs/offertarget.txt";
		List<String> list = FileUtils.readLines(new File(path));
		String offerid = "";
		String geo = "";
		String sql = "insert into feed_offer_target(offer_id,app_info_id,code,status,manual,jump_counter) values(%s,1271,'%s','active',1,-1);";
		String tempSql = "";
		Set<String> set = new HashSet<String>();
		for (String s : list) {
			String[] strArray = s.split("\t");
			offerid = strArray[0];
			geo = strArray[1];
//			set.add(offerid);
			tempSql = String.format(sql, offerid, geo);
			System.out.println(tempSql);
		}
		//		System.out.println(JSON.toJSONString(set));
	}

	@Test
	public void getaid() throws IOException {
		String path = "E:/logs/offertest/aid.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			if (!s.contains("-")) {
				System.out.println(s);
			}
		}

	}

	@Test
	public void getOfferGeo() throws IOException {
		String path = "E:/logs/offertest/offer_geo.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			String[] sArray = s.split(",");
			System.out.println("already_test_" + sArray[0] + "_" + sArray[1]);
		}

	}

	@Test
	public void getOfferSplit() throws IOException {
		String path = "E:/logs/offertest/offerids1.txt";
		String path1 = "E:/logs/offertest/offerids2.txt";
		List<String> list = FileUtils.readLines(new File(path));
		int i = 1;
		//		String offerids = "";
		List<String> result = new ArrayList<String>();
		for (String s : list) {
			//			offerids += s + ",";
			//			if (i % 300 == 0) {
				//				System.out.println(offerids);
				result.add("curl -i 'http://sg-ad.altamob.com/offertest/admin/asyncTrackingOffers?offerids=" + s + "'");
				//				offerids = "";
			//			}
			i++;
		}
		FileUtils.writeLines(new File(path1), result);
	}

	@Test
	public void test26() throws IOException {
		String path = "E:/logs/handle/tid_handle";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			System.out.println(s.substring(0, s.indexOf("&")));
		}

	}

	@Test
	public void test28() throws IOException {
		String path = "D:/Documents/Downloads/Mytable_1.sql";
		String path1 = "D:/Documents/Downloads/Mytable_2.sql";
		List<String> list = FileUtils.readLines(new File(path));
		List<String> result = new ArrayList<String>();
		String title = "INSERT INTO MY_TABLE(android_id, version, sdk_version, language, country, user_agent) VALUES";
		int i = 1;
		result.add(title);
		for (String s : list) {
			if (i % 10000 == 0) {
				result.add(s.substring(0, s.length() - 1) + ";");
				result.add(title);
			} else {
				result.add(s);
			}
			i++;
		}

		FileUtils.writeLines(new File(path1), result);
	}

	@Test
	public void test27() throws IOException {
		String path = "E:/logs/tmp/mytest/offer_geo_payout.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, Set<String>> offergeoMap = new HashMap<String, Set<String>>();
		Map<String, Set<String>> offerpayoutMap = new HashMap<String, Set<String>>();
		String updatePayoutSql = "update feed_offers set maxpayout='%s' where id=%s;";

		String updateGeoSql = "insert into feed_offer_target(offer_id,app_info_id,code,status,manual,jump_counter) values(%s,1271,'%s','active',1,-1);";
		for (String s : list) {
			String[] sarray = s.split("\t");
			//			System.out.println(JSON.toJSONString(sarray));
			String geo = sarray[0];
			String payout = sarray[2];
			payout = payout.substring(1).trim();
			String offerid = sarray[3];
			
			if (!offerpayoutMap.containsKey(offerid)) {
				offerpayoutMap.put(offerid, new HashSet<String>());
			}
			offerpayoutMap.get(offerid).add(payout);

			if (!offergeoMap.containsKey(offerid)) {
				offergeoMap.put(offerid, new HashSet<String>());
			}
			offergeoMap.get(offerid).add(geo);

		}
		//		System.out.println(JSON.toJSONString(offerpayoutMap));

		//		for(String s:offerpayoutMap.keySet()){
		//			for (String payout : offerpayoutMap.get(s)) {
		//				System.out.println(String.format(updatePayoutSql, payout, s));
		//			}
		//		}

		for (String s : offergeoMap.keySet()) {
			for (String geo : offergeoMap.get(s)) {
				System.out.println(String.format(updateGeoSql, s, geo));
			}
		}

	}

}
