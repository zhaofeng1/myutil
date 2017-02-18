/**
 * 
 */
package com.zf.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * 事务内的批量预处理类
 *
 * Create on 2013-10-24 下午12:10:05
 *
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a>.
 * 
 */
public class PrepareBatch extends ReleaseResource implements ManagedExecutor {

	/**
	 * sql预处理
	 */
	private Statement statement;

	/**
	 * 批量操作数据库连接
	 */
	private Connection batchConn;

	/**
	 * 是否在事务内
	 */
	private final boolean trans;

	/**
	 * 托管池
	 */
	private Map<String, ManagedExecutor> managedExc;

	private final String id;

	/**
	 * 构建时间
	 */
	private final long begin;

	public PrepareBatch(String sql, Connection conn, boolean trans, Map<String, ManagedExecutor> managedExc) throws SQLException {
		super();
		this.batchConn = conn;
		this.statement = this.batchConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		this.trans = trans;
		this.id = UUID.randomUUID().toString();
		this.managedExc = managedExc;
		this.begin = System.currentTimeMillis();
	}

	/**
	 * 设置预处理执行器的SQL参数
	 * 
	 * @param params
	 * @throws SQLException
	 */
	public void addParams(String... params) throws SQLException {
		if (statement instanceof PreparedStatement) {
			for (int i = 0; i < params.length; i++) {
				((PreparedStatement) statement).setString(i + 1, params[i]);
			}
			((PreparedStatement) statement).addBatch();
		} else {
			throw new RuntimeException("Statement type must be PreparedStatement");
		}
	}

	/**
	 * 执行批量脚本
	 * 
	 * @return
	 * @throws SQLException
	 */
	private int[] execute() throws SQLException {
		return statement.executeBatch();
	}

	/**
	 * 返回影响函数的执行接口
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int[] executeUpdate() throws SQLException {
		try {
			return execute();
		} finally {
			attemptClose(statement);
			statement = null;
			if (!trans) {
				attemptClose(batchConn);
				batchConn = null;
				managedExc.remove(id);
			}
		}
	}

	/**
	 * 返回自增主键的批量执行接口
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Integer> getKeyOnExecuted() throws SQLException {
		ResultSet rs = null;
		try {
			execute();
			rs = statement.getGeneratedKeys();
			List<Integer> lst = new ArrayList<>();
			while (rs.next()) {
				lst.add(rs.getInt(1));
			}
			return lst;
		} finally {
			attemptClose(statement);
			statement = null;
			attemptClose(rs);
			if (!trans) {
				attemptClose(batchConn);
				batchConn = null;
				managedExc.remove(id);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.database.ManagedExecutor#release()
	 */
	@Override
	public void release() {
		if (!trans) {
			attemptClose(statement);
			statement = null;
			attemptClose(batchConn);
			batchConn = null;
			managedExc = null;
		} else
			throw new IllegalArgumentException("not support");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.database.ManagedExecutor#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.database.ManagedExecutor#runningTime()
	 */
	@Override
	public long runningTime() {
		return System.currentTimeMillis() - begin;
	}

}
