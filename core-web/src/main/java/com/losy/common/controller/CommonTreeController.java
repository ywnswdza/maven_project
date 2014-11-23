package com.losy.common.controller;

import javax.annotation.Resource;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.losy.common.controller.CommonController;
import com.losy.common.domain.CommonTree;
import com.losy.common.service.ICommonTreeService;

/**
 * @controller
 * @table t_common_tree
 * @date 2014-05-05 15:18:45
 * @author losy
 */
@Controller("commonTreeController")
@RequestMapping(value="/system/commonTree")
public class CommonTreeController extends CommonController<ICommonTreeService,CommonTree>  {

	private static final String folder_name = "/datadict";

	@Resource(name="commonTreeService")
	public void setService(ICommonTreeService service) {
		this.service = service;
	}

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/treeEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(CommonTree commonTree,Map<String, Object> result) {
		
		return folder_name + "/treeList";
	} 
	
	@Override
	protected void beforeListData(CommonTree vo) {
		if(vo.getOrderBy() != null && !"".equals(vo.getOrderBy()))
		vo.setOrderBy("priority");
	}
	
	@RequestMapping(value="/loadChildByPid")
	@ResponseBody
	public Object loadChildByPid(CommonTree vo){
		if(vo.getPId() == null) vo.setPId(0);
		vo.setOrderBy("priority");
		return service.getList(vo);
	}
	
	@RequestMapping(value="/editByTree")
	public String editByTree(CommonTree vo,Map<String,Object> result) {
		if(vo.getId() == null) {
			vo.setPriority(service.getLastPriorityByPid(vo.getPId()));
		}
		result.put("vo", vo);
		return this.editHandler(result);
	}
	
	@RequestMapping(value="/deleteById")
	@ResponseBody
	public Object deleteById(CommonTree vo){
		service.deleteByVo(vo);
		return vo;
	}
	
	@RequestMapping(value="/selectTree")
	public String selectTree(CommonTree tree,Map<String, Object> result){

		return folder_name + "/selectTree";
	}
	
	@RequestMapping(value="/moveTree")
	@ResponseBody
	public Object moveTree(Integer preId,Boolean preIsLeaf,String drapNodeIds,Integer dropNodeId,String drapNodePriority){
		service.moveTree(preId,preIsLeaf,drapNodeIds,dropNodeId,drapNodePriority);
		return null;
	}
}
