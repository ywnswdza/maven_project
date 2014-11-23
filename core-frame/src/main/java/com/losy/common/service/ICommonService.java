package com.losy.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.losy.common.utils.Page;

public interface ICommonService<R> {

	public static Logger log = Logger.getLogger(ICommonService.class);
	/**
	 * 根据条件分页
	 * @param statement
	 * @param parameter
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	public Page<R> getListByPage(Object parameter, int startPage, int pageSize);
	
	
	public List<R> getListLimit(Object parameter, int offset, int limitSize);
	/**
	 * 根据主键来获取对象
	 * @param l
	 * @return
	 */
	public R getObjectById(Serializable id);
	/**
	 * 保存
	 * @param vo
	 * @return
	 */
	public R save(R vo);
	/**
	 * 批量删除删除
	 * @param ids 以逗号隔开的主键值串
	 */
	public void removeByIds(String ids);
	
	
	public List<R> getList(Object parameter);
	
	/**
	 * 删除
	 * @param id 主键值
	 */
	public void removeById(Serializable id);
	
	public R insert(R vo);
	
	public R update(R vo);
	/**
	 * 批量插入实体vo，注意list大小。
	 * @param list
	 * @return
	 */
	public List<R> insertBatch(List<R> list);
	/**
	 * 批量删除 实体vo的属性值
	 * @param list
	 */
	public void removeEntityBatch(List<R> list);
	
	public List<Map<String,Object>> selectListBySql(String sql);
	
	public Map<String,Object> selectOneBysql(String sql);
	
	public R selectEntityBySql(String sql);
	/**
	 * 执行 sql 
	 * @param sql  , insert,updata,delete语句
	 * @return
	 */
	public Serializable executeBySql(String sql);
	
	/**
	 * 执行一批sql
	 * @param sql
	 * @return
	 */
	public abstract void executeBatchSql(List<String> sql);
	
	Page<Map<String,Object>> selectPageBySql(String sql,int startPage,int pageSize);
	
}
