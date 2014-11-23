package ${packagePrefix}.${projectName}.dao.impl;

import org.springframework.stereotype.Repository;
import ${packagePrefix}.${projectName}.dao.I${table.entitySimpleName}Dao;
import ${packagePrefix}.${projectName}.domain.${table.entitySimpleName};
import ${packagePrefix}.common.dao.impl.CommonDaoImpl;

/**
 * @daoimpl
 * @table ${table.tableName}
 * @date ${ddate}
 * @author ${author}
 */
@Repository("${table.entitySimpleName ? uncap_first}Dao")
public class ${table.entitySimpleName}DaoImpl extends CommonDaoImpl<${table.entitySimpleName}> implements I${table.entitySimpleName}Dao{

	private String nameSpace = "${packagePrefix}.${projectName}.sql.sqlmapping.${table.entitySimpleName}";
	
	public String getNameSpace() {
		return nameSpace;
	}

}
