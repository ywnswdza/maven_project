package com.losy.codegen.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.Table;
import com.losy.codegen.domian.ColumnInfo;
import com.losy.codegen.domian.TableInfo;


public class AnnotationParser{

	public List<TableInfo> parse(Class<?>... entitys) {
		List<TableInfo> tableList = new ArrayList<TableInfo>();
		for (Class<?> clazz : entitys) {
			if(clazz.isAnnotationPresent(Table.class)) {
				Table table = clazz.getAnnotation(Table.class);
				TableInfo ti = TableInfo.init(table);
				ti.setEntityName(clazz.getName());
				ti.setEntitySimpleName(clazz.getSimpleName());
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if(field.isAnnotationPresent(Column.class)) {
						Column colunm = field.getAnnotation(Column.class);
						ColumnInfo ci = ColumnInfo.init(colunm);
						ci.setFieldName(field.getName());
						ci.setJavaType(field.getType().getSimpleName());
						ci.setJavaFullType(field.getType().getName());
						ci.validation();
						ti.addImport(field.getType().getName());
						ti.addColumn(ci);
					}
				}
				ti.validation();
				tableList.add(ti);
			} 
		}
		return tableList;
	}

}
