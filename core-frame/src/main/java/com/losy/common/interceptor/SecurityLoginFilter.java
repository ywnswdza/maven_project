package com.losy.common.interceptor;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.losy.common.utils.ConstantUtil;
import com.losy.common.utils.CookieUtils;
import com.losy.common.utils.HttpUtils;
import com.losy.common.utils.Kode3;
import com.losy.common.utils.SessionKeys;



/**
 * 单点登录拦截器，用名其它项目引用
 * @author losy
 *
 */
public class SecurityLoginFilter implements Filter{


	Logger log = Logger.getLogger(SecurityLoginFilter.class);
	
	private String loginUrl;
	private String mainframeName = "";
	private String hasParamsUrl = "";
	private String accessDeniedUrl = "";
	
	@Override
	public void destroy() {
//		log.info("AuthorityFilter  destroy........");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		try {
			Cookie sessionCookie = CookieUtils.getCookie(request,SessionKeys.COOKIE_SESSIONID_NAME);
			if(sessionCookie == null) {
				session.removeAttribute(SessionKeys.USER);
				response.sendRedirect(this.loginUrl);
				return;
			}
			String sessionId = sessionCookie.getValue();
			Cookie cname = CookieUtils.getCookie(request,SessionKeys.COOKIE_USER_NAME);
			if(cname == null) {
				response.sendRedirect(this.loginUrl);
				return;				
			}
			String username = Kode3.d(URLDecoder.decode(cname.getValue(),"utf-8"));
			Map<String,String> params = new HashMap<String,String>();
			params.put("url",request.getRequestURI());
			params.put("username", username);
			String hasParam = HttpUtils.post(this.hasParamsUrl + ";jsessionid=" +sessionId, params);
			if(ConstantUtil.PERMISSION_SUCCESS.equals(hasParam)) chain.doFilter(request, response);
			else if (ConstantUtil.NO_PERMISSION.equals(hasParam)) response.sendRedirect(this.accessDeniedUrl);
			else response.sendRedirect(this.loginUrl);
		} catch (Exception e) {
			log.error("AuthorityFilter doFilter error !",e);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
//		log.info("AuthorityFilter  init........");
		this.loginUrl = filterConfig.getInitParameter("loginUrl");
		this.mainframeName = filterConfig.getInitParameter("mainframeName");
		this.hasParamsUrl = filterConfig.getInitParameter("hasParamsUrl");
		this.accessDeniedUrl = filterConfig.getInitParameter("accessDeniedUrl");
		if(StringUtils.isBlank(mainframeName)) this.mainframeName = "core";
		if(StringUtils.isBlank(loginUrl)) this.loginUrl = "/" +  mainframeName + "/login.jsp";
		if(StringUtils.isBlank(this.hasParamsUrl)) this.hasParamsUrl = "http://localhost:8080/" + this.mainframeName + "/sessionValid/hasPermission.do";
		if(StringUtils.isBlank(this.accessDeniedUrl)) this.accessDeniedUrl = "http://localhost:8080/" + this.mainframeName + "/accessDenied.jsp";
	}

}
