package com.losy.common.annotation;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public abstract class EntryUtils {

	public static boolean isSingleRowMapper(Class<?> clazz){
		if(String.class == clazz || 
		   Integer.class == clazz ||
		   Double.class == clazz ||
		   Long.class == clazz ||
		   Float.class == clazz
		) return true;
		return false;
	}
	
	public static boolean supportEntryQuery(Class<?> clazz){
		return clazz.isAnnotationPresent(Entry.class);
	}
	
	public static boolean supportSimpleTableInsert(Class<?> clazz){
		return clazz.isAnnotationPresent(Table.class);
	}
	
	public static <T> String getUpdateSqlAndParams(T entry,List<Object> args){
		Class<?> entryClass = entry.getClass();
		String tableName = entryClass.getAnnotation(Table.class).value();
		StringBuffer update = new StringBuffer("update " + tableName + " set ");
		StringBuffer where = new StringBuffer(" where ");
		List<Object> whereObj = new ArrayList<Object>();
		Field[] fs = entryClass.getDeclaredFields();
		boolean hasUpkey = false;
		for(Field f : fs) {
			if(f.isAnnotationPresent(Ignore.class)) continue;
			try {
				f.setAccessible(true);
				Object value = f.get(entry);
				if(value == null) continue;
				String columnName = f.getName();
				if(f.isAnnotationPresent(Column.class)) {
					Column c = f.getAnnotation(Column.class);
					columnName = c.value();
				}
				if(f.isAnnotationPresent(UpKey.class)) {
					if(!hasUpkey) hasUpkey = true;
					where.append(" and ").append(columnName).append(" = ? ");
					whereObj.add(value);
				} else {
					update.append(columnName).append("= ? , ");
					args.add(value);
				}
			} catch (Exception e) {}
		}
		if(hasUpkey == false) {
			throw new IllegalArgumentException("Dangerous operation !!!  value is null on @Upkey Filed  OR At least one @Upkey Annotation on " + entryClass.getName() + " the Field !!!");
		}
		String sql = update.substring(0, update.lastIndexOf(",")) + where;
		sql = sql.replaceAll("where\\s*and", "where");
		args.addAll(whereObj);
		return sql;
	}
	
	public static <T> String getInsertSqlAndParams(T entry,List<Object> args){
		Class<?> entryClass = entry.getClass();
		String tableName = entryClass.getAnnotation(Table.class).value();
		StringBuffer inserts = new StringBuffer("insert into " + tableName + " (");
		StringBuffer values = new StringBuffer(" values (");
		Field[] fs = entryClass.getDeclaredFields();
		for(Field f : fs) {
			if(f.isAnnotationPresent(Ignore.class)) continue;
			try {
				f.setAccessible(true);
				Object value = f.get(entry);
				if(value == null) continue;
				String columnName = f.getName();
				if(f.isAnnotationPresent(Column.class)) {
					Column c = f.getAnnotation(Column.class);
					columnName = c.value();
				}
				inserts.append(columnName).append(",");
				values.append("?,");
				args.add(value);
			} catch (Exception e) {
				
			}
		}
		inserts.setLength(inserts.length() - 1);
		inserts.append(")");
		values.setLength(values.length() - 1);
		values.append(")");
		return inserts.toString() + values.toString();
	}
}
