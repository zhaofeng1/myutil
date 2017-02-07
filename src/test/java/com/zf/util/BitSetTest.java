package com.zf.util;

import java.util.BitSet;

import org.junit.Test;

/**
 * bitset 测试
 * 
 * @author ZhaoFeng
 * @date 2017年2月7日
 */
public class BitSetTest {

	@Test
	public void test() {
		BitSet set1 = new BitSet();
		set1.set(1);
		set1.set(2);
		set1.set(3);
		set1.set(9);
		BitSet set2 = new BitSet();
		set2.set(1);
		set2.set(2);
		set2.set(3);
		set2.set(9);

		set2.set(5);
		set1.and(set2);
		//		System.out.println(JSON.toJSONString(set1));
		System.out.println(set1.length());
		System.out.println(set1.toString());
		for (int i = set1.nextSetBit(0); i >= 0; i = set1.nextSetBit(i + 1))
			System.out.println(i);
	}
}
