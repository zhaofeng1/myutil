package com.zf.util.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * 列表排序
 * @author ZhaoFeng
 * @date 2017年3月28日
 */
public class SortTest {

	@Test
	public void test() {
		List<People> list = new ArrayList<People>();
		People p = new People(new Long(1), 0.6);
		People p1 = new People(new Long(2), 0.3);
		People p2 = new People(null, 0.3);
		People p3 = new People(new Long(3), null);
		People p4 = new People(new Long(4), 0.5);
		list.add(p);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);

		//		Character[] CharacterType = { Character.UNKNOWN, Character.IN, Character.OUT, Character.BOTH };
		//		Comparator TypeComparator = new FixedOrderComparator(CharacterType);

		ComparatorChain chain = new ComparatorChain();
		Comparator mycmp1 = ComparableComparator.getInstance();
		mycmp1 = ComparatorUtils.reversedComparator(mycmp1); //逆序  
		//		mycmp1 = ComparatorUtils.nullHighComparator(mycmp1); //允许null  
		mycmp1 = ComparatorUtils.nullHighComparator(mycmp1); //允许null  
		//		moInfoComparator.addComparator(new BeanComparator("character", TypeComparator));
		//		chain.addComparator(new BeanComparator("conRate"), true);
		chain.addComparator(new BeanComparator("id", mycmp1));
		chain.addComparator(new BeanComparator("conRate", mycmp1));
		//		chain.addComparator(new BeanComparator("id"));
		//		moInfoComparator.addComparator(new BeanComparator("name"));
		//		moInfoComparator.addComparator(new BeanComparator("sex"));

		Collections.sort(list, chain);
		for (People pe : list) {
			System.out.println(JSON.toJSONString(pe));
		}
	}

	@Test
	public void test1() {
		List<People> list = new ArrayList<People>();
		People p = new People();
		p.setId(1L);
		p.setConRate(0.1);
		People p1 = new People();
		p1.setId(2L);
		p1.setConRate(0.9);
		People p2 = new People();
		p2.setConRate(0.8);
		People p3 = new People();
		p3.setId(3L);
		People p4 = new People();
		p4.setId(4L);
		p4.setConRate(0.2);
		list.add(p);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);

		//		Character[] CharacterType = { Character.UNKNOWN, Character.IN, Character.OUT, Character.BOTH };
		//		Comparator TypeComparator = new FixedOrderComparator(CharacterType);

		ComparatorChain chain = new ComparatorChain();
		Comparator mycmp1 = ComparableComparator.getInstance();
		mycmp1 = ComparatorUtils.reversedComparator(mycmp1); //逆序  
		//		mycmp1 = ComparatorUtils.nullHighComparator(mycmp1); //允许null  
		mycmp1 = ComparatorUtils.nullHighComparator(mycmp1); //允许null  
		//		moInfoComparator.addComparator(new BeanComparator("character", TypeComparator));
		//		chain.addComparator(new BeanComparator("conRate"), true);
		chain.addComparator(new BeanComparator("conRate", mycmp1));
		chain.addComparator(new BeanComparator("id", mycmp1));
		//		chain.addComparator(new BeanComparator("id"));
		//		moInfoComparator.addComparator(new BeanComparator("name"));
		//		moInfoComparator.addComparator(new BeanComparator("sex"));

		Collections.sort(list, chain);
		for (People pe : list) {
			System.out.println(JSON.toJSONString(pe));
		}
	}

}
