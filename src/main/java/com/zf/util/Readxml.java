package com.zf.util;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 读取xml文件
 * @author zhaofeng
 *
 */
public class Readxml {

	@Test
	public void test() {
		String path = "E://logs/jobs.xml";
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = saxReader.read(new File(path));
			//			System.out.println(doc.asXML());
			Element rootE = doc.getRootElement();
			for (Iterator iter = rootE.elementIterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				//				System.out.println(element.asXML());
				String name = "";
				String enabled = "";
				String cron = "";
				for (Iterator iter1 = element.elementIterator(); iter1.hasNext();) {
					Element element1 = (Element) iter1.next();
					if (element1.getName().equals("name")) {
						name = element1.getText();
					}
					if (element1.getName().equals("enabled")) {
						enabled = element1.getText();
					}
					if (element1.getName().equals("cron")) {
						cron = element1.getText();
					}
					//					System.out.println(element1.getName());
					//					System.out.println(element1.asXML());
				}
				if (enabled.equals("true")) {
					System.out.println(name + "\t" + cron);
				}
				//				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
