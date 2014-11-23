package code.entity.security;

import java.util.Date;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_auth_authResources")
public class RolesAuthorities {
	
	@Column(id=true)
	public Integer id;
	
	@Column(label="角色名称",jdbcType=JdbcType.INTEGER)
	public Integer roleId;
	
	@Column(label="权限名称",jdbcType=JdbcType.INTEGER)
	public Integer authId;
	
	@Column(label="角色权限描述",length=150,name="`desc`")
	public String desc;

	@Column(label="创建时间",jdbcType=JdbcType.DATE)
	public Date updateTime;
	@Column(label="更新时间",jdbcType=JdbcType.DATE)
	public Date createTime;
}