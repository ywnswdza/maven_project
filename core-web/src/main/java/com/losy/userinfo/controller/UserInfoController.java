package com.losy.userinfo.controller;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.losy.common.controller.CommonController;
import com.losy.common.utils.MD5Util;
import com.losy.common.utils.SessionKeys;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.service.IUserInfoService;

@Controller("userInfoController")
@RequestMapping(value="/system/userInfo")
public class UserInfoController extends CommonController<IUserInfoService,UserInfo>  {

	private static final String folder_name = "/userinfo";

	@Resource(name="userInfoService")
	public void setService(IUserInfoService service) {
		this.service = service;
	}

	/** 子类实现编辑页面所在目录  */
	public String editHandler(Map<String, Object> result) {
		return folder_name + "/userInfoEdit";
	} 
	
	/** 子类实现列表页面所在目录  */
	protected String showListHandler(UserInfo userInfo,Map<String, Object> result) {
		
		return folder_name + "/userInfoList";
	}
	
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Object resetPwd(String ids) {
		JSONObject json = new JSONObject();
		service.resetPwd(ids);
		json.put("status", true);
		return json;
	}
	
	
	@RequestMapping("/modifyPassword")
	@ResponseBody
	public Object modifyPassword(Integer userId,String oldPassword,String password) {
		
		oldPassword = oldPassword == null ? "" : oldPassword;
		password = password == null ? "" : password;
		userId = userId == null ? 0 : userId;
		
		JSONObject json = new JSONObject();
		UserInfo user = (UserInfo)getRequest().getSession().getAttribute(SessionKeys.USER);
		if(user == null || !userId.equals(user.getUserId())) json.put("message", "请重新登录后再试!");
		else if(!user.getPassword().equals(MD5Util.calc(oldPassword))) json.put("message", "原密码不匹配!");
		else if(oldPassword.equals(password)) json.put("message", "原密码和新密码相同!");
		else if("".equals(password)) json.put("message", "新密码为空!");
		else {
			user.setPassword(MD5Util.calc(password));
			service.save(user);
		}
		json.put("status", true);
		return json;
	}
	
	
}
