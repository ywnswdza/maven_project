package com.losy.common.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.losy.common.dao.ISqlQueryDao;
import com.losy.common.multidatasource.DynamicDataSource;
import com.losy.common.utils.Page;

@Repository("sqlQueryDao")
public class SqlQueryDaoImpl extends SqlSessionDaoSupport implements ISqlQueryDao {

	private final static String COMMON_NAMESPACE = "com.losy.sql.sqlmapping.common";
//	@Resource(name="dataSource")
//	private ComboPooledDataSource dataSource;
	@Resource(name="dataSources")
	protected DynamicDataSource dynamicDataSource;
	
	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Override
	public Map<String, Object> selectOne(String sql) {
		return this.getSqlSession().selectOne(COMMON_NAMESPACE.concat(".queryBySql"), sql);
	}

	@Override
	public List<Map<String, Object>> selectList(String sql) {
		return this.getSqlSession().selectList(COMMON_NAMESPACE.concat(".queryBySql"),sql);
	}

	@Override
	public Serializable updateBySql(String sql) {
		return this.getSqlSession().delete(COMMON_NAMESPACE.concat(".updateBySql"), sql);
	}

	@Override
	public Serializable deleteBySql(String sql) {
		return this.getSqlSession().delete(COMMON_NAMESPACE.concat(".deleteBySql"), sql);
	}
	
	@Override
	public Serializable insertBySql(String sql) {
		return this.getSqlSession().insert(COMMON_NAMESPACE.concat(".insertBySql"), sql);
	}
	
	
	public Serializable insertRtKeyBySql(String sql){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnnection(this.getClass());
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			conn.commit();
			return rs.next() ? rs.getInt(1) : null;
		} catch (SQLException e) {
			try {conn.rollback();} catch (SQLException e1) {}
			throw new RuntimeException(e);
		} finally {
			try {
				if(conn != null && conn.getAutoCommit() == false) conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
	}
	/**
	 * use connection after ,placse close it;
	 */
	public Connection getConnnection(Class<?> clazz) {
//		return this.getSqlSession().getConnection();
		Connection conn = null;
		try {
			//SpringContextListener.getBeanFactory();
			conn = dynamicDataSource.getConnection();
//			return dataSource.getConnection();
		} catch (Exception e) {
			log.error(clazz.getName() + " getConnnection error ! ",e);
		}
		return conn;
	}
	
	/** close conn,stmt,result */
	public void close(Object obj) {
		if(null == obj) return;
		if(obj instanceof ResultSet) {
			try {
				ResultSet rs = ResultSet.class.cast(obj);
				if(rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
				log.error("close ResultSet error !",e);
			}
			return;
		}
		if(obj instanceof Statement) {
			try {
				Statement st = Statement.class.cast(obj);
				if(st != null) {
					st.close();
					st = null;
				}
			} catch (SQLException e) {
				log.error("close Statement error !",e);
			}
			return;
		}
		if(obj instanceof Connection) {
			try {
				Connection conn = Connection.class.cast(obj);
				if(conn != null) {
					conn.close();
					conn = null;
				}			
			} catch (SQLException e) {
				log.error("close Connection error !",e);
			}
			return;
		}
	}

	@Override
	public Page<Map<String, Object>> selectPageBySql(String sql, int startPage,
			int pageSize) {
		String statementId = COMMON_NAMESPACE.concat(".queryBySql");
		SqlSession session = this.getSqlSession();
		int offset = pageSize * (startPage-1);
		int limit =  pageSize;
		//RowBounds rowBounds = new RowBounds(offset, limit);
		String limitSql = dynamicDataSource.getSQLDialect().getLimitString(sql, offset, limit);
		List<Map<String, Object>> resultList = session.selectList(statementId,limitSql);
		/**计算总数**/
		Long totalCount = getCountBySql(sql);		
		return new Page<Map<String, Object>>(resultList, totalCount.intValue(),startPage, pageSize);
	}
	
	protected Long getCountBySql(String sql){
		if(StringUtils.isBlank(sql)) return 0L;
		String newsql = sql;
		sql = sql.trim().toUpperCase();
		newsql = newsql.substring(sql.indexOf("FROM"));
		newsql = "SELECT COUNT(*) as _c " + newsql;
		String flag2 = newsql.toUpperCase();
		newsql = newsql.substring(0, flag2.lastIndexOf("ORDER"));
		Map<String,Object> result = this.selectOne(newsql);
		if(result == null) return 0L;
		return (Long)result.get("_c");
	}

	@Override
	public void executeBatchSql(List<String> sql) {
		
	}
}
