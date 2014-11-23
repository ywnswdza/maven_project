package com.losy.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static Cookie addCookie(HttpServletResponse response,String name,String value) {
		Cookie c = new Cookie(name, value);
		c.setPath("/");
		//c.setDomain(".xxwan.com");
		c.setMaxAge(-1);
		response.addCookie(c);
		return c;
	}
	
	public static Cookie getCookie(HttpServletRequest request,String name) {
		Cookie[] cok = request.getCookies();
		Cookie c = null;
		if(cok != null) 
		for (Cookie cookie : cok) {
			if(cookie.getName().equals(name)) {
				c = cookie;
				break;
			}
		}
		return c;
	}
	
	public static void deleteCookie(HttpServletResponse response, String name){
		Cookie c = new Cookie(name, "");
		c.setPath("/");
		//c.setDomain(".xxwan.com");
		c.setMaxAge(0);
		response.addCookie(c);
	}
}
