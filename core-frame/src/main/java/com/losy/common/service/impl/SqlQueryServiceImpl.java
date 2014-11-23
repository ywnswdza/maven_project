package com.losy.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losy.common.dao.ISqlQueryDao;
import com.losy.common.service.ISqlQueryService;
import com.losy.common.utils.ModelConverter;

@Service("sqlQueryService")
public class SqlQueryServiceImpl implements ISqlQueryService{

	@Autowired
	private ISqlQueryDao dao;
	
	@Override
	public <T> List<T> queryEntryList(String sql, Class<T> clazz) {
		return ModelConverter.toEntryList(this.queryMapList(sql), clazz);
	}

	@Override
	public <T> T queryEntry(String sql, Class<T> clazz) {
		T entry = null;
		Map<String, Object> map  = this.queryMap(sql);
		entry = ModelConverter.mapToModel(map, clazz);
		return entry;
	}

	@Override
	public List<Map<String, Object>> queryMapList(String sql) {
		return dao.selectList(sql);
	}

	@Override
	public Map<String, Object> queryMap(String sql) {
		return dao.selectOne(sql);
	}

	@Override
	public Serializable execute(String sql) {
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
	public Serializable insertRtKey(String insertSql) {
		return dao.insertRtKeyBySql(insertSql);
	}

}
