package com.zf.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * 文件中数据统计
 * 
 * @author ZhaoFeng
 * @date 2017年2月21日
 */
public class FileStatus {

	@Test
	public void test() {

		String path = "E:/项目问题/数据统计adserver/request.txt";
		try {
			//需要统计的site
			Set<String> set = new HashSet<String>();
			set.add("30228");
			set.add("30164");
			set.add("30245");

			List<String> list = FileUtils.readLines(new File(path));
			String[] strArray = null;
			String[] requestArray = null;
			String date = "2017-02-20";
			String site = "";
			String request = "";
			String response = "";
			String responseCode = "";
			String country = "";//requset中国家
			String pkg = "";//requset中包名
			//key: 日期-国家-包名  value: key:requestAll请求量 requestSuccess请求成功量
			Map<String, Map<String, AtomicInteger>> resultMap = new HashMap<String, Map<String, AtomicInteger>>();
			for (String s : list) {
				String key = "";
				if (StringUtils.isNotBlank(s)) {
					strArray = s.split("\t");
					site = strArray[1];
					request = strArray[2];
					response = strArray[3];
					if (set.contains(site)) {
						if (StringUtils.isNotBlank(request)) {
							requestArray = request.split("&");
							for (String s1 : requestArray) {
								if (s1.contains("country")) {
									country = getValue(s1);
								}
								if (s1.contains("flow_intercept_pkg")) {
									pkg = getValue(s1);
								}
							}
						}
						if (StringUtils.isNotBlank(response)) {
							System.out.println("response code:" + response.split("&")[0]);
							responseCode = response.split("&")[0];
						}

						System.out.println("=======================");
						key = date + "||" + country + "||" + pkg;
						if (resultMap.containsKey(key)) {
							resultMap.get(key).get("requestAll").incrementAndGet();
							if (responseCode.contains("200")) {
								resultMap.get(key).get("requestSuccess").incrementAndGet();
							}
						} else {
							Map<String, AtomicInteger> temMap = new HashMap<String, AtomicInteger>();
							temMap.put("requestAll", new AtomicInteger(1));
							if (responseCode.contains("200")) {
								temMap.put("requestSuccess", new AtomicInteger(1));
							} else {
								temMap.put("requestSuccess", new AtomicInteger(0));
							}
							resultMap.put(key, temMap);
						}
					}
				}
			}
			System.out.println("resultMap:" + JSON.toJSONString(resultMap));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获得冒号后的值
	 * 
	 * @param s
	 * @return
	 */
	public static String getValue(String s) {
		String result = "";
		String[] sArray = s.split(":");
		if (sArray.length > 1) {
			result = sArray[1];
		}
		return result;
	}
}
