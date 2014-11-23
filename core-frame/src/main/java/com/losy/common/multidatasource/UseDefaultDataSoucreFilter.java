package com.losy.common.multidatasource;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class UseDefaultDataSoucreFilter implements Filter{

	private static Logger log = Logger.getLogger(UseDefaultDataSoucreFilter.class);
	
	@Override
	public void destroy() {
		log.info("UseDefaultDataSoucreFilter destroy >>>>>>>>>>>>>>>>>>");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.info("UseDefaultDataSoucreFilter doFilter >>>>>>>>>>>>>>>>>>");
		DataSourceContextHolder.setDataSourceType(DataSourceType.defaultD);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("UseDefaultDataSoucreFilter init >>>>>>>>>>>>>>>>>>");
	}

}
