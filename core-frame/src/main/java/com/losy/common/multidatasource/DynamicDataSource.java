package com.losy.common.multidatasource;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.losy.common.utils.SpringContextListener;
import com.losy.common.utils.dialect.Dialect;
import com.losy.common.utils.dialect.MySQLDialect;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 动态数据源
 * @author losy
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private static final Logger log = Logger.getLogger(DynamicDataSource.class);
	private static final Map<String,Dialect> dialectS = new HashMap<String, Dialect>();
	private static final Map<String,String> drivers = new HashMap<String, String>();

	static {
		drivers.put(com.mysql.jdbc.Driver.class.getName(), MySQLDialect.class.getName());
	}
	
	
	@SuppressWarnings("unchecked")
	public void setTargetDataSourcesExt(Map<Object, Object> targetDataSources) {
		Map<Object, Object> selfO = null;
		Field targetDs = null;
		try {
			targetDs = this.getClass().getSuperclass().getDeclaredField("targetDataSources");
			targetDs.setAccessible(true);
			selfO = (Map<Object, Object>)targetDs.get(this);
			if(selfO == null) 
				selfO = targetDataSources; 
			else
				for (Entry<Object, Object> entry : targetDataSources.entrySet()) 
					selfO.put(entry.getKey(),entry.getValue());
		} catch (Exception e) {
			selfO = targetDataSources;
			log.error(e.getMessage(),e);
		} finally {
			if(targetDs != null)targetDs.setAccessible(false);
		}
		super.setTargetDataSources(selfO);
		super.afterPropertiesSet();
	}

	/**
	 * 取得 数据源的 key值
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		//log.info("DataSourceContextHolder key " + key);
		return DataSourceContextHolder.getDataSourceType();
	}
	
	/**
	 * 返回使用的当前数据源
	 */
	@Override
	protected DataSource determineTargetDataSource() {
		 
		return super.determineTargetDataSource();
	}
	
	/**
	 * 得到connection
	 */
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn = determineTargetDataSource().getConnection();
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		return conn;
	}
	
	/**
	 * c3p0数据源
	 * @return
	 */
	public ComboPooledDataSource getC3P0DataSource(){
		ComboPooledDataSource db = null;
		DataSource tdb = this.determineTargetDataSource();
		showDataSourceInfo(tdb);
		if(tdb instanceof ComboPooledDataSource) db = ComboPooledDataSource.class.cast(tdb);
		return db;
	}

	
	/**
	 * 根据不同的数据源取得不同的方言
	 * @return Dialect
	 */
	public  Dialect getSQLDialect(){
		Dialect dl = null;
		ComboPooledDataSource cpdb = getC3P0DataSource();
		String driver = cpdb != null ? cpdb.getDriverClass() : SpringContextListener.getContextProValue("db.driver",com.mysql.jdbc.Driver.class.getName());//dbs.getDriverClass();
		String dialectClass = drivers.get(driver);
		synchronized (dialectS) {
			if(dialectS.containsKey(dialectClass)) {
				dl = dialectS.get(dialectClass);
			} else {
				try {
					Class<?> clazz = Class.forName(dialectClass);
					dl = Dialect.class.cast(clazz.newInstance());
					dialectS.put(dialectClass, dl);
				} catch (ClassNotFoundException e) {
					log.error("class " + driver + "not found error !",e);
				} catch (Exception e) {
					log.error("class " + driver + " error !",e);
				} 
			}
		}
		return dl;
	}
	
	/**
	 * 显示dataSource使用情况，目录只支持c3p0的
	 * @param dataSource
	 */
	public static void showDataSourceInfo(DataSource dataSource) {
		try {
			if(dataSource instanceof ComboPooledDataSource) {
				ComboPooledDataSource c3p0 = ComboPooledDataSource.class.cast(dataSource);
				int num_connections = c3p0.getNumConnectionsDefaultUser();
				int num_busy_connections = c3p0.getNumBusyConnectionsDefaultUser() ;
				log.info("[C3p0 dataSource : " + c3p0.getJdbcUrl() + ",  num_connections : " + num_connections + ",  num_busy_connections : " + num_busy_connections);
			}
		} catch (SQLException e) {
			log.error("showDataSourceInfo error !",e);
		}
	}

	public int getNumConnections() throws SQLException {
		ComboPooledDataSource c3p0 = this.getC3P0DataSource();
		return c3p0 == null ? 0 : c3p0.getNumConnections();
	}
}