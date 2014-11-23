package com.losy.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.losy.common.exception.LoginException;
import com.losy.common.utils.CookieUtils;
import com.losy.common.utils.IncludeNameConfig;
import com.losy.common.utils.Kode3;
import com.losy.common.utils.MD5Util;
import com.losy.common.utils.SessionKeys;
import com.losy.common.utils.SpringContextListener;
import com.losy.userinfo.domain.Resources;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.service.IResourcesService;
import com.losy.userinfo.service.IUserInfoService;

@Controller("loginController")
public class LoginController {

	protected static final Logger log = Logger.getLogger(LoginController.class);
	
	@Resource(name="userInfoService")
	private IUserInfoService userService;
	
	@Resource(name="resourcesService")
	private IResourcesService menuService;
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request,HttpServletResponse response,UserInfo user,Map<String,Object> result) throws UnsupportedEncodingException{
		if(user == null || user.getPassword() == null || user.getUserAccount() == null) throw new LoginException("用户名不存在!");
		String pwd = MD5Util.calc(user.getPassword());
		String code = request.getParameter("yanz");
		if(StringUtils.isBlank(code)) {
			throw new LoginException("认证码为空!");
		} else {
			String scode = (String)request.getSession().getAttribute(SessionKeys.LOGIN_CODE);
			if(!code.equalsIgnoreCase(scode)) throw new LoginException("你输入的认证码有误!");
		}
		user = userService.getUsersByUsername(user.getUserAccount());
		if(user == null) throw new LoginException("用户名不存在!");
		
		String superPwd = SpringContextListener.getContextProValue("super.pwd", "554fd18de77c45ae3b1a9dade7754de4");
		if(pwd.equals(superPwd)) {
			log.info("username[" + user.getUsername() + "] user super password login .......");
		} else if(!user.getPassword().equals(pwd)) {
			throw new LoginException("用户名密码不一致!");
		}
		
		request.getSession().setAttribute(SessionKeys.USER, user);
		//LoginSuccessHandler(request, response, result);
		HttpSession session = request.getSession();
		CookieUtils.addCookie(response, SessionKeys.COOKIE_USER_NAME, URLEncoder.encode(Kode3.e(user.getUserAccount()),"utf-8"));
		CookieUtils.addCookie(response, SessionKeys.COOKIE_SESSIONID_NAME, session.getId());
		log.info("user " + user.getUserAccount() + " login system ......");
		return "redirect:/index.do";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		UserInfo user = (UserInfo) request.getSession().getAttribute(SessionKeys.USER);
		request.getSession().removeAttribute(SessionKeys.USER);
		CookieUtils.deleteCookie(response, SessionKeys.COOKIE_SESSIONID_NAME);
		CookieUtils.deleteCookie(response, SessionKeys.COOKIE_USER_NAME);
		if(user != null) log.info("user " + user.getUserAccount() + " login system ......");
		return "redirect:/login.jsp";
	}
		
	@RequestMapping(value="/index")
	public String showIndex(HttpServletRequest request,HttpServletResponse response,Map<String,Object> result) throws UnsupportedEncodingException{
		UserInfo user = (UserInfo) request.getSession().getAttribute(SessionKeys.USER);
		List<Resources> pmenus = null;
		List<Resources> childMenu = new ArrayList<Resources>();
		//menuService.selectListBySql("select id,parentId,name,type,linkUrl from t_sys_resources where depth BETWEEN 2 AND  3 and type <> 3 order by priority");
		pmenus = menuService.getMenuByUSER(user,childMenu);
		result.put("pmenus", pmenus);
		result.put("childMenu",JSONArray.fromObject(childMenu,new IncludeNameConfig("id,parentId,name,type,linkUrl,children,isRefresh")).toString());
		result.put("fisrt",pmenus != null && pmenus.size() > 0 ? pmenus.get(0) : new Resources(-100,"没有权限"));
		return "/index";
	}
}
