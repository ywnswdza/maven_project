package com.losy.userinfo.domain;


import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.losy.common.domain.BaseVo;

/**
 * t_sys_userinfo
 * @author Administrator
 *
 */
public class UserInfo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 906337322284683739L;
	/** userId */
	private Integer userId;
	/** 登录帐号 */
	private String userAccount;
	/** 用户密码 */
	private String password;
	/** 用户名称 */
	private String username;
	/** 是否有效 */
	private Boolean isEnabled;
	/** 是否超级用户 */
	private Boolean isSupperUser;
	/** 用户描述  */
	private String userDesc;
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
	
	@Transient//帐号有没有被锁
	public boolean isAccountNonLocked = true;

	@Transient//密码可用
	public boolean credentialsNonExpired = true;
	@Transient// 帐号可用？
	public boolean accountNonExpired = true;
	
	@Transient
	private Collection<String> authorities;

	private List<Roles> roles;
	
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserAccount(String userAccount){
		this.userAccount = userAccount;
	}
	public String getUserAccount() {
		return this.userAccount;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername() {
		return this.username;
	}
	public void setIsEnabled(Boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	public Boolean getIsEnabled() {
		return this.isEnabled;
	}
	public void setIsSupperUser(Boolean isSupperUser){
		this.isSupperUser = isSupperUser;
	}
	public Boolean getIsSupperUser() {
		return this.isSupperUser;
	}
	public void setUserDesc(String userDesc){
		this.userDesc = userDesc;
	}
	public String getUserDesc() {
		return this.userDesc;
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
	
	public Collection<String> getAuthorities() {
		return this.authorities;
	}
	
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public void setAuthorities(Collection<String> authorities) {
		this.authorities = Collections.unmodifiableCollection(authorities);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
	public String getRolesString(){
		String rs = this.isSupperUser != null && this.isSupperUser == true ? Roles.SUPER_ROLE_FLAG : "";
		if(roles == null) return rs;
		if(!"".equals(rs)) rs +=",";
		for(Roles rl : roles) {
			rs += rl.getRoleFlag() + ",";
		}
		if(rs.indexOf(",") > 0) rs = rs.substring(0, rs.lastIndexOf(","));
		return rs;
	}
	
}