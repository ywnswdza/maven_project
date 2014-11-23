package com.losy.wf.domain;

import com.losy.common.domain.BaseVo;
/**
 * @entity
 * @table t_wf_workflow
 * @date 2014-09-04 10:36:36
 * @author losy
 */
public class WorkFlow extends BaseVo {

	/** id */
	private Integer id;
	/** 流程名称 */
	private String flowName;
	/** 创建人 */
	private String createName;
	/** 描述 */
	private String descr;

	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	public void setFlowName(String flowName){
		this.flowName = flowName;
	}
	public String getFlowName() {
		return this.flowName;
	}
	public void setCreateName(String createName){
		this.createName = createName;
	}
	public String getCreateName() {
		return this.createName;
	}
	public void setDescr(String descr){
		this.descr = descr;
	}
	public String getDescr() {
		return this.descr;
	}

}