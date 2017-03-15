package com.zf.reflection;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * 通过反射获取静态常量
 * 
 * @author ZhaoFeng
 * @date 2017年3月15日
 */
public class GetNobidReason {

	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException {
		Class nobid = NobidReason.class;
		Field[] fields = nobid.getDeclaredFields();
		Map<String, String> map = new TreeMap<>();
		for (Field f : fields) {
			f.setAccessible(true);
			System.out.println(f.getName());
			System.out.println(f.get(nobid));
			//			System.out.println(f.getGenericType());
			//			System.out.println(f);
			map.put(f.get(nobid) + "", f.getName());
		}
		System.out.println("result:" + JSON.toJSONString(map));
	}
}
