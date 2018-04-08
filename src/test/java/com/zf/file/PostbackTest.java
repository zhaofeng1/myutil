package com.zf.file;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class PostbackTest {

	@Test
	public void test() {
		String path = "E:\\logs\\appsnt.txt";
		String postback = "http://ad-cb.altamob.xiaoying.co/adserver/v1/postback?transaction_id=%s&payout=%s&source=10950";
		try {
			List<String> list = FileUtils.readLines(new File(path));
			for (String s : list) {
				//				System.out.println(s);
				String[] arr = s.split("\t");
				String postem = String.format(postback, arr[1], arr[2]);
				System.out.println("curl -i '" + postem + "'");
				//				System.out.println(arr[1] + ";" + arr[2]);
				//				HttpUtil.requestGet(postem);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
