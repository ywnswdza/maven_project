package com.losy.wf.controller;

import javax.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.losy.common.controller.CommonController;
import com.losy.wf.service.IWorkFlowService;
import com.losy.wf.domain.WorkFlow;

/**
 * @controller
 * @table t_wf_workflow
 * @date 2014-09-04 10:36:36
 * @author losy
 */
@Controller("workFlowController")
@RequestMapping(value="/wf/workFlow")
public class WorkFlowController extends CommonController<IWorkFlowService,WorkFlow>  {

	private static final String folder_name = "/wf";

	@Resource(name="workFlowService")
	public void setService(IWorkFlowService service) {
		this.service = service;
	}

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/workFlowEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(WorkFlow workFlow,Map<String, Object> result) {
		
		return folder_name + "/workFlowList";
	} 
	
	
	
}
