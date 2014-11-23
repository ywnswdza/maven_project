package code.entity.common;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.JdbcType;
import com.losy.codegen.annotation.Table;

@Table(name="t_common_tree")
public class CommonTree {

	@Column(id=true)
	public Integer id;
	
	@Column(label="父节点",jdbcType=JdbcType.INTEGER)
	public Integer pId;
	
	@Column(label="层级",jdbcType=JdbcType.TINYINT,length=4)
	public Integer depth;
	
	@Column(label="节点名称",length=30,notnull=true)
	public String nodeText;
	
	@Column(label="种类",jdbcType=JdbcType.INTEGER)
	public Integer kindId;
	
	@Column(label="是否有效",jdbcType=JdbcType.BOOLEAN)
	public Boolean isValid;
	
	@Column(label="是否子节点",jdbcType=JdbcType.BOOLEAN)
	public Boolean isLeaf;
	
	@Column(label="描述",name="`desc`")
	public String desc;
	
	@Column(label="资源地址",length=150)
	public String links;
	
	@Column(label="优先级",jdbcType=JdbcType.INTEGER)
	public Integer priority;
	
	@Column(label="类型",jdbcType=JdbcType.INTEGER,comment="1:url,2:method")
	public Integer rType;
	
}
