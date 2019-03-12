package com.zf.qqip;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import au.com.bytecode.opencsv.CSVWriter;

import com.alibaba.fastjson.JSON;
import com.zf.util.Hanyu;

public class HandleQqIp {

	@Test
	public void test() throws IOException {
		String dir = "E:/项目问题/地域/";
		String ipPath = dir + "cunzhen.txt";
		String resultPath = dir + "qqwry_china_201703.csv";

		List<String> list = FileUtils.readLines(new File(ipPath));
		String[] tempArray = null;
		String ip1 = "";
		String ip2 = "";
		String region = "";
		String city = "";
		String tempArea = "";
		//		Set<String> regionSet = new HashSet<String>();
		//		Set<String> citySet = new HashSet<String>();

		List<String> specilCityList = new ArrayList<String>();
		specilCityList.add("北京市");
		specilCityList.add("天津市");
		specilCityList.add("上海市");
		specilCityList.add("重庆市");

		List<String> specilRegionList = new ArrayList<String>();
		specilRegionList.add("广西");
		specilRegionList.add("宁夏");
		specilRegionList.add("新疆");
		specilRegionList.add("西藏");
		specilRegionList.add("内蒙古");

		Map<String, Set<String>> regionMap = new HashMap<String, Set<String>>();
		Set<String> citySet = null;

		List<String[]> csvList = new ArrayList<String[]>();
		for (String s : list) {
			region = "";
			city = "";
			s = s.replace("校区", "");
			s = s.replace("东区", "");
			s = s.replace("园区", "");
			s = s.replace("地区", "区");
			if (s.contains("华中科技大学")) {
				s = s.replace("华中科技大学", "湖北省武汉市");
			}
			if (s.contains("武汉大学")) {
				s = s.replace("武汉大学", "湖北省武汉市");
			}
			if (s.contains("南开大学")) {
				s = s.replace("南开大学", "天津市");
			}
			if (s.contains("吉林大学")) {
				s = s.replace("吉林大学", "吉林省长春市");
			}
			if (s.contains("上海交通大学")) {
				s = s.replace("上海交通大学", "上海市");
			}
			if (s.contains("华南理工大学")) {
				s = s.replace("华南理工大学", "广东省广州市");
			}
			if (s.contains("北京化工大学")) {
				s = s.replace("北京化工大学", "北京市");
			}
			if (s.contains("华南农业大学")) {
				s = s.replace("华南农业大学", "广东省广州市");
			}
			if (s.contains("四川大学")) {
				s = s.replace("四川大学", "四川省成都市");
			}
			if (s.contains("中南大学")) {
				s = s.replace("中南大学", "湖南省长沙市");
			}
			if (s.contains("省") || s.contains("市") || s.contains("区") || s.contains("新疆") || s.contains("内蒙古")) {

				tempArray = s.split(" +");
				ip1 = tempArray[0];
				ip2 = tempArray[1];
				if (tempArray.length > 3) {
					tempArea = tempArray[2];
					if (tempArea.contains("省")) {
						region = tempArea.substring(0, tempArea.indexOf("省") + 1);
						if (tempArea.contains("市")) {
							city = tempArea.substring(tempArea.indexOf("省") + 1, tempArea.indexOf("市") + 1);
						}
					} else if (tempArea.contains("北京市") || tempArea.contains("天津市") || tempArea.contains("上海市") || tempArea.contains("重庆市")) {
						for (String sc : specilCityList) {
							if (tempArea.contains(sc)) {
								region = sc;
								if (tempArea.contains("区")) {
									city = tempArea.replace(sc, "");
									city = city.substring(0, city.indexOf("区") + 1);
								} else {
									city = sc;
								}
							} else if (tempArea.contains(sc.substring(0, sc.length() - 1))) {
								region = sc;
								city = sc;
							}
						}
					} else if (tempArea.contains("广西") || tempArea.contains("宁夏") || tempArea.contains("新疆") || tempArea.contains("西藏") || tempArea.contains("内蒙古")) {
						for (String sr : specilRegionList) {
							if (tempArea.contains(sr)) {
								region = sr + "省";
								if (tempArea.contains("市")) {
									city = tempArea.replace(sr, "");
									city = city.substring(0, city.indexOf("市") + 1);
								}
							}
						}
					}

					else if (!tempArea.contains("国") && !tempArea.contains("北美")) {
						System.out.println(s);
					}

					//					regionSet.add(region);
					//					citySet.add(city);
				}
				if (StringUtils.isNotBlank(region)) {
					region = region.substring(0, region.length() - 1);
					String regionPinyin = getPinyin(region);
					String cityPinyin = "";
					if (StringUtils.isNotBlank(city)) {
						city = city.substring(0, city.length() - 1);
						if (city.contains("临夏州")) {
							city = "临夏";
						}
						if (city.contains("州") && city.contains("黔")) {
							city = city.substring(0, city.indexOf("州"));
						}
						if (city.contains("恩施州")
								|| city.contains("湘西州")
								|| city.contains("延边州")
								|| city.contains("海西州")
								|| city.contains("凉山州")
								|| city.contains("昌吉州")
								|| city.contains("伊犁州")
								|| city.contains("红河州")
								|| city.contains("德宏州")
								|| city.contains("大理州")
								|| city.contains("楚雄州")
								|| city.contains("西双版纳州")
								|| city.contains("文山州")) {
							city = city.substring(0, city.indexOf("州"));
						}
						if (city.contains("塔城区") || city.contains("喀什区") || city.contains("和田区") || city.contains("哈密区") || city.contains("阿克苏区") || city.contains("日喀则区")) {
							city = city.substring(0, city.indexOf("区"));
						}
						if (city.contains("省")) {
							city = city.replace("省", "");
						}
						if (city.contains("湖北武汉")) {
							city = "武汉";
						}
						if (city.contains("福州/厦门") || city.contains("南宁/桂林") || city.contains("沈阳/大连") || city.contains("济南/青岛")) {
							city = city.substring(city.indexOf("/") + 1);
						}
						if (city.equals("呼和")) {
							city = "呼和浩特";
						}
						if (city.equals("德阳广汉")) {
							city = "德阳";
						}
						if (city.equals("武情")) {
							city = "武清";
						}
						if (city.equals("嘉兴桐乡")) {
							city = "嘉兴";
						}
						cityPinyin = getPinyin(city);
					}
					if (tempArea.contains("台湾")) {
						csvList.add(new String[] { ip1, ip2, "TW", regionPinyin, cityPinyin, region, city });
					} else {
						csvList.add(new String[] { ip1, ip2, "CN", regionPinyin, cityPinyin, region, city });
					}
					if (regionMap.containsKey(region + "@" + regionPinyin)) {
						regionMap.get(region + "@" + regionPinyin).add(city + "@" + cityPinyin);
					} else {
						citySet = new HashSet<String>();
						citySet.add(city + "@" + cityPinyin);
						regionMap.put(region + "@" + regionPinyin, citySet);
					}
				}
			} else {
				//				System.out.println(s);
			}
		}

		writeCsv(csvList, resultPath);
		writeCnJson(regionMap);
		//		System.out.println(JSON.toJSONString(regionSet));
		//		System.out.println(JSON.toJSONString(citySet));
	}

