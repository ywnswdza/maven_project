package ${packagePrefix}.${projectName}.domain;

import com.losy.common.domain.BaseVo;
<#if table.imports ??>
<#list table.imports as item>
import ${item};
</#list>
</#if>
/**
 * @entity
 * @table ${table.tableName}
 * @date ${ddate}
 * @author ${author}
 */
public class ${table.entitySimpleName} extends BaseVo {

<#list table.columns as column>
	<#if column.comment ?? && column.comment != ''>
	/** ${column.comment} */
	</#if>
	private ${column.javaType} ${column.fieldName};
	<#if column.javaType == 'Date'>
	/**判断${column.label}，大于或等于 */
	private ${column.javaType}	gt${column.fieldName ? cap_first};
	/**判断${column.label}，小于或等于 */
	private ${column.javaType}	lt${column.fieldName ? cap_first};
	</#if>	
</#list>

<#list table.columns as column>
	public void set${column.fieldName ? cap_first}(${column.javaType} ${column.fieldName}){
		this.${column.fieldName} = ${column.fieldName};
	}
	public ${column.javaType} get${column.fieldName ? cap_first}() {
		return this.${column.fieldName};
	}
	<#if column.javaType == 'Date'>
	public void setGt${column.fieldName ? cap_first}(${column.javaType} gt${column.fieldName ? cap_first}){
		this.gt${column.fieldName ? cap_first} = gt${column.fieldName ? cap_first};
	}
	@JsonIgnore
	public ${column.javaType} getGt${column.fieldName ? cap_first}() {
		return this.gt${column.fieldName ? cap_first};
	}
	public void setLt${column.fieldName ? cap_first}(${column.javaType} lt${column.fieldName ? cap_first}){
		this.lt${column.fieldName ? cap_first} = lt${column.fieldName ? cap_first};
	}
	@JsonIgnore
	public ${column.javaType} getLt${column.fieldName ? cap_first}() {
		return this.lt${column.fieldName ? cap_first};
	}
	</#if>	
</#list>

}