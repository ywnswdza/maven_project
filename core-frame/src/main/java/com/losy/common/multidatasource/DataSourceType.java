package com.losy.common.multidatasource;
/**
 * 多数据源，枚举类型
 * @author longyt
 */
public enum DataSourceType {
	/** 默认的数据源 **/
	defaultD("default"),
	/** 订单数据源使用 **/
	pay("pay"),
	/** sdk用户数据源key**/
	sdkUser("sdkuser"),
	test("test"),
	im("im"),
	/** 旧的管理后台数据源，现在新的官网使用旧的查询用 **/
	sdkOld("sdkAdminOld"),
	/**bbs 论坛*/
	bbs("xxwanbbs");
	/**
	 * spring factory中的数据源的 bean id
	 */
	private String value;

	private DataSourceType(String value) {
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
