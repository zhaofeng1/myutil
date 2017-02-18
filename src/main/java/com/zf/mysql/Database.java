/**
 * 
 */
package com.zf.mysql;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

/**
 * 
 * 自动事务模式下的SQL执行类，每次执行都会从数据源中获取最新可用连接，每次执行完归还连接入池，所以该类是线程安全的
 * 
 * Create on 2013-6-27 下午5:21:04
 * 
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a.
 * 
 */
public class Database extends SQLStatement {

	/**
	 * 池化数据源
	 */
	private final DataSource dataSource;

	/**
	 * 缓存创建的事务，超时事务自动释放
	 */
	private final Map<String, ManagedExecutor> managedExc;

	/**
	 * 通过数据源构造数据库查询接口
	 * 
	 * @param dataSource
	 */
	public Database(DataSource dataSource, Map<String, ManagedExecutor> managedExc) {
		super();
		this.dataSource = dataSource;
		this.managedExc = managedExc;
	}

	/**
	 * 更新语句执行接口
	 */
	@Override
	public int executeUpdate(String sql, Object... params) throws SQLException {
		return executeUpdate(dataSource.getConnection(), sql, false, false, params);
	}

	/**
	 * 更新语句执行并且返回自增ID
	 */
	@Override
	public int getKeyOnExecuted(String sql, Object... params) throws SQLException {
		return executeUpdate(dataSource.getConnection(), sql, false, true, params);
	}

	/**
	 * 查询语句执行接口
	 */
	@Override
	public DataTable executeQuery(String sql, Object... params) throws SQLException {
		return executeQuery(dataSource.getConnection(), sql, params);
	}

	/**
	 * 创建事务执行器
	 * 
	 * @return
	 */
	public DataTransaction createTransaction() {
		checkTimeoutConnection(managedExc);
		DataTransaction trans = new DataTransaction(dataSource, managedExc);
		return trans;
	}

	/**
	 * 正常提交或者回滚的事务，需要清理
	 */
	protected void removeTransaction(String id) {
		managedExc.remove(id);
	}

	/**
	 * 创建批量预处理执行器
	 * @param sql
	 * @throws SQLException
	 */
	@Override
	public PrepareBatch createPrepareBatch(String sql) throws SQLException {
		checkTimeoutConnection(managedExc);
		PrepareBatch batch = new PrepareBatch(sql, dataSource.getConnection(), false, managedExc);
		managedExc.put(batch.getId(), batch);
		return batch;
	}

	/**
	 * 创建批量标准执行器
	 * @return
	 * @throws SQLException
	 */
	@Override
	public SimpleBatch createSimpleBatch() throws SQLException {
		checkTimeoutConnection(managedExc);
		SimpleBatch batch = new SimpleBatch(dataSource.getConnection(), false, managedExc);
		managedExc.put(batch.getId(), batch);
		return batch;
	}

}
