package com.losy.userinfo.domain;

import com.losy.common.domain.BaseVo;

import java.util.Date;

public class RolesAuthorities extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3232427544801712930L;
	/** id */
	private Integer id;
	/** 角色名称 */
	private Integer roleId;
	/** 权限名称 */
	private Integer authId;
	/** 角色权限描述 */
	private String desc;
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
	public void setAuthId(Integer authId){
		this.authId = authId;
	}
	public Integer getAuthId() {
		return this.authId;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	public String getDesc() {
		return this.desc;
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