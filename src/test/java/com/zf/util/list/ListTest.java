package com.zf.util.list;

import java.util.ArrayList;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class ListTest {

	@Test
	public void test() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");

		list.add(0, "tt");

		//		list = (ArrayList<String>) list.subList(0, list.size());
		list.remove(list.size() - 1);
		System.out.println(JSON.toJSONString(list));
	}
}
