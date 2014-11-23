package code.entity.security;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_sys_roles_resources")
public class RolesResources {

	@Column(id=true)
	public Integer id;
	
	@Column(label="角色",jdbcType=JdbcType.INTEGER)
	public Integer roleId;
	
	@Column(label="资源",jdbcType=JdbcType.INTEGER)
	public Integer resourceId;
	
}
