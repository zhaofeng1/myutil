package com.zf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class DateTest {

	@Test
	public void test() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String value_hour = "72";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, Integer.valueOf(value_hour) * -1);

		System.out.println(sdf.format(c.getTime()));
	}
}
