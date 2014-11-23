package com.losy.wf.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.losy.common.service.impl.CommonServiceImpl;
import com.losy.wf.dao.IWorkFlowDao;
import com.losy.wf.domain.WorkFlow;
import com.losy.wf.service.IWorkFlowService;


/**
 * @serviceimpl
 * @table t_wf_workflow
 * @date 2014-09-04 10:36:36
 * @author losy
 */
@Service("workFlowService")
public class WorkFlowServiceImpl extends CommonServiceImpl<IWorkFlowDao,WorkFlow> implements IWorkFlowService {

	
	@Resource(name="workFlowDao")
	public void setDao(IWorkFlowDao dao){
		this.dao = dao;
	}

	/**
	 * 根据主键执行insert或update
	 */
	public WorkFlow save(WorkFlow vo) {
		if(vo.getId() == null) {
			return this.insert(vo);
		} else {
			return this.update(vo);
		}
	}
	
}
