package com.losy.common.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.losy.common.aop.PermissionMappingManager;
import com.losy.common.utils.ConstantUtil;
import com.losy.common.utils.SessionKeys;
import com.losy.userinfo.domain.UserInfo;


/**
 * 
 * @author Administrator
 *
 */
@Controller("sessionValidationController")
@RequestMapping(value="/sessionValid")
public class SessionValidationController {

	@Autowired
	private PermissionMappingManager pmm ;
	
	
	@RequestMapping(value="/hasPermission")
	public void hasPermission(HttpServletRequest request,HttpServletResponse response,String username,String url) throws IOException{
		UserInfo user = (UserInfo)request.getSession().getAttribute(SessionKeys.USER);
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(getPorcessMsg(user,username,url));
	}
	
	private String getPorcessMsg(UserInfo user,String username,String url) {
		if(user == null) return ConstantUtil.NOT_LOGIN;
		if(!user.getUserAccount().equals(username)) return ConstantUtil.LOGIN_USER_NOT_EQUALS;
		if(!pmm.hasPermission(url, user)) return ConstantUtil.NO_PERMISSION;
		return ConstantUtil.PERMISSION_SUCCESS;
	}



	@RequestMapping(value="/loginUserInfo")
	@ResponseBody
	public Object hasPermission(HttpServletRequest request){
		return request.getSession().getAttribute(SessionKeys.USER);
	}
}
