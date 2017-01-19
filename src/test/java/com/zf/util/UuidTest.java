package com.zf.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * uuid 测试8位
 * 
 * @author ZhaoFeng
 * @date 2017年1月18日
 */
public class UuidTest {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	@Test
	public void testUUid() {
		int num = 30;
		for (int i = 0; i < num; i++) {
			String s = generateShortUuid();
			System.out.println(s);
		}

	}

	/**
	 * 生成8位uuid 不能重复
	 */
	@Test
	public void generateUuid() {
		// String path = getClass().getClassLoader().getResource("uuid.txt")
		// .getPath();
		String path = System.getProperty("user.dir");
		path += "/uuid.txt";
		Set<String> set = new HashSet<String>();
		int num = 34;// 想要生成的数量
		try {
			List<String> list = FileUtils.readLines(new File(path));
			for (String s : list) {
				if (StringUtils.isNotBlank(s)) {
					if (set.contains(s)) {
						System.out.println("repeat:" + s);
					} else {
						set.add(s);
					}
				}
			}
			System.out.println("set size:" + set.size());
			String s = "";
			List<String> resultList = new ArrayList<String>();
			while (num > 0) {
				s = generateShortUuid();
				if (!set.contains(s)) {
					System.out.println(s);
					num--;
					resultList.add(s);
				}
			}
			resultList.add("");
			FileUtils.writeLines(new File(path), resultList, true);

			// System.out.println("set size:" + set.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
