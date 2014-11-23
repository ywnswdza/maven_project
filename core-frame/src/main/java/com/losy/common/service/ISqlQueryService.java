package com.losy.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @author losy
 *
 */
public interface ISqlQueryService {
	
	public static Logger log = Logger.getLogger(ISqlQueryService.class);

	/**
	 * 查询实例列表，sql 字段名必须和 clazz 变量名相同
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <T> List<T> queryEntryList(String sql,Class<T> clazz);
	
	public <T> T queryEntry(String sql,Class<T> clazz);
	
	public List<Map<String,Object>> queryMapList(String sql);
	
	public Map<String,Object> queryMap(String sql);
	
	public Serializable execute(String sql);
	
	public Serializable insertRtKey(String insertSql);
	
}
