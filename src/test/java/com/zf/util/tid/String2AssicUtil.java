package com.zf.util.tid;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class String2AssicUtil {
	
	private static Map<String, String> char2StringMap = new HashMap<String, String>();
	private static Map<String,String> country2Code=new HashMap<String, String>();
	private static Map<String,String> code2Country=new HashMap<String, String>();
	static{
		for(int i=65;i<91;i++){
			char c1 = (char)i;
			for(int j=65;j<91;j++){
				char c2 = (char)j;
				country2Code.put(String.valueOf(new char[]{c1,c2}), i+""+j);
				code2Country.put(i+""+j,String.valueOf(new char[]{c1,c2}));
			}
			char c = (char)i;
			char2StringMap.put(String.valueOf(c), i+"");
		}
	}
	
	public static String encodeCountryCode(String s){
		return country2Code.containsKey(s)?country2Code.get(s):"";
	}
	
	public static String decodeCountryCode(String s){
		return code2Country.containsKey(s)?code2Country.get(s):"";
	}
	
	public static void main(String[] args) {
//		System.out.println(char2StringMap);
//		System.out.println(country2Code);
//		System.out.println(code2Country);
		for(Entry<String, String> entry:country2Code.entrySet()){
			String encode=encodeCountryCode(entry.getKey());
			String decode=decodeCountryCode(encode);
			System.out.println("key:"+entry.getKey()+",vale:"+entry.getValue()+"-->encode:"+encode+"|decode:"+decode);
		}
		
	}
}
