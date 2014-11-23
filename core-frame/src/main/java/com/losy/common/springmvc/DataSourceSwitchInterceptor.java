package com.losy.common.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.losy.common.multidatasource.DataSourceContextHolder;
import com.losy.common.multidatasource.DataSourceType;



public class DataSourceSwitchInterceptor  extends HandlerInterceptorAdapter {
	
	protected static final Logger log = Logger.getLogger(DataSourceSwitchInterceptor.class);

/*	public DataSourceSwitchInterceptor() {
		log.info("DataSourceSwitch >>>>>>>>>>>>>>>>>>>>>>>> init ");
	}*/
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod hm = (HandlerMethod)handler;
		if(hm.getBean().getClass().getName().equals(""))
			DataSourceContextHolder.setDataSourceType(DataSourceType.pay);
		else 
			DataSourceContextHolder.setDataSourceType(DataSourceType.defaultD);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		DataSourceContextHolder.setDataSourceType(DataSourceType.defaultD);
		super.afterCompletion(request, response, handler, ex);
	}
}
