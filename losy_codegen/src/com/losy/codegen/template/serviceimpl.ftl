package ${packagePrefix}.${projectName}.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ${packagePrefix}.common.service.impl.CommonServiceImpl;
import java.util.Date;
import ${packagePrefix}.${projectName}.dao.I${table.entitySimpleName}Dao;
import ${packagePrefix}.${projectName}.domain.${table.entitySimpleName};
import ${packagePrefix}.${projectName}.service.I${table.entitySimpleName}Service;


/**
 * @serviceimpl
 * @table ${table.tableName}
 * @date ${ddate}
 * @author ${author}
 */
@Service("${table.entitySimpleName ? uncap_first}Service")
public class ${table.entitySimpleName}ServiceImpl extends CommonServiceImpl<I${table.entitySimpleName}Dao,${table.entitySimpleName}> implements I${table.entitySimpleName}Service {

	
	@Resource(name="${table.entitySimpleName ? uncap_first}Dao")
	public void setDao(I${table.entitySimpleName}Dao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public ${table.entitySimpleName} save(${table.entitySimpleName} vo) {
<#list table.columns as col>
	<#if col.id == true>
		if(vo.get${col.fieldName ? cap_first}() == null) {
			vo.setCreateTime(new Date());
			return this.insert(vo);
		} else {
			vo.setUpdateTime(new Date());
			return this.update(vo);
		}
		<#break>
	</#if>
</#list>
	}
	
<#-- 
	@Resource(name="${table.entitySimpleName ? uncap_first}Dao")
	private I${table.entitySimpleName}Dao ${table.entitySimpleName ? uncap_first}Dao;
	
	public Page<${table.entitySimpleName}> getListByPage(Object parameter, int startPage,
			int pageSize) {
		return ${table.entitySimpleName ? uncap_first}Dao.getListByPage(parameter, startPage, pageSize);
	}

	public ${table.entitySimpleName} getObjectById(Serializable id) {
		return ${table.entitySimpleName ? uncap_first}Dao.getObjectById(id);
	}
	
	/**
	 * 根据主键执行insert或update
	 */
	public ${table.entitySimpleName} save(${table.entitySimpleName} vo) {
<#list table.columns as col>
	<#if col.isId>
		if(vo.get${col.fieldName ? cap_first}() != null) {
			return this.update(vo);
		} 
	</#if>
</#list>
		return this.insert(vo);
	}

	public void removeByIds(String ids) {
		${table.entitySimpleName ? uncap_first}Dao.removeByIds(ids);
	}

	public List<${table.entitySimpleName}> getList(Object parameter) {
		return ${table.entitySimpleName ? uncap_first}Dao.getList(parameter);
	}

	public void removeById(Serializable id) {
		${table.entitySimpleName ? uncap_first}Dao.removeById(id);
	}

	public ${table.entitySimpleName} insert(${table.entitySimpleName} vo) {
		return ${table.entitySimpleName ? uncap_first}Dao.insert(vo);
	}

	public ${table.entitySimpleName} update(${table.entitySimpleName} vo) {
		return ${table.entitySimpleName ? uncap_first}Dao.update(vo);
	}
	
	public List<${table.entitySimpleName}> insertBatch(List<${table.entitySimpleName}> list) {
		return ${table.entitySimpleName ? uncap_first}Dao.insertBatch(list);
	}

	public void removeEntityBatch(List<${table.entitySimpleName}> list) {
		${table.entitySimpleName ? uncap_first}Dao.removeEntityBatch(list);
	}
	
	public List<${table.entitySimpleName}> getListLimit(Object parameter, int offset,
			int limitSize) {
		return ${table.entitySimpleName ? uncap_first}Dao.getListLimit(parameter, offset, limitSize);
	}
-->	
}
