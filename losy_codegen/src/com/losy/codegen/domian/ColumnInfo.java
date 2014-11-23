package com.losy.codegen.domian;


import com.losy.codegen.annotation.Column;


public class ColumnInfo {

	private String name;
	
	private int length;
	
	private String comment;
	
	private boolean id;
	
	private boolean unique;
	
	private boolean notnull;
	
	private String label;
	
	private String jdbcType;
	
	private String javaType;
	
	private String javaFullType;
	
	private String fieldName;
	
	private String defalut;
	
	private boolean required;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isId() {
		return id;
	}
	public void setId(boolean id) {
		this.id = id;
	}
	public boolean isUnique() {
		return unique;
	}
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	public boolean isNotnull() {
		return notnull;
	}
	public void setNotnull(boolean notnull) {
		this.notnull = notnull;
	}
	public String getLabel() {
		return label;
	}
	
	public String getNotNull(){
		return String.valueOf(notnull);
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	public static ColumnInfo init(Column t) {
		ColumnInfo c = new ColumnInfo();
		if(t != null) {
			c.setComment(t.comment());
			c.setId(t.id());
			c.setJdbcType(t.jdbcType().toString());
			c.setLabel(t.label());
			c.setLength(t.length());
			c.setName(t.name());
			c.setNotnull(t.notnull());
			c.setUnique(t.unique());
			c.setUnique(t.unique());
		}
		return c;
	}
	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", length=" + length + ", comment="
				+ comment + ", id=" + id + ", unique=" + unique + ", notnull="
				+ notnull + ", lebel=" + label + ", jdbcType=" + jdbcType
				+ ", javaType=" + javaType + ", fieldName=" + fieldName + "]";
	}
	public void validation() {
		if(this.name == null || "".equals(this.name)) this.name = this.fieldName;
		if(this.label == null || "".equals(this.label)) this.label = this.fieldName;
		if(this.comment == null || "".equals(this.comment)) this.comment = this.label;
		if(
			"Integer".equals(this.javaType) 
			|| "int".equals(javaType) 
			|| "double".equals(javaType.toLowerCase()) 
			|| "float".equals(javaType.toLowerCase()) 
			) {
			if(this.length > 11 ) this.length = 11;
		}
	}
	public String getDefalut() {
		return defalut;
	}
	public void setDefalut(String defalut) {
		this.defalut = defalut;
	}
	public String getJavaFullType() {
		return javaFullType;
	}
	public void setJavaFullType(String javaFullType) {
		this.javaFullType = javaFullType;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequiredStr() {
		return String.valueOf(required);
	}
}
