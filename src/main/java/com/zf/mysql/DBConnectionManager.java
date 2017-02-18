/**
 * 
 */
package com.zf.mysql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * 所有数据库连接通过该类产生，连接使用C3P0管理，每次连接在使用完后都会close，因此客户端无需操心连接的关闭
 * 
 * Create on 2013-6-27 下午5:19:07
 * 
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a.
 * 
 */
public class DBConnectionManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 检查未释放连接间隔时间
	 */
	public final static int checkerInterval = 1000 * 60 * 1;

	/**
	 * 缓存已创建的池化数据源
	 */
	private final static Map<Properties, DataSource> dataSources = new ConcurrentHashMap<>(new HashMap<Properties, DataSource>());

	/**
	 * 缓存创建的事务，超时事务自动释放
	 */
	private final static Map<String, ManagedExecutor> managedExc = new ConcurrentHashMap<>();

	/**
	 * 数据库操作接口缓存
	 */
	private final static Map<Properties, Database> databases = new ConcurrentHashMap<>();

	/**
	 * 数据库操作接口缓存
	 */
	private final static Map<String, Database> str2databases = new ConcurrentHashMap<>();

	/**
	 * 单例模式
	 */
	private final static DBConnectionManager INSTANCE = new DBConnectionManager();

	/**
	 * 私有构造函数
	 */
	private DBConnectionManager() {
		super();
	}

	/**
	 * 返回连接管理器实例
	 * 
	 * @return
	 */
	public static DBConnectionManager getInstance() {
		return INSTANCE;
	}

	/**
	 * 通过配置创建数据源
	 * 
	 * @param config
	 * @return
	 * @throws SQLException
	 */
	public DataSource getDataSource(Properties config) throws SQLException {
		DataSource ds = dataSources.get(config);
		if (ds == null) {
			synchronized (dataSources) {
				ds = dataSources.get(config);
				if (ds == null) {
					ds = createDataSource(config);
					dataSources.put(config, ds);
				}
			}
		}
		return ds;
	}

	/**
	 * 通过配置创建数据库查询通用接口
	 * 
	 * @param config
	 * @return
	 * @throws SQLException
	 */
	public Database getDatabase(Properties config) throws SQLException {
		Database db = databases.get(config);
		if (db == null) {
			synchronized (dataSources) {
				db = databases.get(config);
				if (db == null) {
					DataSource ds = getDataSource(config);
					db = new Database(ds, managedExc);
					databases.put(config, db);
				}
			}
		}
		return db;
	}

	/**
	 * 通过指定key缓存数据库操作器
	 * 
	 * @param key
	 * @param config
	 * @return
	 * @throws SQLException
	 */
	public Database getDatabase(String key, Properties config) throws SQLException {
		Database db = getDatabase(config);
		str2databases.put(key, db);
		return db;
	}

	/**
	 * 获取缓存的数据库操作器
	 * 
	 * @param key
	 * @return
	 */
	public Database getDatabase(String key) {
		return str2databases.get(key);
	}

	/**
	 * 构建配置
	 * 
	 * @param key
	 * @param value
	 * @param ds
	 */
	private void setConfig(String key, String value, ComboPooledDataSource ds) {
		Method method = null;
		try {
			if ("password".equals(key) || "user".equals(key)) {
				method = ds.getClass().getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1), String.class);
				method.invoke(ds, value);
			} else if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
				method = ds.getClass().getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1), boolean.class);
				method.invoke(ds, Boolean.valueOf(value));
			} else if (!value.matches("\\d*")) {
				method = ds.getClass().getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1), String.class);
				method.invoke(ds, value);
			} else {
				method = ds.getClass().getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1), int.class);
				method.invoke(ds, Integer.valueOf(value));
			}
		} catch (NoSuchMethodException e) {
			logger.error("DB config \"" + key + "\" is Invalid. ", e);
		} catch (IllegalAccessException e) {
			logger.error("DB config \"" + key + "\" is Invalid. ", e);
		} catch (IllegalArgumentException e) {
			logger.error("DB config \"" + key + "\" is Invalid. ", e);
		} catch (InvocationTargetException e) {
			logger.error("DB config \"" + key + "\" is Invalid. ", e);
		}
	}

	/**
	 * 通过配置创建池化数据源
	 * 
	 * @param config
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private DataSource createDataSource(Properties config) throws SQLException {
		if (config == null) {
			throw new IllegalArgumentException("Config can not be null");
		}

		String driverClass = config.getProperty("driverClass");
		if (driverClass == null)
			driverClass = config.getProperty("DriverClass");
		// 解决C3P0的suitable drive问题
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("driverClass can not be null", e);
		}

		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			Iterator<Entry<Object, Object>> iter = config.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Object, Object> entry = iter.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				setConfig(key, value, ds);
			}
			return (DataSource) ds;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
}
