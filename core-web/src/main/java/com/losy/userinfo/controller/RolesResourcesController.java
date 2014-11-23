package com.losy.userinfo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.losy.common.controller.CommonController;
import com.losy.common.utils.IncludeNameConfig;
import com.losy.common.utils.StrUtil;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.RolesResources;
import com.losy.userinfo.service.IResourcesService;
import com.losy.userinfo.service.IRolesResourcesService;
import com.losy.userinfo.service.IRolesService;

/**
 * @controller
 * @table t_sys_roles_resources
 * @date 2014-05-14 11:50:54
 * @author losy
 */
@Controller("rolesResourcesController")
@RequestMapping(value="/system/rolesResources")
public class RolesResourcesController extends CommonController<IRolesResourcesService,RolesResources>  {

	private static final String folder_name = "/userinfo";

	@Resource(name="rolesResourcesService")
	public void setService(IRolesResourcesService service) {
		this.service = service;
	}
	
	@Autowired
	private IRolesService rService;

	@Autowired
	private IResourcesService rcServcie;
	
	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/rolesResourcesEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(RolesResources rolesResources,Map<String, Object> result) {
		
		return folder_name + "/rolesResourcesList";
	} 
	
	/**
	 * 
	 * @param key 角色id
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/editPermission")
	public String editPermission(Integer key,Map<String, Object> result){
		Roles r = rService.getObjectById(key);
		RolesResources rrQ = new RolesResources();
		
		rrQ.setRoleId(key);
		List<RolesResources> hasR = service.getList(rrQ);
		
		Resources qR = new Resources();
		qR.setOrderBy("priority");
		qR.setAppendWhere(" and depth < 5 ");
		List<Resources> rl = rcServcie.getList(qR);
		rl = rcServcie.arrayToTree(rl); 
		
		result.put("roles", r);	
		result.put("hasPer",JSONArray.fromObject(hasR,new IncludeNameConfig("resourceId")).toString());	
		result.put("menus", JSONArray.fromObject(rl,new IncludeNameConfig("id,name,isLeaf,type,children")).toString());
		
		return folder_name + "/permissionEdit";
	}
	
	@RequestMapping(value="/savePer")
	@ResponseBody
	public Object savePermission(String resourceIds,Integer rolesId){
		List<RolesResources> rrsList = new ArrayList<RolesResources>();
		RolesResources save = null;
		for (String rsId : resourceIds.split(",")) {
			save = new RolesResources();
			save.setResourceId(Integer.parseInt(rsId));
			save.setRoleId(rolesId);
			rrsList.add(save);
		}
		this.service.insertBatchByRolesId(rrsList,rolesId);
		return "{status:'success'}";
	}
	
	@RequestMapping(value="/showAccessSetting")
	public String showAccessSetting(Integer key,Map<String, Object> result){
		result.put("res", rcServcie.getObjectById(key));
		
		Roles rQ = new Roles();
		rQ.setIsEnabled(true);
		//rQ.setAppendWhere("");
		List<Roles> roleList = rService.getList(rQ);
		
		String sql = "SELECT sr.`roleFlag` ,sr.`roleName`,sr.roleId FROM `t_sys_roles` sr";
		sql += " JOIN `t_sys_roles_resources` srr ON(sr.`roleId` = srr.`roleId`)";
		sql += "WHERE srr.`resourceId` = " + key;
		
		List<Map<String,Object>> hasRole = this.service.selectListBySql(sql);
		
		
		result.put("roleList", JSONArray.fromObject(roleList,new IncludeNameConfig("roleId,roleName")));
		result.put("hasRoleList", JSONArray.fromObject(hasRole));
		return "/menu/accessSetting";
	}
	
	@RequestMapping(value="/updateRRMapping")
	@ResponseBody
	public String updateRRMapping(Integer key,String roleIds){
		if(key == null || key < 0) return "资源为空!!!";
		List<RolesResources> urList = new ArrayList<RolesResources>();
		if(!StrUtil.isNullOrEmpty(roleIds)) {
			RolesResources rr = null;
			for (String roleId : roleIds.split(",")) {
				rr = new RolesResources();
				rr.setResourceId(key);
				rr.setRoleId(Integer.parseInt(roleId));
				urList.add(rr);
			}
		}
		this.service.updateRRMapping(key,urList);
		//if(String)
		return "保存成功";
	}
}
