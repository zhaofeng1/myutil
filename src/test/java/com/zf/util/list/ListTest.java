package com.zf.util.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

	@Test
	public void test1() {

		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		Set<Integer> set1 = new HashSet<Integer>();
		set1.add(2);
		set1.add(5);

		set.retainAll(set1);
		System.out.println(JSON.toJSONString(set));

	}
}
