package com.losy.wf.dao.impl;

import org.springframework.stereotype.Repository;
import com.losy.wf.dao.IWorkFlowDao;
import com.losy.wf.domain.WorkFlow;
import com.losy.common.dao.impl.CommonDaoImpl;

/**
 * @daoimpl
 * @table t_wf_workflow
 * @date 2014-09-04 10:36:36
 * @author losy
 */
@Repository("workFlowDao")
public class WorkFlowDaoImpl extends CommonDaoImpl<WorkFlow> implements IWorkFlowDao{

	private String nameSpace = "com.losy.wf.sql.sqlmapping.WorkFlow";
	
	public String getNameSpace() {
		return nameSpace;
	}

}
