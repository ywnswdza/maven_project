package code.entity.security;

import java.util.Date;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_auth_authorities")
public class Authorities {

	@Column(id=true)
	public Integer id;
	
	@Column(label="权限名称",length=20)
	public String authName;
	
	@Column(label="权限标识",unique=true,length=10)
	public String authFlag;
	
	@Column(label="权限描述",length=100)
	public String authDesc;
	
	@Column(label="是否有效",jdbcType=JdbcType.BOOLEAN)
	public Boolean isEnabled;
	
	@Column(label="所属模块",length=10)
	public String module;
	
	@Column(label="创建时间",jdbcType=JdbcType.DATE)
	public Date updateTime;
	@Column(label="更新时间",jdbcType=JdbcType.DATE)
	public Date createTime;
}
