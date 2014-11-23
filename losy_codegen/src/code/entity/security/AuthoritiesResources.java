package code.entity.security;

import java.util.Date;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_auth_authResources")
public class AuthoritiesResources {
	
	@Column(id=true)
	public Integer id;
	
	@Column(label="权限名称",jdbcType=JdbcType.INTEGER)
	public Integer authId;
	
	@Column(label="资源名称",jdbcType=JdbcType.INTEGER)
	public Integer resourceId;
	
	@Column(label="是否有效",jdbcType=JdbcType.BOOLEAN)
	public Boolean isEnabled; //0禁用，1正常

	@Column(label="所属模块",length=10)
	public String module;
	
	@Column(label="创建时间",jdbcType=JdbcType.DATE)
	public Date updateTime;
	@Column(label="更新时间",jdbcType=JdbcType.DATE)
	public Date createTime;
}
