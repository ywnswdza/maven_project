package code.entity.security;

import java.util.Date;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_sys_resources")
public class Resources {
	
	/*resource_id Vchar(32) 资源id PK 
	2 resource_name Vchar(100) 资源名称 
	3 resource_type Vchar(40) 资源类型 url、method 
	4 priority int 资源优先权 即排序 
	5 resource_link Vchar(200) 资源链接  
	6 resource_desc Vchar(100) 资源描述  
	7 Enabled Int 是否被禁用 0禁用1正常 
	8 isSys Int 是否是超级权限 0非1是*/
	
	@Column(id=true)
	public Integer id;
	
	@Column(label="资源名称",length=15)
	public String name;
	
	@Column(label="资源类型",comment="1:顶级菜单,2:url,3:method",jdbcType=JdbcType.INTEGER)
	public Integer type;
	
	@Column(label="资源链接",length=150)
	public String linkUrl;
	
	@Column(label="优先级",jdbcType=JdbcType.INTEGER)
	public Integer priority;
	
	@Column(label="是否有效",jdbcType=JdbcType.BOOLEAN)
	public Boolean isEnabled; //0禁用，1正常
	
	@Column(label="父级目录",jdbcType=JdbcType.INTEGER)
	public Integer parentId;
	
	@Column(label="描述",length=150,name="`desc`")
	public String desc;
	
	@Column(label="创建时间",jdbcType=JdbcType.DATE)
	public Date updateTime;
	@Column(label="更新时间",jdbcType=JdbcType.DATE)
	public Date createTime;
}
