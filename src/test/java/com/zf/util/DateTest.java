package com.zf.util;

import java.text.DateFormat;
import java.text.ParseException;
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

	@Test
	public void test1() throws ParseException {
		//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		System.out.println(df.parse("2013-06-19T15:42:03.000Z"));
	}

	@Test
	public void test2() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			System.out.println(getDayHourLong());
			//			Thread.sleep(1 * 1000);
		}
	}

	/**
	 * 获得当前时间小时对应的long
	 * @param date
	 * @return
	 */
	public static long getDayHourLong() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}
}
