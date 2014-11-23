DROP TABLE IF EXISTS `${table.tableName}`;
CREATE TABLE `${table.tableName}` (
<#list table.columns as c>
<#if c.id == true>
		${c.name} INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT<#if c_has_next>,</#if>
<#else>
<#assign _javaType="${c.javaType ? lower_case}">
	<#if _javaType== 'string'>
		${c.name} ${c.jdbcType}(${c.length}) default '' comment '${c.comment}'<#if c_has_next>,</#if>
	<#elseif _javaType=='integer' || _javaType=='int'>
		${c.name} ${c.jdbcType}(${c.length}) comment '${c.comment}'<#if c_has_next>,</#if>
	<#elseif _javaType=='double' || _javaType=='float'>
		${c.name} ${c.jdbcType}(${c.length},2) comment '${c.comment}'<#if c_has_next>,</#if>
	<#else>
		${c.name} ${c.jdbcType}  comment '${c.comment}'<#if c_has_next>,</#if>
	</#if>
</#if>
</#list>	
);