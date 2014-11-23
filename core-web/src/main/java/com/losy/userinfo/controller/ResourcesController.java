package com.losy.userinfo.controller;

import javax.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.losy.common.controller.CommonController;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.service.IResourcesService;

@Controller("resourcesController")
@RequestMapping(value="/system/resources")
public class ResourcesController extends CommonController<IResourcesService,Resources>  {

	private static final String folder_name = "/menu";

	@Resource(name="resourcesService")
	public void setService(IResourcesService service) {
		this.service = service;
	}

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/resourcesEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(Resources resources,Map<String, Object> result) {
		
		return folder_name + "/resourcesList";
	} 
	
	
	@Override
	protected void beforeSave(Resources vo) {
		//DataSourceContextHolder.setDataSourceType(DataSourceType.test);
	}
	
	@Override
	protected void beforeListData(Resources vo) {
		vo.setOrderBy("priority");
	}

	@RequestMapping(value="/loadChildByPid")
	@ResponseBody
	public Object loadChildByPid(Resources vo){
		if(vo.getParentId() == null) vo.setParentId(0);
		vo.setOrderBy("priority");
		return service.getList(vo);
	}
	
	@RequestMapping(value="/editByTree")
	public String editByTree(Resources vo,Map<String,Object> result) {
		if(vo.getId() == null) {
			vo.setPriority(service.getLastPriorityByPid(vo.getParentId()));
		}
		result.put("vo", vo);
		return this.editHandler(result);
	}
	
	@RequestMapping(value="/deleteById")
	@ResponseBody
	public Object deleteById(Resources vo){
		service.deleteByVo(vo);
		return null;
	}
	
	@RequestMapping(value="/moveMenu")
	@ResponseBody
	public Object moveMenu(Integer preId,Boolean preIsLeaf,String drapNodeIds,Integer dropNodeId,String drapNodePriority){
		service.moveMenu(preId,preIsLeaf,drapNodeIds,dropNodeId,drapNodePriority);
		return null;
	}
}
