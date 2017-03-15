package com.zf.util.fastjson;

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
}
