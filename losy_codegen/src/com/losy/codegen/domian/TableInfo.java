package com.losy.codegen.domian;

import java.util.ArrayList;
import java.util.List;

import com.losy.codegen.annotation.Table;
import com.losy.codegen.utils.ReadPropertiesUtil;

public class TableInfo {

	private List<ColumnInfo> columns;

	private String entityName;

	private String entitySimpleName;
	
	private String fileName;
	
	private String schema;
	
	private String tableName;
	
	private String comment;
	
	private List<String> imports;
	
	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntitySimpleName() {
		return entitySimpleName;
	}

	public void setEntitySimpleName(String entitySimpleName) {
		this.entitySimpleName = entitySimpleName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports = imports;
	}
	
	public void addImport(String className){
		if(className.startsWith("java.lang") || className.indexOf(".") == -1) return;
		if(className.equals("java.util.Date")) importDate = true;
		if(this.imports == null) this.imports = new ArrayList<String>();
		if(!this.imports.contains(className))this.imports.add(className);
	}
	
	public void addColumn(ColumnInfo ci){
		if(this.columns == null) this.columns = new ArrayList<ColumnInfo>();
		if(ci.getFieldName().equals("createTime")) this.hasCreateTime = true;
		if(ci.getFieldName().equals("updateTime")) this.hasUpdateTime = true;
		this.columns.add(ci);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public static TableInfo init(Table table) {
		TableInfo ti = new TableInfo();
		if(table != null) {
			ti.setSchema(table.schema());
			ti.setTableName(table.name());
			ti.setComment(table.comment());
		}
		return ti;
	}

	@Override
	public String toString() {
		return "TableInfo [columns=" + columns + ", entityName=" + entityName
				+ ", entitySimpleName=" + entitySimpleName + ", fileName="
				+ fileName + ", schema=" + schema + ", tableName=" + tableName
				+ ", comment=" + comment + ", imports=" + imports + "]";
	}

	private Boolean hasCreateTime = false;
	private Boolean hasUpdateTime = false;
	private Boolean importDate = false;
	public void validation() {
		if(this.tableName == null || "".equals(this.tableName)) this.tableName = "t_" + this.entitySimpleName;
		if(ReadPropertiesUtil.getBooleanValue("domian.addCreateOrUpdateTime")) {
			if(this.hasUpdateTime == false) {
				ColumnInfo c = new ColumnInfo();
				c.setName("updateTime");
				c.setComment("更新时间");
				c.setFieldName("updateTime");
				c.setJavaFullType("java.util.Date");
				c.setJavaType("Date");
				c.setLabel("更新时间");
				c.setJdbcType("DATE");
				this.columns.add(c);
			}
			if(this.hasCreateTime == false) {
				ColumnInfo c = new ColumnInfo();
				c.setName("createTime");
				c.setComment("创建时间");
				c.setFieldName("createTime");
				c.setJavaFullType("java.util.Date");
				c.setJavaType("Date");
				c.setLabel("创建时间");
				c.setJdbcType("DATE");
				this.columns.add(c);
			}
			if(importDate == false) this.addImport("java.util.Date");
		}
	}
	
	public List<ColumnInfo> getNotKeyCoulmnInfo(){
		List<ColumnInfo> list = new ArrayList<ColumnInfo>();
		if(this.columns != null)
		for (ColumnInfo columnInfo : columns) {
			if(columnInfo.isId() == false) 
			list.add(columnInfo);
		}
		return list;
	}
	
	public List<ColumnInfo> getKeyCoulmnInfo(){
		List<ColumnInfo> list = new ArrayList<ColumnInfo>();
		if(this.columns != null)
		for (ColumnInfo columnInfo : columns) {
			if(columnInfo.isId() == true) 
			list.add(columnInfo);
		}
		return list;
	}
}
