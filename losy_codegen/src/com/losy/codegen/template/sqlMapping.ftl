<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
    PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"
    "http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">
   
<#list table.columns as column><#if column.id == true><#assign tablesId="${column.fieldName}"><#break></#if></#list>   
<mapper namespace="${packagePrefix}.${projectName}.sql.sqlmapping.${table.entitySimpleName}">
	<resultMap type="${packagePrefix}.${projectName}.domain.${table.entitySimpleName}" id="vo">
<#list table.columns as column>
	    <result property="${column.fieldName}" column="${column.name}" />
</#list>	
	</resultMap>

<#list table.columns as column>	
<#if column.id == true>
	<select id="getObjectById" parameterType="${column.javaFullType}" resultMap="vo">
		select * from ${table.tableName} where ${column.name}=${"#"}{id}
	</select>
	
	<delete id="removeById" parameterType="${column.javaFullType}">
		delete from ${table.tableName} where ${column.name} = ${"#"}{${column.fieldName}}
	</delete>
	
	<delete id="removeByIds" >
		delete from ${table.tableName} where ${column.name} in 
		<foreach item="item" index="index" collection="list" open="(" separator="," close=")">
			${"#"}{item}
		</foreach>
	</delete>	
	
	<delete id="removeEntityBatch" parameterType="java.util.List">
		delete from ${table.tableName} where ${column.name} in 
		<foreach item="item" index="index" collection="list" open="(" separator="," close=")">
			${"#"}{item.${column.fieldName}}
		</foreach>
	</delete>	
	 <#break>
</#if>
</#list>

	<select id="getListByPage"  resultMap="vo">
		select * from ${table.tableName} 
	<where>
<#list table.notKeyCoulmnInfo as column>
<#assign _fileType="${column.javaType ? lower_case}">
	<#if "${_fileType}" == 'string'>
	 	<if test="${column.fieldName}!=null and ${column.fieldName}!=''">
			AND ${column.name} like '%${"$"}{${column.fieldName}}%'
		</if> 	
	<#elseif "${_fileType}" == 'date'>
		<if test="lt${column.fieldName ? cap_first}!=null and lt${column.fieldName ? cap_first}!=''">
			<![CDATA[AND ${column.name} >= ${"#"}{lt${column.fieldName ? cap_first}} ]]>
		</if> 
		<if test="gt${column.fieldName ? cap_first}!=null and gt${column.fieldName ? cap_first}!=''">
			<![CDATA[AND ${column.name} <= ${"#"}{gt${column.fieldName ? cap_first}} ]]>
		</if> 
	<#elseif "${_fileType}" == 'boolean'>
		<if test="${column.fieldName}!=null">
			AND ${column.name} = ${"#"}{${column.fieldName}}
		</if>		
	<#else>
		<if test="${column.fieldName}!=null">
			AND ${column.name} = ${"#"}{${column.fieldName}}
		</if>	
	</#if>	    	
</#list> 
		<if test="appendWhere!=null and appendWhere!=''">
			${"$"}{appendWhere}
		</if>           
      </where>
        ORDER BY 
		<choose>
			<when test="orderBy!=null and orderBy!=''">
				${"$"}{orderBy}
			</when>
			<otherwise>
<#assign idflag="false">
<#list table.columns as column>	
<#if column.id == true>
	<#assign idflag="true">	
				${column.name}
	<#break>
</#if>	
</#list>
<#if idflag == "false">
<#list table.columns as column>	
				${column.name}
</#list>
</#if>
			</otherwise>
		</choose>
		<choose>
			<when test="descOrAsc!=null and descOrAsc!=''">
				${"$"}{descOrAsc}
			</when>
			<otherwise>
				asc 
			</otherwise>
		</choose>
	</select>
	  <insert id="insert" useGeneratedKeys="true" keyProperty="${tablesId}">
	  <!--
	  <selectKey resultType="int" keyProperty="baseId" order="BEFORE">select nextval('seq_xxx')</selectKey>
	  -->
	  <![CDATA[
	  INSERT INTO ${table.tableName}(
<#list table.columns as column>	  
			${column.name}<#if column_has_next>,</#if>
</#list>						
			) VALUES (
<#list table.columns as column>	  
			${"#"}{${column.fieldName},jdbcType=${column.jdbcType}}<#if column_has_next>,</#if>
</#list>		
		)	
		]]>
	  </insert>
	  
	  <insert id="insertBatch" parameterType="java.util.List">
	  <![CDATA[
	  		INSERT INTO ${table.tableName}(
<#list table.columns as column>	  
			${column.name}<#if column_has_next>,</#if>
</#list>	
			) VALUES 
		]]>
		<foreach collection="list" item="item" separator=",">
		<![CDATA[
			 (<#list table.columns as column>${"#"}{item.${column.fieldName},jdbcType=${column.jdbcType}}<#if column_has_next>,</#if></#list>)
		]]>
		</foreach>
	  </insert>
	  
	  <update id="update">
		UPDATE ${table.tableName}
			<set>
<#list table.notKeyCoulmnInfo as column>
<#assign _fileType="${column.javaType ? lower_case}">
			<#if "${_fileType}" == 'string'>
			 	<if test="${column.fieldName}!=null">
					<![CDATA[${column.name} = ${"#"}{${column.fieldName},jdbcType=${column.jdbcType}}<#if column_has_next>,</#if>  ]]>
				</if> 	
			<#elseif "${_fileType}" == 'date'>
				<if test="${column.fieldName}!=null">
					<![CDATA[${column.name} = ${"#"}{${column.fieldName},jdbcType=${column.jdbcType}}<#if column_has_next>,</#if>  ]]>
				</if> 
			<#elseif "${_fileType}" == 'boolean'>
				<if test="${column.fieldName}!=null">
					<![CDATA[ ${column.name} = ${"#"}{${column.fieldName},jdbcType=${column.jdbcType}}<#if column_has_next>,</#if>  ]]>
				</if>		
			<#else>
				<if test="${column.fieldName}!=null">
					 <![CDATA[ ${column.name} = ${"#"}{${column.fieldName},jdbcType=${column.jdbcType}}<#if column_has_next>,</#if>  ]]>
				</if>	
			</#if>
</#list>	
			</set>
			<where>
<#list table.keyCoulmnInfo as column>		
			 <![CDATA[ and ${column.name} = ${"#"}{${column.fieldName},jdbcType=${column.jdbcType}} ]]>  			  	
</#list>				  	
			</where>  
	  </update>	
</mapper>
