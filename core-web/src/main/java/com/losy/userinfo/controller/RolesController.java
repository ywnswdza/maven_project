package com.losy.userinfo.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.losy.common.controller.CommonController;
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.service.IRolesService;

/**
 * @controller
 * @table t_sys_roles
 * @date 2014-05-14 11:20:20
 * @author losy
 */
@Controller("rolesController")
@RequestMapping(value="/system/roles")
public class RolesController extends CommonController<IRolesService,Roles>  {

	private static final String folder_name = "/userinfo";

	@Resource(name="rolesService")
	public void setService(IRolesService service) {
		this.service = service;
	}

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/rolesEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(Roles roles,Map<String, Object> result) {
		
		return folder_name + "/rolesList";
	} 
}
