package com.losy.userinfo.domain;

import java.util.Date;

import com.losy.common.domain.BaseVo;
/**
 * @entity
 * @table t_sys_roles
 * @date 2014-05-14 11:20:20
 * @author losy
 */
public class Roles extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 528608663063207665L;
	
	public static final String SUPER_ROLE_FLAG  = "su";
	
	/** roleId */
	private Integer roleId;
	/** 角色名称 */
	private String roleName;
	/** 角色描述 */
	private String roleDesc;
	/** 是否有效 */
	private Boolean isEnabled;
	
	/** 标识  全表唯一**/
	private String roleFlag;
	
	/** 更新时间 */
	private Date updateTime;
	/**判断更新时间，大于或等于 */
	private Date	gtUpdateTime;
	/**判断更新时间，小于或等于 */
	private Date	ltUpdateTime;
	/** 创建时间 */
	private Date createTime;
	/**判断创建时间，大于或等于 */
	private Date	gtCreateTime;
	/**判断创建时间，小于或等于 */
	private Date	ltCreateTime;

	public void setRoleId(Integer roleId){
		this.roleId = roleId;
	}
	public Integer getRoleId() {
		return this.roleId;
	}
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	public String getRoleName() {
		return this.roleName;
	}
	public void setRoleDesc(String roleDesc){
		this.roleDesc = roleDesc;
	}
	public String getRoleDesc() {
		return this.roleDesc;
	}
	public void setIsEnabled(Boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	public Boolean getIsEnabled() {
		return this.isEnabled;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setGtUpdateTime(Date gtUpdateTime){
		this.gtUpdateTime = gtUpdateTime;
	}
	public Date getGtUpdateTime() {
		return this.gtUpdateTime;
	}
	public void setLtUpdateTime(Date ltUpdateTime){
		this.ltUpdateTime = ltUpdateTime;
	}
	public Date getLtUpdateTime() {
		return this.ltUpdateTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setGtCreateTime(Date gtCreateTime){
		this.gtCreateTime = gtCreateTime;
	}
	public Date getGtCreateTime() {
		return this.gtCreateTime;
	}
	public void setLtCreateTime(Date ltCreateTime){
		this.ltCreateTime = ltCreateTime;
	}
	public Date getLtCreateTime() {
		return this.ltCreateTime;
	}
	public String getRoleFlag() {
		return roleFlag;
	}
	public void setRoleFlag(String roleFlag) {
		this.roleFlag = roleFlag;
	}
	
}