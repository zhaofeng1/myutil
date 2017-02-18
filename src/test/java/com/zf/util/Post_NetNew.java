/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.zf.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author android
 */
public class Post_NetNew {

	public static String pn(Map<String, String> pams) throws Exception {
		if (null == pams) {
			return "";
		}
		String strtmp = "url";
		URL url = new URL(pams.get(strtmp));
		pams.remove(strtmp);
		strtmp = "body";
		String body = pams.get(strtmp);
		pams.remove(strtmp);
		strtmp = "POST";
		if (StringUtils.isEmpty(body))
			strtmp = "GET";
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod(strtmp);
		for (String pam : pams.keySet()) {
			httpConn.setRequestProperty(pam, pams.get(pam));
		}
		if ("POST".equals(strtmp)) {
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
			dos.writeBytes(body);
			dos.flush();
		}
		int resultCode = httpConn.getResponseCode();
		StringBuilder sb = new StringBuilder();
		sb.append(resultCode).append("\n");
		String readLine;
		InputStream stream;
		try {
			stream = httpConn.getInputStream();
		} catch (Exception ignored) {
			stream = httpConn.getErrorStream();
		}
		try {
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
		} catch (Exception ignored) {
		}

		return sb.toString();
	}

	/**
	 * 走代理
	 * 
	 * @param pams
	 * @param ip
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public static String pn(Map<String, String> pams, String ip, int port) throws Exception {
		if (null == pams) {
			return "";
		}
		InetSocketAddress addr = new InetSocketAddress(ip, port);
		Proxy proxy = new Proxy(Type.HTTP, addr);
		String strtmp = "url";
		URL url = new URL(pams.get(strtmp));
		pams.remove(strtmp);
		strtmp = "body";
		String body = pams.get(strtmp);
		pams.remove(strtmp);
		strtmp = "POST";
		if (StringUtils.isEmpty(body))
			strtmp = "GET";
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection(proxy);
		httpConn.setConnectTimeout(30000);
		httpConn.setReadTimeout(30000);
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod(strtmp);
		for (String pam : pams.keySet()) {
			httpConn.setRequestProperty(pam, pams.get(pam));
		}
		if ("POST".equals(strtmp)) {
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
			dos.writeBytes(body);
			dos.flush();
		}
		int resultCode = httpConn.getResponseCode();
		StringBuilder sb = new StringBuilder();
		sb.append(resultCode).append("\n");
		String readLine;
		InputStream stream;
		try {
			stream = httpConn.getInputStream();
		} catch (Exception ignored) {
			stream = httpConn.getErrorStream();
		}
		try {
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
		} catch (Exception ignored) {
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		//		String ip = "50.115.163.247";
		//		int port = 80;
		//		System.setProperty("http.proxyHost", "localhost");
		//		System.setProperty("http.proxyPort", "1080");
		//		System.setProperty("http.proxyHost", "207.244.64.132");
		//		System.setProperty("http.proxyPort", "8080");
		System.setProperty("proxyHost", "207.244.64.132");
		System.setProperty("proxyPort", "8080");
		//		System.setProperty("http.proxyHost", "207.244.64.132");
		//		System.setProperty("http.proxyPort", "8080");
		//		System.setProperty("socksProxyHost", "103.30.246.43");
		//		System.setProperty("socksProxyPort", "10800");
		//		System.setProperty("socksProxyHost", "localhost");
		//		System.setProperty("socksProxyPort", "1080");
		//		System.setProperty("socksProxyHost", "207.244.64.132");
		//		System.setProperty("socksProxyPort", "8080");

		Map<String, String> map = new HashMap<String, String>();
		//		map.put("url", "http://www.ip.cn");
		//		map.put("url", "https://www.google.com/");
		map.put("url", "http://tracking.crobo.com/aff_c?offer_id=21553&aff_id=1478&aff_sub2=P6P49R4873200591858716615&aff_sub=3181");
		//		map.put("url", "http://www.baidu.com");
		try {
			//			String result = Post_NetNew.pn(map, ip, port);
			String result = Post_NetNew.pn(map);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
