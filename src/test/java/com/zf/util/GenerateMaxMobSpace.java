package com.zf.util;

import org.junit.Test;

/**
 * 生成maxmob space 配置
 * 
 * @author ZhaoFeng
 * @date 2017年1月21日
 */
public class GenerateMaxMobSpace {

	@Test
	public void test() {
		//查违章 android
		String mid = "8DFwpwYp";
		String[] spidArray = new String[] { "njfR2MAY", "AiRTdXPX", "el5hCLw8", "kElelaP7", "mBYkPpmd", "2KzbxcW2", "KcDuMV7R", "1kVmiqiC", "4bTaIP1D", "DXzz7a9J", "E8dqn7Z8", "YNiW2RlP", "A1NPr3xM" };
		//考驾照ios
		mid = "RnbQmcjt";
		spidArray = new String[] { "p4Fiu9dl", "WFcFexqK", "9wJq9Df7", "ppSCHnJf", "hq4W0KgF", "kzmGuSBU", "GQrYMVca" };
		//考驾照android
		mid = "bEnwdtbl";
		spidArray = new String[] { "I9fjO79d", "YOFyD2UK", "FToHh8hQ", "ecJGzOYu", "yBRquDw6", "fyUAEKw7", "62C9O7he" };
		//车轮社区ios
		mid = "D2JpujfT";
		spidArray = new String[] { "M3H25UY0", "SJcF8VLC", "dsuP18iy", "5mbjY48W" };
		//车轮社区android
		mid = "rXZ3Mspn";
		spidArray = new String[] { "Nv9ii7aj", "UEKPIZi3", "N4fXgoc7", "kDMB5Egc" };

		String result = "";
		for (String s : spidArray) {
			result = s + "={\"mid\":\"" + mid + "\",\"spid\":\"" + s + "\",\"type\":1,\"attr\":[2],\"vamakeraid\":\"1551778104\",\"bundle\":\"chelun\"}";
			System.out.println(result);
		}
	}
}
