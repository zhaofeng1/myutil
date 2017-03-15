package com.zf.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.alibaba.fastjson.JSON;
import com.zf.util.CharUtil;
import com.zf.util.CountryNameUitls;

/**
 * 处理dbip中的国内ip库和前端需要的json
 * 
 * @author ZhaoFeng
 * @date 2017年3月13日
 */
public class CsvHandle {

	@Test
	public void test() {
		String path = "E:/项目问题/dbip-full-2017-03.csv";
		String resultPath = "E:/项目问题/dbip-china-2017-03.csv";
		File file = new File(path);
		BufferedReader reader = null;
		List<String> chinaList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				line++;
				if (tempString.contains("\"CN\"")) {
					//					System.out.println("line " + line + ": " + tempString);
					chinaList.add(tempString);
				}
				//				if (line > 1000) {
				//					break;
				//				}
			}
			System.out.println("数量：" + chinaList.size());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		try {
			FileUtils.writeLines(new File(resultPath), chinaList);
		} catch (IOException e) {
			System.out.println("write error!");
		}
	}

	@Test
	public void csvRead() {
		String path = "E:/项目问题/dbip-full-2017-03.csv";
		String resultPath = "E:/项目问题/dbip-china-2017-03.csv";
		try {
			File file = new File(path);

			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			CSVReader csvReader = new CSVReader(isr);

			if (csvReader != null) {
				//				String[] csvRow = csvReader.readNext(); // row  
				String[] csvRow = null;
				String ip1 = "";
				String ip2 = "";
				String country = "";
				String region = "";
				String city = "";
				String tempCity = "";
				String area = "";
				List<String[]> chinaList = new ArrayList<String[]>();
				String[] tempArray = null;

				//key:value  country:region:city:areaset
				Map<String, Map<String, Map<String, Set<String>>>> map = new HashMap<>();
				//				Map<String, Map<String, Set<String>>> map = new HashMap<String, Map<String, Set<String>>>();
				Map<String, Map<String, Set<String>>> regionMap = null;
				Map<String, Set<String>> cityMap = null;
				Set<String> areaSet = null;

				while ((csvRow = csvReader.readNext()) != null) {
					ip1 = csvRow[0];
					ip2 = csvRow[1];
					country = csvRow[2];
					region = csvRow[3];
					city = csvRow[4];
					area = "";
					if (country.equals("CN") || country.equals("TW")) {

						//处理省
						if (region.indexOf(" ") > 0) {
							System.out.println("region s:" + region);
							if (!region.contains("Inner")) {
								region = region.substring(0, region.indexOf(" "));
							}
							System.out.println("region e:" + region);
						}
						//处理市
						if (CharUtil.isChinese(city)) {
							continue;
						}
						if (city.contains("(")) {
							tempCity = city.substring(0, city.indexOf("(")).trim();
							area = city.substring(city.indexOf("(") + 1, city.indexOf(")"));
						} else {
							tempCity = city.trim();
							area = "";
						}
						if (tempCity.contains(" ")) {
							if (!tempCity.contains("Da Hinggan Ling")) {
								tempCity = tempCity.substring(0, tempCity.indexOf(" ")).trim();
							}
						}

						if (area.contains("Qu")) {
							area = area.replace("Qu", "").trim();
						}

						//写csv需要数据
						tempArray = new String[] { ip1, ip2, country, region, tempCity, area };
						chinaList.add(tempArray);

						//前端需要的数据
						if (map.containsKey(country)) {
							if (map.get(country).containsKey(region)) {
								if (map.get(country).get(region).containsKey(tempCity)) {
									map.get(country).get(region).get(tempCity).add(area);
								} else {
									areaSet = new HashSet<String>();
									areaSet.add(area);
									map.get(country).get(region).put(tempCity, areaSet);
								}
							} else {
								cityMap = new HashMap<String, Set<String>>();
								areaSet = new HashSet<String>();
								areaSet.add(area);
								cityMap.put(tempCity, areaSet);
								map.get(country).put(region, cityMap);
							}

						} else {
							regionMap = new HashMap<String, Map<String, Set<String>>>();
							cityMap = new HashMap<String, Set<String>>();
							areaSet = new HashSet<String>();
							areaSet.add(area);
							cityMap.put(tempCity, areaSet);
							regionMap.put(region, cityMap);

							map.put(country, regionMap);
						}

					}
				}

				isr.close();
				csvReader.close();

				//写csv 文件
				File wfile = new File(resultPath);
				Writer writer = new FileWriter(wfile);
				CSVWriter csvWriter = new CSVWriter(writer, ',');
				csvWriter.writeAll(chinaList);
				csvWriter.close();

				//写前端需要的json
				writeJson(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写前端需要的json
	 * 
	 * @param map
	 */
	public void writeJson(Map<String, Map<String, Map<String, Set<String>>>> map) {
		CountryNameUitls.initNameMap();
		String path = "E:/项目问题/地域/geonew/";
		String regionPath = path + "region/";
		String cityPath = path + "city/";
		String area = path + "area/";

		List<Map<String, Object>> regionList = null;
		List<Map<String, Object>> cityList = null;
		List<Map<String, Object>> areaList = null;

		Map<String, Object> regionMap = null;
		Map<String, Object> cityMap = null;
		Map<String, Object> areaMap = null;

		Map<String, Map<String, Set<String>>> countryMap = null;
		System.out.println("map json:" + JSON.toJSONString(map));

		Set<String> regionSet = null;
		Set<String> citySet = null;

		for (String s : map.keySet()) {
			regionSet = new HashSet<String>();
			regionList = new ArrayList<Map<String, Object>>();
			for (String s1 : map.get(s).keySet()) {
				//省
				regionMap = new HashMap<String, Object>();
				regionMap.put("id", s + "/" + s1.toUpperCase());
				//				regionMap.put("text", s1);
				String text = CountryNameUitls.getName(s1.toLowerCase());
				if (StringUtils.isNotBlank(text)) {
					regionMap.put("text", text);
				} else {
					regionMap.put("text", s1);
				}
				regionMap.put("children", true);
				regionMap.put("type", "r");
				regionList.add(regionMap);

				cityList = new ArrayList<Map<String, Object>>();
				citySet = new HashSet<>();
				for (String c : map.get(s).get(s1).keySet()) {
					citySet.add(c.toLowerCase());
				}
				for (String c : citySet) {
					cityMap = new HashMap<String, Object>();
					cityMap.put("id", s + "/" + s1.toUpperCase() + "/" + c.toUpperCase());
					//					cityMap.put("text", c);
					String text1 = CountryNameUitls.getName(c.toLowerCase());
					if (StringUtils.isNotBlank(text1)) {
						cityMap.put("text", text1);
					} else {
						cityMap.put("text", c);
					}
					cityMap.put("type", "city");
					cityList.add(cityMap);
				}
				writeFile(cityPath + s + "-" + s1.toUpperCase() + ".json", JSON.toJSONString(cityList));
			}

			writeFile(regionPath + s + ".json", JSON.toJSONString(regionList));
		}

		//		Map<String, Map<String, Set<String>>> cnMap = map.get("CN");
		//		Map<String, Map<String, Set<String>>> twMap = map.get("TW");
		//		if (cnMap != null) {
		//			for (String s : cnMap.keySet()) {
		//				//省
		//				regionMap = new HashMap<String, Object>();
		//				regionMap.put("id", "CN/" + s.toUpperCase());
		//				regionMap.put("text", s);
		//				regionMap.put("children", true);
		//				regionMap.put("type", "r");
		//				regionList.add(regionMap);
		//
		//				cityList = new ArrayList<Map<String, Object>>();
		//				for (String c : cnMap.get(s).keySet()) {
		//					cityMap = new HashMap<String, Object>();
		//					cityMap.put("id", "CN/" + s.toUpperCase() + "/" + c.toUpperCase());
		//					cityMap.put("text", c);
		//					cityMap.put("type", "city");
		//					cityList.add(cityMap);
		//				}
		//				writeFile(cityPath + "CN-" + s.toUpperCase() + ".json", JSON.toJSONString(cityList));
		//
		//			}
		//			writeFile(regionPath + "CN.json", JSON.toJSONString(regionList));
		//		}

	}

	public void writeFile(String path, String data) {
		try {
			FileUtils.writeStringToFile(new File(path), data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
