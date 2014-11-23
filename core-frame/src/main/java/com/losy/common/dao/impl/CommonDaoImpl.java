package com.losy.common.dao.impl;

import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.losy.common.dao.ICommonDao;
import com.losy.common.multidatasource.DynamicDataSource;
import com.losy.common.utils.Page;
import com.losy.common.utils.mybatis.Constants;


public abstract class CommonDaoImpl<T> extends SqlSessionDaoSupport implements ICommonDao<T>   {
	private final static String COMMON_NAMESPACE = "com.losy.sql.sqlmapping.common";
//	@Resource(name="dataSource")
//	private ComboPooledDataSource dataSource;
	@Resource(name="dataSources")
	protected DynamicDataSource dynamicDataSource;
	
	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	/**
	 * 批处理 sql,正确的sql提交，错误的sql写入日志
	 */
	public void executeBatchSql(List<String> sqlList) {
		Connection conn = null;
		Statement pstatm = null;
		long sTime = System.currentTimeMillis();
		try {
			log.info("executeBatchSql >>>>  size " + sqlList.size());
			conn = this.getConnnection(CommonDaoImpl.class);
			conn.setAutoCommit(false);
			pstatm = conn.createStatement();
			for (String sql : sqlList) {
				pstatm.addBatch(sql);
			}
			pstatm.executeBatch();
			conn.commit();
		} catch (BatchUpdateException bue) {
			log.error(bue.getMessage(),bue);
			int[] r = bue.getUpdateCounts();
			for(int j=0; j<r.length; j++){
				if(r[j] == Statement.EXECUTE_FAILED){
					log.error("bad sql : " + sqlList.get(j));
				} 
			}
			try {
				conn.commit(); //提交正确的sql ?????
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e.getMessage(),e1);
			}
			log.error(e.getMessage(),e);
		} finally {
			try {
				if(conn!= null)conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			this.close(pstatm);
			this.close(conn);
			log.info("executeBatchSql >>>>  end use time " + (System.currentTimeMillis() - sTime) + "ms");
		}
	}

	public Page<T> getListByPage( Object parameter, int startPage, int pageSize){
		return getListByPage(parameter, startPage, pageSize, this.getNameSpace().concat(".getListByPage"));
	}

	protected Page<T> getListByPage( Object parameter, int startPage, int pageSize, String statementId){
		SqlSession session = this.getSqlSession();
		int offset = pageSize * (startPage-1);
		int limit =  pageSize;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<T> resultList = session.selectList(statementId, parameter, rowBounds);
		/**计算总数**/
		Long totalCount = (Long)session.selectOne(statementId + Constants.IBATIS3_AUTO_COUNT_SUFFIX , parameter);		
		log.debug("totalCount : " + totalCount);
		return new Page<T>(resultList, totalCount.intValue(),startPage , pageSize);
	}	


	@SuppressWarnings("unchecked")
	public T getObjectById(Serializable id) {
		String statement = this.getNameSpace().concat(".getObjectById");
		Object obj = getSqlSession().selectOne(statement, id);
		return (T)obj;
	}
	

	public T insert(T vo) {
		String statement = this.getNameSpace().concat(".insert");
		getSqlSession().insert(statement, vo);
		return vo;
	}

	public List<T> insertBatch(List<T> list) {
		getSqlSession().insert(getNameSpace().concat(".insertBatch"),list);
//		throw new RuntimeException("测试事务回滚");
		return list;
	}

	public T update(T vo) {
		String statement = this.getNameSpace().concat(".update");
		getSqlSession().update(statement, vo);
		return vo;
	}	


	public void removeByIds(String ids) {
		String[] args = ids.split(",");
		String statement = this.getNameSpace().concat(".removeByIds");
		List<String> idList = new ArrayList<String>();
		for(String s : args){
			idList.add(s);
		}
		this.getSqlSession().delete(statement, idList);
	}

	
	
	public void removeEntityBatch(List<T> list) {
		String statement = this.getNameSpace().concat(".removeEntityBatch");
		getSqlSession().delete(statement, list);
	}

	public List<T> getList(Object vo) {		
		String statement = this.getNameSpace().concat(".getListByPage");
		return getSqlSession().selectList(statement, vo);
	}
	
	public void removeByIds(List<String> idList) {
		String statement = this.getNameSpace().concat(".removeByIds");
		getSqlSession().delete(statement, idList);
	}	
	
	
	
	public void removeById(Serializable id) {
		String statement = this.getNameSpace().concat(".removeById");
		getSqlSession().delete(statement, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Page<?> getObjectListByPage(Object parameter, int startPage, int pageSize, String statementId){
		SqlSession session = this.getSqlSession();
		int offset = pageSize * (startPage-1);
		int limit =  pageSize;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<?> resultList = session.selectList(statementId, parameter, rowBounds);
		/**计算总数**/
		Long totalCount = (Long)session.selectOne(statementId + Constants.IBATIS3_AUTO_COUNT_SUFFIX , parameter);		
		return new Page(resultList, totalCount.intValue(),startPage, pageSize);
	}

	@Override
	public List<T> getListLimit(Object parameter, int offset, int limitSize) {
		String statement = this.getNameSpace().concat(".getListByPage");
		return getListLimit(parameter, offset, limitSize, statement);
	}

	protected List<T> getListLimit(Object parameter, int offset, int limitSize,String statementId) {
		RowBounds rowBounds = new RowBounds(offset, limitSize);
		List<T> resultList = this.getSqlSession().selectList(statementId, parameter, rowBounds);
		return resultList;
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
	
	
	public Integer insertRtKeyBySql(String sql){
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
	
}
