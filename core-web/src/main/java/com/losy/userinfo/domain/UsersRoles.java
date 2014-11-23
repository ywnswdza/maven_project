package com.losy.userinfo.domain;

import com.losy.common.domain.BaseVo;

import java.util.Date;

public class UsersRoles extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735185172877703033L;
	/** id */
	private Integer id;
	/** 角色名称 */
	private Integer roleId;
	/** 用户名称 */
	private Integer userId;
	/** 是否有效 */
	private Boolean isEnabled;
	/** 创建时间 */
	private Date updateTime;
	/**判断时间，大于或等于 */
	private Date	gtUpdateTime;
	/**判断时间，小于或等于 */
	private Date	ltUpdateTime;
	/** 更新时间 */
	private Date createTime;
	/**判断时间，大于或等于 */
	private Date	gtCreateTime;
	/**判断时间，小于或等于 */
	private Date	ltCreateTime;

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

}