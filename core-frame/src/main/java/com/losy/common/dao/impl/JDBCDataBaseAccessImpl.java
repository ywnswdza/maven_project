package com.losy.common.dao.impl;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.losy.common.annotation.EntryUtils;
import com.losy.common.dao.IDataBaseAccess;



@Repository("jdbcDataBaseAccessImpl")
public class JDBCDataBaseAccessImpl extends JdbcTemplate implements IDataBaseAccess  {
	
	@Resource(name="dataSources")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * 根据实现，插入数据，并返回自增 key
	 */
	@Override
	public <T> Serializable insert(T entry) {
		if(!EntryUtils.supportSimpleTableInsert(entry.getClass()))  throw new RuntimeException("entry " + entry.getClass().getName() + " not @table type on class !!!!");
		final List<Object> args = new ArrayList<Object>();
		final String sql = EntryUtils.getInsertSqlAndParams(entry,args);
		final KeyHolder key = new GeneratedKeyHolder();
		if(log.isDebugEnabled()) {
			log.debug("     entry : " + entry.getClass().getName());
			log.debug("insert sql : " + sql);
			log.debug("      args : " + args.toString());
		}
		this.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(args.toArray());
				try {
                    if (pss != null) {
                        pss.setValues(pstmt);
                    }
                } finally {
                    if (pss instanceof ParameterDisposer) {
                        ((ParameterDisposer) pss).cleanupParameters();
                    }
                }
				return pstmt;
			}
		},key);
		return key.getKey();
	}

	@Override
	public <T> Serializable updateEntry(T entry) {
		if(!EntryUtils.supportSimpleTableInsert(entry.getClass()))  throw new RuntimeException("entry " + entry.getClass().getName() + " not @table type on class !!!!");
		final List<Object> args = new ArrayList<Object>();
		final String sql = EntryUtils.getUpdateSqlAndParams(entry,args);
		if(log.isDebugEnabled()) {
			log.debug("     entry : " + entry.getClass().getName());
			log.debug("update sql : " + sql);
			log.debug("      args : " + args.toString());
		}
		return this.update(sql, args.toArray());
	}
	
	@Override
	public <T> T queryEntry(String sql, Class<T> clazz) {
		return this.queryEntry(sql,clazz,new Object[]{});
	}
	
	@Override
	public <T> T queryEntry(String sql, final Class<T> clazz, Object... args) {
		RowMapper<T> rm = null;
		if(EntryUtils.supportEntryQuery(clazz)) 
			rm = new RowMapper<T>() {
				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					return ResultSet2Entry(rs,rowNum,clazz);
				}
			};
		else rm = ParameterizedBeanPropertyRowMapper.newInstance(clazz);
		try {
			return this.queryForObject(sql,rm,args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	
	private <T> T ResultSet2Entry(ResultSet rs,int rowNum,Class<T> clazz){
		T t = null;
		return t;
	}

	@Override
	public <T> List<T> queryEntryList(String sql, Class<T> clazz) {
		RowMapper<T> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(clazz);
		if(EntryUtils.isSingleRowMapper(clazz)) rowMapper = getSingleColumnRowMapper(clazz);
		return this.query(sql,rowMapper);
	}

	@Override
	public <T> List<T> queryEntryList(String sql, Class<T> clazz,
			Object... args) {
		RowMapper<T> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(clazz);
		if(EntryUtils.isSingleRowMapper(clazz)) rowMapper = getSingleColumnRowMapper(clazz);
		return this.query(sql,args,rowMapper);
	}

	@Override
	public int executeSql(String sql, Object[] args) {
		return this.update(sql, args);
	}

	@Override
	public Serializable insertReturnKey(final String sql, final Object[] args) {
		KeyHolder key = new GeneratedKeyHolder();
		this.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(args);
				try {
                    if (pss != null) {
                        pss.setValues(pstmt);
                    }
                } finally {
                    if (pss instanceof ParameterDisposer) {
                        ((ParameterDisposer) pss).cleanupParameters();
                    }
                }
				return pstmt;
			}
		}, key);
		return key.getKey();
	}

	@Override
	public List<Map<String, Object>> queryForListMap(String sql, Object[] args) {
		return this.queryForList(sql, args);
	}

	@Override
	public Map<String, Object> findMapBySql(String sql, Object[] args) {
		try {
			return this.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public <T> T findBaseType(String sql, Class<T> clazz, Object... args) {
		//this.query(sql, args, getSingleColumnRowMapper(clazz));
		return this.queryForObject(sql, clazz,args);
	}

	@Override
	public int[] executeBatchSql(String[] sql) {
		return this.batchUpdate(sql);
	}

	
}
