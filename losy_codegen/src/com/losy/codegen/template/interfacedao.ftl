package ${packagePrefix}.${projectName}.dao;

import ${packagePrefix}.common.dao.ICommonDao;
import ${packagePrefix}.${projectName}.domain.${table.entitySimpleName};
/**
 * @intefacedao
 * @table ${table.tableName}
 * @date ${ddate}
 * @author ${author}
 */
public interface I${table.entitySimpleName}Dao extends ICommonDao<${table.entitySimpleName}> {

}