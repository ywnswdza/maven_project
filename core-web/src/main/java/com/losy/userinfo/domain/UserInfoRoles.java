package com.losy.userinfo.domain;

import com.losy.common.domain.BaseVo;
/**
 * @entity
 * @table t_sys_user_roles
 * @date 2014-05-15 11:27:41
 * @author losy
 */
public class UserInfoRoles extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4374647127429949542L;
	/** id */
	private Integer id;
	/** 角色名称 */
	private Integer roleId;
	/** 用户名称 */
	private Integer userId;

	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	public void setRoleId(Integer roleId){
		this.roleId = roleId;
	}
	public Integer getRoleId() {
		return this.roleId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Integer getUserId() {
		return this.userId;
	}

}