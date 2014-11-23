package code.entity;

import com.losy.codegen.annotation.Column;
import com.losy.codegen.annotation.Table;

@Table(name="t_wf_workflow")
public class WorkFlow {

	@Column(id=true)
	public Integer id;
	
	@Column(label="流程名称",length=100,notnull=true)
	public String flowName;
	
	@Column(label="创建人",length=30,notnull=true)
	public String createName;
	
	@Column(label="描述",length=999)
	public String descr;
}
