package com.zf.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class CountryNameUitls {

	/**
	 * 
	 */
	@Test
	public void init() {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		System.out.println(path);
		try {
			String xmlPath = path + "/LocList_china.xml";
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(xmlPath));
			//			System.out.println(document.asXML());
			// 获取根元素
			Element root = document.getRootElement();
			System.out.println("Root: " + root.getName());
			Element countryRegion = root.element("CountryRegion");
			List<Element> stateList = countryRegion.elements("State");
			List<String> resultList = new ArrayList<String>();
			String str = "";
			for (Element e : stateList) {
				System.out.println("省:" + e.attributeValue("Name") + " pinyin:" + getPinyin(e.attributeValue("Name")));
				str = getPinyin(e.attributeValue("Name")) + ":" + e.attributeValue("Name");
				resultList.add(str);
				List<Element> cityList = e.elements("City");
				for (Element c : cityList) {
					System.out.println("市:" + c.attributeValue("Name") + " pinyin:" + getPinyin(c.attributeValue("Name")));
					str = getPinyin(c.attributeValue("Name")) + ":" + c.attributeValue("Name");
					resultList.add(str);
				}
			}

			String resultPath = path + "/pinyin_china.txt";
			FileUtils.writeLines(new File(resultPath), resultList);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void onlyCity() {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		System.out.println(path);
		try {
			String xmlPath = path + "/LocList_china.xml";
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(xmlPath));
			//			System.out.println(document.asXML());
			// 获取根元素
			Element root = document.getRootElement();
			System.out.println("Root: " + root.getName());
			Element countryRegion = root.element("CountryRegion");
			List<Element> stateList = countryRegion.elements("State");
			List<String> resultList = new ArrayList<String>();
			String str = "";

			for (Element e : stateList) {
				List<Element> list = e.elements();
				for (Element e1 : list) {
					List<Element> list1 = e1.elements();
					for (Element e2 : list1) {
						e1.remove(e2);
					}
				}
			}
			System.out.println(document.asXML());
			//			String resultPath = path + "/pinyin_china.txt";
			//			FileUtils.writeLines(new File(resultPath), resultList);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getPinyin(String str) {
		Hanyu hanyu = new Hanyu();
		return hanyu.getStringPinYin(str);
	}

	public static Map<String, List<String>> nameMap = new HashMap<String, List<String>>();

	public static void initNameMap() {
		String path = CountryNameUitls.class.getClassLoader().getResource("").getPath();
		path += "/pinyin_china.txt";

		File f = new File(path);
		try {
			List<String> list = FileUtils.readLines(f);
			String name = "";
			String value = "";
			String[] temArray = null;
			List<String> tempList = null;
			for (String s : list) {
				if (StringUtils.isNotBlank(s)) {
					temArray = s.split(":");
					name = temArray[0];
					value = temArray[1];
					if (nameMap.containsKey(name)) {
						nameMap.get(name).add(value);
					} else {
						tempList = new ArrayList<String>();
						tempList.add(value);
						nameMap.put(name, tempList);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getName(String pinyin) {
		String result = "";
		List<String> list = nameMap.get(pinyin.toLowerCase());
		if (list != null) {
			if (list.size() > 1) {
				result = list.get(0) + "-";
			} else {
				result = list.get(0);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		CountryNameUitls.initNameMap();
		System.out.println(CountryNameUitls.getName("Jilin"));
	}
}
