package com.losy.userinfo.domain;

import com.losy.common.domain.BaseVo;
/**
 * @entity
 * @table t_sys_roles_resources
 * @date 2014-05-14 11:50:54
 * @author losy
 */
public class RolesResources extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1016955098630884895L;
	/** id */
	private Integer id;
	/** 角色 */
	private Integer roleId;
	/** 资源 */
	private Integer resourceId;


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
	public void setResourceId(Integer resourceId){
		this.resourceId = resourceId;
	}
	public Integer getResourceId() {
		return this.resourceId;
	}

}