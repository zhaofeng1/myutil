package com.zf.util.fastjson;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FastJsonTest {

	@Test
	public void test() {
		String str = "{\"flag\":false,\"name\":\"abc\"}";
		//		My m = JSON.parseObject(str, My.class);

		//		People p = new People();
		//		p.setName("abc");
		//		p.setFlag(1);
		//		String str = JSON.toJSONString(p);
		//		System.out.println(str);
		People p1 = JSON.parseObject(str, People.class);
		System.out.println(JSON.toJSONString(p1));
	}

	@Test
	public void test1() {
		String str = "[{\"flag\":1,\"name\":\"abc\"},{\"flag\":2,\"name\":\"abc\"}]";

		List<People> list = JSON.parseArray(str, People.class);

		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void test2() {
		String str = "{\"0\":7,\"1\":8}";

		Map<String, Integer> map = JSON.parseObject(str, Map.class);

		System.out.println(JSON.toJSONString(map));
	}
}
