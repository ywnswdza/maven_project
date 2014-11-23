package com.losy.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.losy.common.dao.ICommonDao;
import com.losy.common.domain.BaseVo;
import com.losy.common.service.ICommonService;
import com.losy.common.utils.ModelConverter;
import com.losy.common.utils.Page;

public abstract class CommonServiceImpl<D extends ICommonDao<R>,R> implements ICommonService<R> {

	protected D dao;
	
	protected Class<R> clazz;
	
	@SuppressWarnings("unchecked")
	public CommonServiceImpl() {
		Type [] trueType = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments();
		//this.entityClass  = (Class<T>)trueType[1];
		Type type = trueType[1];
		if(type instanceof Class) {
			this.clazz = (Class<R>)trueType[1];
		} else {
			//type = ((ParameterizedType)type).getRawType();
			log.info(type.getClass());
		}
	}

	public Page<R> getListByPage(Object parameter, int startPage,
			int pageSize) {
		return dao.getListByPage(parameter, startPage, pageSize);
	}

	public R getObjectById(Serializable id) {
		return dao.getObjectById(id);
	}
	
	public void removeByIds(String ids) {
		dao.removeByIds(ids);
	}
	
	public List<R> getList(Object parameter) {
		return dao.getList(parameter);
	}

	public void removeById(Serializable id) {
		dao.removeById(id);
	}

	public R insert(R vo) {
		return dao.insert(vo);
	}

	public R update(R vo) {
		return dao.update(vo);
	}
	
	public List<R> insertBatch(List<R> list) {
		return dao.insertBatch(list);
	}

	public void removeEntityBatch(List<R> list) {
		dao.removeEntityBatch(list);
	}
	
	public List<R> getListLimit(Object parameter, int offset,
			int limitSize) {
		return dao.getListLimit(parameter, offset, limitSize);
	}
	
	public List<Map<String,Object>> selectListBySql(String sql) {
		return dao.selectList(sql);
	}
	
	public Map<String,Object> selectOneBysql(String sql) {
		Map<String,Object> result = null;
		result = dao.selectOne(sql);
		if(result == null) result = new HashMap<String, Object>();
		return result;
	}
	
	
	@Override
	public R selectEntityBySql(String sql) {
		Map<String,Object> result = dao.selectOne(sql);
		if(result == null) return null; 
		return ModelConverter.mapToModel(result,clazz);
	}

	/**
	 * 方法没有实现
	 */
	public R save(R vo){
		if(vo instanceof BaseVo) {
			
		}
		return vo;
	}
	
	
	
	@Override
	public void executeBatchSql(List<String> sql) {
		dao.executeBatchSql(sql);
	}

	@Override
	public Serializable executeBySql(String sql) {
		Serializable effectCount = null;
		if(StringUtils.isNotBlank(sql)) {
			sql = sql.trim();
			if(sql.toLowerCase().startsWith("insert")) {
				effectCount = dao.insertBySql(sql);
			} else if(sql.toLowerCase().startsWith("update")) {
				effectCount = dao.updateBySql(sql);
			} else if(sql.toLowerCase().startsWith("delete")) {
				effectCount = dao.deleteBySql(sql);
			}
		}
		return effectCount;
	}

	
	@Override
	public Page<Map<String, Object>> selectPageBySql(String sql, int startPage,
			int pageSize) {
		return dao.selectPageBySql(sql, startPage, pageSize);
	}

	public abstract void setDao(D dao);
}
