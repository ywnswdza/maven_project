package com.losy.codegen.annotation;

public enum JdbcType {
	BIT("BIT"),FLOAT("FLOAT"),CHAR("CHAR"),TIMESTAMP("TIMESTAMP"),OTHER("OTHER"),UNDEFINED("UNDEFINED"),
	TINYINT("TINYINT"),REAL("REAL"),VARCHAR("VARCHAR"),BINARY("BINARY"),BLOB("BLOB"),NVARCHAR("NVARCHAR"),
	SMALLINT("SMALLINT"),DOUBLE("DOUBLE"),LONGVARCHAR("LONGVARCHAR"),    VARBINARY("VARBINARY"),CLOB("CLOB"),NCHAR("NCHAR"),
	INTEGER("INTEGER"),NUMERIC("NUMERIC"),DATE("DATE"),LONGVARBINARY("LONGVARBINARY"),BOOLEAN("BOOLEAN"), NCLOB("NCLOB"),
	BIGINT("BIGINT"), DECIMAL("DECIMAL"),TIME("TIME"),NULL("NULL"),CURSOR("CURSOR");
	
	private String value;
	
	private JdbcType(String value) {
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}