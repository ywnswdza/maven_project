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
import com.losy.userinfo.domain.Roles;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.domain.UserInfoRoles;
import com.losy.userinfo.service.IRolesService;
import com.losy.userinfo.service.IUserInfoRolesService;
import com.losy.userinfo.service.IUserInfoService;

/**
 * @controller
 * @table t_sys_user_roles
 * @date 2014-05-15 11:27:41
 * @author losy
 */
@Controller("userInfoRolesController")
@RequestMapping(value="/system/userInfoRoles")
public class UserInfoRolesController extends CommonController<IUserInfoRolesService,UserInfoRoles>  {

	private static final String folder_name = "/userinfo";

	@Resource(name="userInfoRolesService")
	public void setService(IUserInfoRolesService service) {
		this.service = service;
	}
	
	@Autowired
	private IUserInfoService uService;
	
	@Autowired
	private IRolesService rService;

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/userInfoRolesEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(UserInfoRoles userInfoRoles,Map<String, Object> result) {
		
		return folder_name + "/userInfoRolesList";
	}

	@Override
	public String edit(Integer userId, Map<String, Object> result) {
		UserInfo user = uService.getObjectById(userId);
		
		Roles rQ = new Roles();
		rQ.setIsEnabled(true);
		//rQ.setAppendWhere("");
		List<Roles> roleList = rService.getList(rQ);
		
		StringBuffer qrole = new StringBuffer("SELECT r.roleId,r.roleName FROM t_sys_roles r ");
		qrole.append("JOIN t_sys_user_roles  ur ON(r.roleId = ur.roleId) ")
		.append("WHERE ur.userId = ").append(userId);
		List<Map<String,Object>> hasRole = this.service.selectListBySql(qrole.toString());
		
		result.put("user", user);
		result.put("roleList", JSONArray.fromObject(roleList,new IncludeNameConfig("roleId,roleName")));
		result.put("hasRoleList", JSONArray.fromObject(hasRole));
		return editHandler(result);
		//return super.edit(id, result);
	} 
	
	@RequestMapping(value="/updateURMapping")
	@ResponseBody
	public Object updateURMapping(Integer userId,String roleIds){
		//log.info(userId + " \n " + roleIds);
		if(userId == null || userId < 0) return "用户为空!!!";
		List<UserInfoRoles> urList = new ArrayList<UserInfoRoles>();
		if(!StrUtil.isNullOrEmpty(roleIds)) {
			UserInfoRoles userInfoRoles = null;
			for (String roleId : roleIds.split(",")) {
				userInfoRoles = new UserInfoRoles();
				userInfoRoles.setUserId(userId);
				userInfoRoles.setRoleId(Integer.parseInt(roleId));
				urList.add(userInfoRoles);
			}
		}
		this.service.updateURMapping(userId,urList);
		//if(String)
		return "保存成功";
	}
	
	@RequestMapping(value="/updateLinkUser")
	@ResponseBody
	public Object updateLinkUser(Integer roleId,String userIds){
		//log.info(userId + " \n " + roleIds);
		if(roleId == null || roleId < 0) return "用户为空!!!";
		List<UserInfoRoles> urList = new ArrayList<UserInfoRoles>();
		if(!StrUtil.isNullOrEmpty(userIds)) {
			UserInfoRoles userInfoRoles = null;
			for (String userId : userIds.split(",")) {
				userInfoRoles = new UserInfoRoles();
				userInfoRoles.setRoleId(roleId);
				userInfoRoles.setUserId(Integer.parseInt(userId));
				urList.add(userInfoRoles);
			}
		}
		this.service.updateLinkUser(roleId,urList);
		//if(String)
		return "保存成功";
	}
	
	@RequestMapping(value="/linkUsers")
	public String linkUsers(Integer roleId, Map<String, Object> result){
		Roles role = rService.getObjectById(roleId);
		result.put("role", role);
		List<Map<String,Object>> userList = service.selectListBySql("SELECT userId,username FROM t_sys_userinfo WHERE isEnabled = 1");
		List<Map<String,Object>> linkedUsers = service.selectListBySql("SELECT u.userId,u.username FROM t_sys_userinfo u , t_sys_user_roles ur WHERE  ur.userId = u.userId AND u.isEnabled = 1 AND ur.roleId = " + roleId);
		result.put("userList", JSONArray.fromObject(userList).toString());
		result.put("linkedUsers", JSONArray.fromObject(linkedUsers).toString());
		return folder_name + "/linkUsers";
	}
	
}
