package com.losy.springmvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.losy.common.aop.PermissionMappingManager;
import com.losy.common.controller.AttachmentController;
import com.losy.common.exception.PermissionException;
import com.losy.common.utils.SessionKeys;
import com.losy.userinfo.domain.UserInfo;
import com.losy.userinfo.service.IRolesService;
import com.losy.userinfo.service.IUserInfoService;


public class AccessPermissionInterceptor extends HandlerInterceptorAdapter{

	private static Logger log = Logger.getLogger(AccessPermissionInterceptor.class);
	
	private List<String> excludedUrls;
	
	@Autowired
	private PermissionMappingManager resouceRole;
	
	@Autowired
	private IUserInfoService uService;
	
	@Autowired
	private IRolesService rService;
	/**
	 * 拦截器的前端，执行控制器之前所要处理的方法，通常用于权限控制、日志，其中，Object handler表示下一个拦截器；
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(SessionKeys.USER);
		HandlerMethod hm = (HandlerMethod)handler;
		String uri = request.getRequestURI();
		/** 上传文件，需要特处理，必传  uploadUser.userId 参数**/
		if(hm.getBean().getClass().getName().equals(AttachmentController.class.getName()) && hm.getMethod().getName().equals("uploads")) {
			String userId = request.getParameter("uploadUser.userId");
			if(StringUtils.isBlank(userId)) return false;
			user = uService.getObjectById(userId);
			if(user == null) return false;
			user.setRoles(rService.getListByUser(user));
		} 
		String ctx = request.getContextPath();
		uri = uri.substring(uri.indexOf(ctx) + ctx.length());
		String curUrl = uri.substring(0,uri.lastIndexOf("."));
		for(String ec : excludedUrls) {
			if(ec.equals(curUrl)) return true;
		}
		
		if(user == null) {
			response.sendRedirect(request.getContextPath() +  "/login.jsp");
			return false;
		}
		log.info("user [" + user.getUserAccount() + "] access " + hm.getBean().getClass().getSimpleName() + "." + hm.getMethod().getName()+ "[" + uri + "]");
		//log.info("current user account[" + user.getUserAccount() + "] name[" + user.getUsername() + "] isSuperUser ? " + user.getIsSupperUser());
		if(!resouceRole.hasPermission(uri,user)) throw new PermissionException("你无权访问当前页面！请与管理员取得联系!");
		return super.preHandle(request, response, handler);
	}
	
	
	/**
	 * 视图已处理完后执行的方法，通常用于释放资源；
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	/**
	 * 控制器的方法已经执行完毕，转换成视图之前的处理；
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView != null)log.info("forward jsp : " +  modelAndView.getViewName() + ".jsp");
		//super.postHandle(request, response, handler, modelAndView);
	}


	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}
	
	

}
