/**
 * 
 */
package com.zf.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 事务模式下数据库查询接口,注意该类不是线程安全的
 * 
 * Create on 2013-6-28 上午10:41:13
 * 
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a.
 * 
 */
public class DataTransaction extends SQLStatement implements ManagedExecutor {

	/**
	 * 池化数据源
	 */
	private DataSource dataSource;
	/**
	 * 数据库连接
	 */
	private Connection conn;

	/**
	 * 托管池
	 */
	private Map<String, ManagedExecutor> managedExc;

	/**
	 * 事务ID
	 */
	private final String id;

	/**
	 * 构建时间
	 */
	private final long begin;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 默认构造
	 * 
	 * @param dataSource
	 */
	public DataTransaction(DataSource dataSource, Map<String, ManagedExecutor> managedExc) {
		super();
		this.managedExc = managedExc;
		this.dataSource = dataSource;
		this.id = UUID.randomUUID().toString();
		this.begin = System.currentTimeMillis();
	}

	/**
	 * 标记开始一个事物
	 * 
	 * @throws SQLException
	 */
	public void begin() throws SQLException {
		if (conn != null) {
			throw new SQLException("Have an not closed transaction,please close it at first.");
		}
		conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		managedExc.put(id, this);
	}

	/**
	 * 执行更新语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int executeUpdate(String sql, Object... params) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Must begin a transaction at first");
		}
		return executeUpdate(conn, sql, true, false, params);
	}

	/**
	 * 回滚事务并且标记事务结束
	 * 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		// 清理正常关闭的事务
		managedExc.remove(id);
		if (conn != null) {
			conn.rollback();
			conn.setAutoCommit(true);
			conn.close();
			conn = null;
		} else
			throw new RuntimeException("Must invoke begin at first");
	}

	/**
	 * 提交事务并且标记事务结束
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		// 清理正常关闭的事务
		managedExc.remove(id);
		if (conn != null) {
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			conn = null;
		} else
			throw new RuntimeException("Must invoke begin at first");
	}

	/**
	 * 执行单条更新语句并且返回自增ID
	 * 
	 * @throws SQLException
	 * @throws SQLTransactionException
	 */
	@Override
	public int getKeyOnExecuted(String sql, Object... params) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Must begin a transaction at first");
		}
		return executeUpdate(conn, sql, true, true, params);
	}

	/**
	 * 创建批量预处理执行器
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	@Override
	public PrepareBatch createPrepareBatch(String sql) throws SQLException {
		checkTimeoutConnection(managedExc);
		// 事务内的批量不属于托管对象，因为连接是事务的
		PrepareBatch batch = new PrepareBatch(sql, conn, true, null);
		return batch;
	}

	/**
	 * 创建批量标准执行器
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public SimpleBatch createSimpleBatch() throws SQLException {
		checkTimeoutConnection(managedExc);
		// 事务内的批量不属于托管对象，因为连接是事务的
		SimpleBatch batch = new SimpleBatch(conn, true, null);
		return batch;
	}

	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.database.ManagedExecutor#release()
	 */
	@Override
	public void release() {
		try {
			if (conn != null) {
				conn.rollback();
				conn.setAutoCommit(true);
				conn.close();
				conn = null;
			} else
				throw new RuntimeException("Must invoke begin at first");
			dataSource = null;
			managedExc = null;
		} catch (SQLException e) {
			logger.error("release DB transaction ", e);
		}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.database.SQLStatement#executeQuery(java.lang.String, java.lang.Object[])
	 */
	@Override
	public DataTable executeQuery(String sql, Object... params) throws SQLException {
		return executeQuery(dataSource.getConnection(), sql, params);
	}

}
