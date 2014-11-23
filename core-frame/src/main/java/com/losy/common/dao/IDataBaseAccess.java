package com.losy.common.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public interface IDataBaseAccess {

	static final Logger log = Logger.getLogger(IDataBaseAccess.class);
	
	public <T> Serializable insert(T entry); 
	
	public <T> T queryEntry(String sql,Class<T> clazz);
	
	public <T> T queryEntry(String sql,Class<T> clazz,Object ... args);
	
    public <T> List<T> queryEntryList(String sql,Class<T> clazz);
	
	public <T> List<T> queryEntryList(String sql,Class<T> clazz,Object ... args);
	
	public <T> Serializable updateEntry(T entry);

	public int executeSql(String sql, Object[] args);

	public Serializable insertReturnKey(String sql, Object[] args);

	public List<Map<String, Object>> queryForListMap(String sql, Object[] args);

	public Map<String, Object> findMapBySql(String sql, Object[] args);
	
	public <T> T findBaseType(String sql, Class<T> clazz, Object... args);

	public int[] executeBatchSql(String[] sql);
}
