package com.zf.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JsoupTest {

	@Test
	public void testGetHref(){
		String path = "E:/crawler/error.html";
		try {
			File f = new File(path);
			Document doc = Jsoup.parse(f, "UTF-8");
			Elements productE = doc.select("div[data-component=product_list] > div");
			System.out.println("productE:"+productE.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLine(){
		String url = "http://www.lazada.co.id/beli-perhiasan/?searchredirect=Aksesoris";
		try {
			Document doc = Jsoup.connect(url).timeout(30*1000).post();
			File f = new File("E:/crawler/errorline.html");
			FileUtils.writeStringToFile(f, doc.toString());
//			Elements productE = doc.select("div[data-component=product_list] > div");
//			System.out.println("productE:"+productE.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
