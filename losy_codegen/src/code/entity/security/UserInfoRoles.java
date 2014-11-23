package code.entity.security;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_sys_user_roles")
public class UserInfoRoles {
	
	@Column(id=true)
	public Integer id;
	
	@Column(label="角色名称",length=20,jdbcType=JdbcType.INTEGER)
	public Integer roleId;
	
	@Column(label="用户名称",length=20,jdbcType=JdbcType.INTEGER)
	public Integer userId;

}