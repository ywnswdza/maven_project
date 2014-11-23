package com.losy.common.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.losy.common.utils.Page;


public interface ICommonDao<T> {

	public static Logger log = Logger.getLogger(ICommonDao.class);
	public abstract T insert(T vo);
	/**
	 * 根据实现集合，插入
	 * @param list
	 * @return
	 */
	public abstract List<T> insertBatch(List<T> list);
	public abstract T update(T vo);
	/**
	 * 删除以逗号隔开的 id值 
	 * @param ids 
	 */
	public abstract void removeByIds(String ids);
	/**
	 * 根据 id 的集合删除
	 * @param ids
	 */
	public abstract void removeByIds(List<String> ids);
	/**
	 * 根据 id 删除
	 * @param id
	 */
	public abstract void removeById(Serializable id);
	public abstract void removeEntityBatch(List<T> list);
	public abstract T getObjectById(Serializable id);
	public abstract List<T> getList(Object vo);
	public abstract List<T> getListLimit(Object parameter, int offset, int limitSize);
	public abstract Page<T> getListByPage(Object parameter, int startPage, int pageSize);
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
	public Integer insertRtKeyBySql(String sql);
	
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
	/**
	 * 返回Mybatis的命名空间,了实现类实现
	 * @return
	 */
	public abstract String getNameSpace();
}