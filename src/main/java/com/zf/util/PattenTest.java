package com.zf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class PattenTest {

	@Test
	public void test() {
		Pattern ITUNES_APP_ID_PATTERN = Pattern.compile("([0-9]{1,})");
		String package_name = "https://itunes.apple.com/app/com.criticalhitsoftware.jigsawpuzzle";
		package_name = "1214788539";
		Matcher m = ITUNES_APP_ID_PATTERN.matcher(package_name);
		if (!m.matches()) {
			System.out.println("not ios appid");
		} else {
			System.out.println("ios appid");
		}
	}
}
