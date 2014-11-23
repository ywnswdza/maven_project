package com.losy.common.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.losy.common.utils.Page;

public interface ISqlQueryDao {

	public static Logger log = Logger.getLogger(ISqlQueryDao.class);
	/**
	 * 执行一批sql ,批处理 sql,正确的sql提交，错误的sql写入日志
	 * @param sql
	 * @return
	 */	
	public abstract void executeBatchSql(List<String> sql);
	/**
	 *  sql 查询单行记录
	 * @param sql
	 * @return
	 */
	public abstract Map<String,Object> selectOne(String sql);
	/**
	 * 查询列表
	 * @param sql
	 * @return  list[map[column,value]]
	 */
	public abstract List<Map<String,Object>> selectList(String sql);
	/**
	 * 
	 * @param sql
	 * @return Serializable 影响条数
	 */
	public abstract Serializable insertBySql(String sql);
	
	/**
	 * 
	 * @param sql
	 * @return Integer 反回自增的 id
	 */
	public Serializable insertRtKeyBySql(String sql);
	
	/**
	 * 
	 * @param sql
	 * @return Serializable 影响条数
	 */
	public abstract Serializable updateBySql(String sql);
	/**
	 * 
	 * @param sql
	 * @return Serializable 影响条数
	 */
	public abstract Serializable deleteBySql(String sql);
	
	public abstract Page<Map<String,Object>> selectPageBySql(String sql,int startPage,int pageSize);
	
	public abstract Connection getConnnection(Class<?> clazz);
	/**
	 * 关闭 connection , statment,resultset
	 * @param obj
	 */
	public abstract void close(Object obj);
	
}
