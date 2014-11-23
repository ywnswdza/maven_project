package code.entity.security;


import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_sys_roles")
public class Roles {

	@Column(id=true)
	public Integer roleId;

	@Column(label="角色名称",length=20)
	public String roleName;

	@Column(label="角色描述",length=100)
	public String roleDesc;

	@Column(label="是否有效",jdbcType=JdbcType.BOOLEAN)
	public Boolean isEnabled;

}