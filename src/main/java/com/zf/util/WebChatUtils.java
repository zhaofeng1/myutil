package com.zf.util;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 微信预警消息发送
 * 
 * @author ZhaoFeng
 * @date 2016年12月26日
 */
public class WebChatUtils {

	private static final Logger logger = Logger.getLogger(WebChatUtils.class);

	/**
	 * 微信企业号的appid dsp使用
	 */
	private static final String CORPID_DSP = "wxf601632127c9d62b";

	/**
	 * 微信企业号的corpSecret dsp使用
	 */
	private static final String CORSECRET_DSP = "QfJVBWK1QAxykU_PR1wQg_DuVHDbpmCvIXkJKBwxd8OONREvCu0v02SDcDBPfXxw";

	/**
	 * agentid dsp alert
	 */
	private static final String AGENTID = "3";

	/**
	 * 获取token接口
	 */
	private static final String WEB_CHAT_ACCESS_TOKEN_API_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CORPID_DSP + "&corpsecret=" + CORSECRET_DSP;

	/**
	 * 发送信息接口
	 */
	private static final String WEB_CHAT_SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

	/**
	 * 读取超时
	 */
	private static int readTimeout = 30 * 1000;

	/**
	 * 连接超时
	 */
	private static int connectionTimeout = 30 * 1000;

	public static void sendMessage(String user, String message) {

		CloseableHttpClient client = HttpClientBuilder.create().build();
		String accessStr = null;
		CloseableHttpResponse r = null;
		try {
			HttpGet get = new HttpGet(WEB_CHAT_ACCESS_TOKEN_API_URL);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
			get.setConfig(requestConfig);
			r = client.execute(get);
			if (r.getStatusLine().getStatusCode() == 200) {
				accessStr = EntityUtils.toString(r.getEntity());
			}
		} catch (Exception e) {
			logger.error("get token false error:" + e);
		} finally {
			closeQuietly(r);
		}
		if (accessStr == null) {
			logger.error("access get error!");
			closeQuietly(client);
		}

		Map accessTokenMap = JSON.parseObject(accessStr, Map.class);
		if (accessTokenMap != null && accessTokenMap.containsKey("access_token")) {
			String accessToken = accessTokenMap.get("access_token").toString();
			String url = WEB_CHAT_SEND_MESSAGE_URL + accessToken;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("msgtype", "text");
			map.put("safe", "0");
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("content", message);
			map.put("text", contentMap);
			map.put("touser", user);
			map.put("agentid", "25");//dsp预警
			CloseableHttpResponse response = null;
			try {
				HttpPost post = new HttpPost(url);
				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectionTimeout).build();
				post.setConfig(requestConfig);
				String jsonData = JSON.toJSONString(map);
				System.out.println("jsonData:" + jsonData);
				post.setEntity(new StringEntity(jsonData, "utf-8"));
				response = client.execute(post);
				String reponseStr = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (Exception e) {
				logger.error("post msg data error:" + e);
				closeQuietly(response);
				closeQuietly(client);
			}
		} else {
			logger.error("WebChat access token get error!!!!");
		}
	}

	private static void closeQuietly(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (Exception e) {

			}
		}
	}

	public static void main(String[] args) {
		//		sendMessage("zhaofeng|zhouyan", "test!!");
		sendMessage("zhaofeng", "test!!");
		//		System.out.println("aaaaa");
	}

}
