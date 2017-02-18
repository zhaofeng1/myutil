/**
 * 
 */
package com.zf.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 实现具体SQL查询的基础类，将连接的使用封装起来来保证连接不被泄露,结果集缓存起来返回与连接无关
 * 
 * Create on 2013-6-28 上午10:56:23
 * 
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a.
 * 
 */
public abstract class SQLStatement extends ReleaseResource {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 执行更新语句接口，无结果集返回
	 * 
	 * @param conn
	 * @param sql
	 * @param transaction
	 * @param returnKey
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	protected int executeUpdate(Connection conn, String sql, boolean transaction, boolean returnKey, Object... params) throws SQLException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			int r = statement.executeUpdate();
			if (returnKey) {
				rs = statement.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				} else {
					return 0;
				}
			} else {
				return r;
			}
		} finally {
			attemptClose(statement);
			attemptClose(rs);
			if (!transaction) {
				attemptClose(conn);
			}
		}
	}

	/**
	 * 执行查询语句有结果集返回
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	protected DataTable executeQuery(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			rs = statement.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			ArrayList<Column> cols = new ArrayList<Column>();
			for (int i = 0; i < md.getColumnCount(); i++) {
				Column col = new Column();
				col.setLable(md.getColumnLabel(i + 1));
				col.setType(md.getColumnType(i + 1));
				col.setTypeName(md.getColumnTypeName(i + 1));
				cols.add(col);
			}

			ArrayList<DataRow> rows = new ArrayList<DataRow>();
			while (rs.next()) {
				DataRow row = new DataRow(cols);
				for (int j = 0; j < cols.size(); j++) {
					Object value = rs.getObject(j + 1);
					row.addRowData(cols.get(j).getLable(), value);
				}
				rows.add(row);
			}

			return new DataTable(cols, rows);

		} finally {
			attemptClose(rs);
			attemptClose(statement);
			attemptClose(conn);
		}
	}

	/**
	 * 释放未主动释放的连接
	 */
	protected synchronized void checkTimeoutConnection(Map<String, ManagedExecutor> managedExc) {
		logger.debug("Have " + managedExc.size() + " not closed sql connection.");
		if (managedExc.size() == 0)
			return;
		Iterator<Entry<String, ManagedExecutor>> iter = managedExc.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, ManagedExecutor> entry = iter.next();
			ManagedExecutor me = entry.getValue();
			if (me.runningTime() > DBConnectionManager.checkerInterval) {
				me.release();
				logger.debug("release " + me.getId());
				iter.remove();
			}
		}
		logger.debug("ManagedExc size is " + managedExc.size());
	}

	public abstract int executeUpdate(String sql, Object... params) throws SQLException;

	public abstract int getKeyOnExecuted(String sql, Object... params) throws SQLException;

	public abstract DataTable executeQuery(String sql, Object... params) throws SQLException;

	public abstract PrepareBatch createPrepareBatch(String sql) throws SQLException;

	public abstract SimpleBatch createSimpleBatch() throws SQLException;
}
