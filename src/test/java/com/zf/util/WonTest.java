package com.zf.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class WonTest {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					testTimeLocal(Thread.currentThread().getName());
				}
			});
			t.start();
		}
		try {
			Thread.sleep(5 * 60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testTime() {
		//		String path = "E:/tmp/won.req.log";
		//		String url = "http://54.223.98.39";
		String path = "E:/tmp/won.req_local.log";
		String url = "http://localhost:8080";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		try {
			List<String> list = FileUtils.readLines(new File(path));
			long l = 0;
			int i = 1;
			long cost = 0;
			Date date = null;
			for (String s : list) {
				if (i > 100) {
					break;
				}
				l = System.currentTimeMillis();
				String result = "";
				date = new Date();
				try {
					result = HttpUtil.requestGet(url + s, 40 * 1000, 40 * 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//				System.out.println("result:" + result);
				cost = System.currentTimeMillis() - l;
				if (cost > 2 * 1000) {
					System.out.println("now:" + sf.format(date));
					System.out.println("url:" + url + s);
				}
				System.out.println("耗时：" + cost);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testTimeLocal(String threadName) {
		System.out.println("threadName:" + threadName);
		//		String path = "E:/tmp/won.req.log";
		//		String url = "http://54.223.98.39";
		String path = "E:/tmp/won.req_local.log";
		String url = "http://localhost:8080";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		try {
			List<String> list = FileUtils.readLines(new File(path));
			long l = 0;
			int i = 1;
			long cost = 0;
			Date date = null;
			for (String s : list) {
				if (i > 100) {
					break;
				}
				l = System.currentTimeMillis();
				String result = "";
				date = new Date();
				try {
					result = HttpUtil.requestGet(url + s, 40 * 1000, 40 * 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//				System.out.println("result:" + result);
				cost = System.currentTimeMillis() - l;
				if (cost > 2 * 1000) {
					System.out.println("now:" + sf.format(date));
					System.out.println("url:" + url + s);
				}
				System.out.println("threadName" + threadName + " 耗时：" + cost);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
