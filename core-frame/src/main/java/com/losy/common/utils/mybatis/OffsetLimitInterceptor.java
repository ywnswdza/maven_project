package com.losy.common.utils.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.losy.common.multidatasource.DynamicDataSource;
import com.losy.common.utils.SpringContextListener;
import com.losy.common.utils.dialect.Dialect;
/**
 * * 为ibatis3提供基于方言(Dialect)的分页查询的插件 将拦截Executor.query()方法实现分页方言的插入.
 * 自动生成count语句。
 * @author Long
 *
 */
@Intercepts( { @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class OffsetLimitInterceptor implements Interceptor {

	private static final ReadWriteLock rw = new ReentrantReadWriteLock();
	static Logger logger = Logger.getLogger(OffsetLimitInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
//	Dialect dialect;

	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation,invocation.getArgs());
		return invocation.proceed();
	}

	void processIntercept(Invocation invocation, final Object[] queryArgs) {
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX]; 
		Object parameter = queryArgs[PARAMETER_INDEX]; // 获取拦截的参数
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX]; // 取分页信息
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		// 分页处理
		DynamicDataSource dynamicDataSource = SpringContextListener.getBean("dataSources",DynamicDataSource.class);
		Dialect dialect = dynamicDataSource.getSQLDialect();
		if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			BoundSql boundSql = ms.getBoundSql(parameter);
			String defaultSql = boundSql.getSql().trim();
			String sql = defaultSql;
			// 根据分页方言处理器生成分页语句
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}
			limit = RowBounds.NO_ROW_LIMIT;
			// 替换旧SQL语句
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			copyAdditionalProps(boundSql,newBoundSql);
			MappedStatement newMs = copyFromMappedStatement(ms.getId(), ms,new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
			try {
				synchronized (rw) {
					String autoCountMsId = ms.getId() + Constants.IBATIS3_AUTO_COUNT_SUFFIX;
					boolean hasCount = ms.getConfiguration().hasStatement(autoCountMsId);
					if(hasCount == false) {
						long startime = System.currentTimeMillis();
						MetaObject msObj = MetaObject.forObject(ms, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY);
					    @SuppressWarnings("unchecked")
						List<SqlNode> list = (List<SqlNode>)msObj.getValue("sqlSource.rootSqlNode.contents");
						SqlSource countSqlSource = null;
						SqlNode countMixedSqlNode = null;
						List<SqlNode> list2 = new ArrayList<SqlNode>();
						boolean isSelect = false;
						for (SqlNode sqlNode : list) {
							if(isSelect == false) {
								MetaObject sqlNodeObj = MetaObject.forObject(sqlNode, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY);
								String text = (String)sqlNodeObj.getValue("text"); 
								String textUp = text.toUpperCase();
								if(textUp.contains("SELECT") && textUp.contains("FROM")) {
									SqlNode sqlNode2  = new TextSqlNode("SELECT count(0) as totalCount " + text.substring(textUp.indexOf("FROM")));
									list2.add(sqlNode2);
									isSelect = true;
									continue;
								}
							}
							list2.add(sqlNode);
						}
						countMixedSqlNode = new MixedSqlNode(list2); 
						countSqlSource = new DynamicSqlSource(ms.getConfiguration(), countMixedSqlNode);
						MappedStatement autoCountMs = copyFromMappedStatementCount(autoCountMsId,ms,countSqlSource);
						ms.getConfiguration().addMappedStatement(autoCountMs);
						logger.debug("Create Auto count "+autoCountMsId+" user time "+(System.currentTimeMillis() - startime)+" millis");
					}
				}
			} catch (Exception e) {
				logger.debug("Create Auto count statement "+ms.getId()+" error.");
			} finally {
//				rw.readLock().unlock();
			}
		}
	}

	private MappedStatement copyFromMappedStatementCount(String newId, MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), newId, newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
//		builder.keyProperty(ms.getKeyProperties());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		/**
		 * 在这里要把resultmap转成long
		 */
		ResultMap.Builder resultBuilder = new ResultMap.Builder(null, newId, Long.class, new ArrayList<ResultMapping>()); 
		ResultMap longRm = resultBuilder.build();
		List<ResultMap> rsList = new ArrayList<ResultMap>();
		rsList.add(longRm);
		builder.resultMaps(rsList);
		builder.resultSetType(ms.getResultSetType());

		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}

	private MappedStatement copyFromMappedStatement(String newId, MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), newId, newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
//		logger.info("" + ms.getKeyProperties().length);
		builder.keyProperty(ms.getKeyProperties() != null &&  ms.getKeyProperties().length > 1 ?ms.getKeyProperties()[0]:"");
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}	
	
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
////		String dbDriver = new PropertiesHelper(SystemContext.getContextPro()).getRequiredString("jdbc.driverClassName");
//		String dbDriver = SpringContextListener.getContextProValue("db.driver","");
//		String dialectClass = "";
//		try {
////			dialectClass = SpringContextListener.getContextMapValue(dbDriver, String.class);
//			dialect = (Dialect) Class.forName(dialectClass).newInstance();
//		} catch (Exception e) {
//			throw new RuntimeException( "cannot create dialect instance by dialectClass:" + dialectClass, e);
//		}
//		logger.debug("{}.dialect={}", OffsetLimitInterceptor.class.getSimpleName(), dialectClass);
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
	/**
	 * 拷贝特殊参数
	 * @param boundSql
	 * @param newBoundSql
	 */
	public void copyAdditionalProps(BoundSql boundSql, BoundSql newBoundSql){
	    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	    if (parameterMappings != null) {
	      for (int i = 0; i < parameterMappings.size(); i++) {
	        ParameterMapping parameterMapping = parameterMappings.get(i);
	        if (parameterMapping.getMode() != ParameterMode.OUT) {
	          String propertyName = parameterMapping.getProperty();
	          PropertyTokenizer prop = new PropertyTokenizer(propertyName);
	          if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
	              && boundSql.hasAdditionalParameter(prop.getName())) {
	        	  newBoundSql.setAdditionalParameter(prop.getName(), boundSql.getAdditionalParameter(prop.getName()));
	          }
	        }
	      }
	    }		
	}
}
