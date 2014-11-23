package ${packagePrefix}.${projectName}.service;

import ${packagePrefix}.common.service.ICommonService;
import ${packagePrefix}.${projectName}.domain.${table.entitySimpleName};
/**
 * @interfaceservice
 * @table ${table.tableName}
 * @date ${ddate}
 * @author ${author}
 */
public interface I${table.entitySimpleName}Service extends ICommonService<${table.entitySimpleName}> {

}
