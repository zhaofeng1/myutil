/**
 * 
 */
package com.zf.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 释放数据库资源
 *
 * Create on 2013-10-21 下午7:26:09
 *
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a>.
 * 
 */
public abstract class ReleaseResource {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 资源关闭
	 * 
	 * @param o
	 */
	protected void attemptClose(ResultSet o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 资源关闭
	 * 
	 * @param o
	 */
	protected void attemptClose(Statement o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 资源关闭
	 * 
	 * @param o
	 */
	protected void attemptClose(Connection o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
