package com.zf.mysql;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库连接
 * 
 * @author ZhaoFeng
 * @date 2017年2月18日
 */
public class DbTest {
	//	private final Logger logger = Logger.getLogger(getClass());
	private static final Logger logger = LoggerFactory.getLogger(DbTest.class);

	private static Database db = null;

	@Before
	public void initDb() {
		logger.error("init db");
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = DbTest.class.getClassLoader().getResourceAsStream("dsp-static-db-v2.xml");
			prop.loadFromXML(is);
			db = DBConnectionManager.getInstance().getDatabase(prop);
		} catch (Exception e) {
			//			e.printStackTrace();
			logger.error("init db error:" + e);
		}
	}

	@Test
	public void testSelect() {
		logger.info("test start");
		StringBuffer sql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		sql.append("select id,name,pass from Test_Zf where name like ?");
		list.add("%zf%");

		try {
			DataTable dt = db.executeQuery(sql.toString(), list.toArray());
			for (DataRow dr : dt.getRows()) {
				System.out.println(dr.getLong("id"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
