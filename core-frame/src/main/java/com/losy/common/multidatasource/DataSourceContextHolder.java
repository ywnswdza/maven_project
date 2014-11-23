package com.losy.common.multidatasource;


public class DataSourceContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>(); // 线程本地环境

	// 设置数据源类型
	public static void setDataSourceType(DataSourceType dataSourceType) {
		contextHolder.set(dataSourceType.toString());
	}

	// 获取数据源类型 　　
	public static String getDataSourceType() {
		return contextHolder.get();
	}

	// 清除数据源类型
	public static void clearDataSourceType() {
		contextHolder.remove();
	}
}
