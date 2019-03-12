package com.zf.mybitset.store;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

/**
 * bitset 索引
 * @author zhaofeng
 *
 */
public class BitSetEntity {

	/**
	 * 索引类别  geo、status、platform
	 */
	private String type;

	/**
	 * 索引类型值对应的bsList中索引位置
	 * <IN,1> <US,2>
	 */
	private ConcurrentHashMap<String, Integer> map;

	/**
	 * 索引类型对应的值
	 * IN,US
	 */
	private List<String> list;

	/**
	 * [INlist,USlist]
	 */
	private List<BitSet> bsList;

	public BitSetEntity() {
		this(null);
	}

	public BitSetEntity(String type) {
		this.type = type;
		map = new ConcurrentHashMap<String, Integer>();
		list = new ArrayList<String>();
		bsList = new ArrayList<BitSet>();
	}

	/**
	 * 更新bitset数据 (fx:) str/i i i i i i str1 1 0 0 0 0 str2 1 0 1 0 1 str3 0 1 1 0 1
	 *
	 * @param str
	 * @param i
	 */
	public void update(String str, int i) {
		if (StringUtils.isEmpty(str)) {
			str = "default";
		} else {
			str = str.toLowerCase();
		}

		BitSet bitset = null;//
		// 从map里拿出bitset索引位置
		Integer index = this.getMap().get(str);
		// 如果索引不为空 即bitset里包含了当前元素
		if (index != null) {
			// 拿出对应的bitset
			try {
				bitset = this.getBsList().get(index);
				if (bitset == null) {
					bitset = new BitSet();
					this.getBsList().add(index, bitset);
				}
				bitset.set(i, true);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// 将str对应的位置为1
		} else {
			// 拿出entity中的 list list主要是为了排序
			bitset = new BitSet();
			List<String> list = this.getList();
			list.add(str);
			index = list.size() - 1;
			bitset.set(i, true);
			this.getBsList().add(bitset);
			this.getMap().put(str, index);
		}
	}

	public void updateList(Collection<String> strs, int i) {
		if (strs != null) {
			for (String s : strs) {
				update(s, i);
			}
		}
	}

	/**
	 * 获取bitset 值为true的  即把bitset翻译为list索引
	 * @param bitset
	 * @return
	 */
	public static List<Integer> getRealIndexs(BitSet bitset) {
		List<Integer> indexs = new ArrayList<Integer>();
		if (bitset != null) {
			int i = bitset.nextSetBit(0);
			if (i != -1) {
				indexs.add(i);
				for (i = bitset.nextSetBit(i + 1); i >= 0; i = bitset.nextSetBit(i + 1)) {
					int endOfRun = bitset.nextClearBit(i);
					do {
						indexs.add(i);
					} while (++i < endOfRun);
				}
			}
		}

		return indexs;
	}

	/**
	 * 从entity里拿出符合条件的bitset
	 *
	 * @param str
	 * @return
	 */
	public BitSet get(String str) {
		BitSet bitset = null;
		str = str.toLowerCase();
		Integer index = this.getMap().get(str);

		if (index != null) {
			bitset = this.getBsList().get(index);
		} else {
			bitset = new BitSet();
		}
		return bitset;
	}

	/**
	 * bitset的与运算 拆分出来
	 *
	 * @param str
	 * @param bitset
	 * @return
	 */
	public BitSet and(String str, BitSet bitset) {
		if (str != null) {
			str = str.toLowerCase();
			if (bitset != null) {
				bitset.and(get(str));
			} else {
				bitset = new BitSet();
				bitset.or(get(str));
			}
		}
		return bitset;
	}

	/**
	 * bitset的或运算 拆分出来
	 *
	 * @param str
	 * @param bitset
	 * @return
	 */
	public BitSet or(String str, BitSet bitset) {
		if (str != null) {
			str = str.toLowerCase();
			if (bitset != null) {
				bitset.or(get(str));
			} else {
				bitset = new BitSet();
				bitset.or(get(str));
			}
		}
		return bitset;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ConcurrentHashMap<String, Integer> getMap() {
		return map;
	}

	public void setMap(ConcurrentHashMap<String, Integer> map) {
		this.map = map;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<BitSet> getBsList() {
		return bsList;
	}

	public void setBsList(List<BitSet> bsList) {
		this.bsList = bsList;
	}
}
