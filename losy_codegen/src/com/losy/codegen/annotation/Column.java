package com.losy.codegen.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
	
	public String name() default "";
	public int length() default 255;
	public String comment() default "";
	public boolean id() default false;
	public boolean unique() default false;
	public boolean notnull()default false;
	public boolean required()default false;
	public String label() default "";
	public JdbcType jdbcType() default JdbcType.VARCHAR;
	
}