	public static String getPinyin(String str) {
		Hanyu hanyu = new Hanyu();
		//		str = str.substring(0, str.length() - 1);
		if (str.equals("陕西")) {
			return "shaanxi";
		}
		if (str.equals("重庆")) {
			return "chongqing";
		}
		return hanyu.getStringPinYin(str);
	}

	public static void writeCsv(List<String[]> list, String path) throws IOException {
		//写csv 文件
		File wfile = new File(path);
		Writer writer = new FileWriter(wfile);
		CSVWriter csvWriter = new CSVWriter(writer, ',');
		csvWriter.writeAll(list);
		csvWriter.close();
	}

	/**
	 * 写前端需要的json cn
	 */
	public static void writeCnJson(Map<String, Set<String>> map) {
		String path = "E:/项目问题/地域/geonew-qq/";
		String regionPath = path + "region/";
		String cityPath = path + "city/";
		List<Map<String, Object>> regionCnList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> regionTwList = new ArrayList<Map<String, Object>>();
		Map<String, Object> regionMap = null;
		Map<String, Object> cityMap = null;

		for (String s : map.keySet()) {
			List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
			regionMap = new HashMap<String, Object>();
			String[] temArray = s.split("@");
			if (s.contains("台湾")) {
				for (String c : map.get(s)) {
					if (StringUtils.isNotBlank(c) && c.contains("@")) {
						String[] temCityArray = c.split("@");
						if (temCityArray.length > 1) {
							regionMap = new HashMap<String, Object>();
							regionMap.put("id", "TW/" + temCityArray[1].toUpperCase());
							regionMap.put("text", temCityArray[0]);
							regionMap.put("children", false);
							regionMap.put("type", "r");
							regionTwList.add(regionMap);
						}
					}
				}
			} else {
				regionMap.put("id", "CN/" + temArray[1].toUpperCase());
				regionMap.put("text", temArray[0]);
				regionMap.put("children", true);
				regionMap.put("type", "r");
				regionCnList.add(regionMap);
				for (String c : map.get(s)) {
					if (StringUtils.isNotBlank(c) && c.contains("@")) {
						String[] temCityArray = c.split("@");
						if (temCityArray.length > 1) {
							cityMap = new HashMap<String, Object>();
							cityMap.put("id", "CN/" + temArray[1].toUpperCase() + "/" + temCityArray[1].toUpperCase());
							cityMap.put("text", temCityArray[0]);
							cityMap.put("type", "city");
							cityList.add(cityMap);
						}
					}
				}
				writeFile(cityPath + "CN-" + temArray[1].toUpperCase() + ".json", JSON.toJSONString(cityList));
			}
			System.out.println(s);
			System.out.println(JSON.toJSONString(map.get(s)));
		}
		if (regionCnList.size() > 0) {
			writeFile(regionPath + "CN.json", JSON.toJSONString(regionCnList));
		}
		if (regionTwList.size() > 0) {
			writeFile(regionPath + "TW.json", JSON.toJSONString(regionTwList));
		}
	}

	public static void writeFile(String path, String data) {
		try {
			FileUtils.writeStringToFile(new File(path), data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
