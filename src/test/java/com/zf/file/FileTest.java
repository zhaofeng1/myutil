package com.zf.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FileTest {

	@Test
	public void test() throws IOException {

		String path = "E:/tmp/all-data.txt";
		List<String> list = FileUtils.readLines(new File(path));

		for (String s : list) {
			String[] array = s.split("\t");
			System.out.println(JSON.toJSON(array));
		}
	}
}
