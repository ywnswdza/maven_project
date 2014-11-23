package com.losy.userinfo.controller;

import javax.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.losy.common.controller.CommonController;
import com.losy.userinfo.domain.UsersRoles;
import com.losy.userinfo.service.IUsersRolesService;

@Controller("usersRolesController")
@RequestMapping(value="/system/usersRoles")
public class UsersRolesController extends CommonController<IUsersRolesService,UsersRoles>  {

	private static final String folder_name = "/userinfo";

	@Resource(name="usersRolesService")
	public void setService(IUsersRolesService service) {
		this.service = service;
	}

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/usersRolesEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(UsersRoles usersRoles,Map<String, Object> result) {
		
		return folder_name + "/usersRolesList";
	} 
}
