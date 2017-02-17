package com.zf.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 测试代理ip是否可用
 * 
 * @author ZhaoFeng
 * @date 2017年2月13日
 */
public class ProxyTest {

	@Test
	public void test() {
		String ip = "114.112.64.46";
		int port = 10000;

		//		String url = "http://google.com";
		String url = "http://www.baidu.com";
		try {
			String s = HttpUtil.requestGet(url, 300, 300, ip, port);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {

		String url = "http://www.baidu.com";
		try {
			String s = HttpUtil.requestGet(url, 300, 300);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testProxyBath() {
		List<IpProxy> list = new ArrayList<ProxyTest.IpProxy>();
		//		list.add(new IpProxy("173.239.35.108", 3128));
		//		list.add(new IpProxy("192.208.184.185", 80));
		//		list.add(new IpProxy("localhost", 1080));
		list.add(new IpProxy("103.30.246.43", 10800));
		//		list.add(new IpProxy("138.197.197.75", 8080));
		//		String url = "http://www.baidu.com";
		//		String url = "http://www.google.com";
		String url = "http://tracking.crobo.com/aff_c?offer_id=21553&aff_id=1478&aff_sub2=P6P49R4873200591858716615&aff_sub=3181";
		String result = "";
		for (IpProxy pro : list) {
			result = test2(url, pro.getIp(), pro.getPort());
			System.out.println("result:::" + result);
		}
	}

	public String test2(String url, String ip, int port) {
		String result = "";
		BufferedReader in = null;
		try {
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection(proxy);
			//			URLConnection connection = realUrl.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.setProperty("http.proxyHost", "localhost");
		System.setProperty("http.proxyPort", "1080");

	}

	class IpProxy {
		String ip;
		int port;

		public IpProxy() {
			super();
		}

		public IpProxy(String ip, int port) {
			super();
			this.ip = ip;
			this.port = port;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

	}
}
