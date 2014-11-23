package com.losy.common.springmvc;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.losy.common.exception.LoginException;
import com.losy.common.exception.PermissionException;

/*import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;*/
/**
 * spring mvc 全局异常请求,拦截不到TemplateException,所以又实现了TemplateExceptionHandler
 * @author Long
 *
 */
public class SpringMVCExceptionHandler implements HandlerExceptionResolver/*,TemplateExceptionHandler */ {
	
	private static Logger logger = Logger.getLogger(SpringMVCExceptionHandler.class);
	
	/**
	 * spring mvc异常处理
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Throwable rootT = ex;
		while ( rootT.getCause() != null ) {
			rootT = rootT.getCause();
		}
		if(isAjaxRequest(request)) {
			//ajax 请求 ........
			//String error = getErrorMsg(ex, "\n",handler);
			String error = "来自系统的消息！您没有权限进行当前的操作!";
			response.reset();
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("char-set", "uft-8");
			Map<String,Object> json = new HashMap<String,Object>();
			try {
				if(!(rootT instanceof PermissionException)) {
					logger.error("SpringMVCExceptionHandler " + rootT.getMessage(),ex);
					error = rootT.getMessage();
				}
				json.put("errorMsg", error);
				response.getWriter().print(json.toString());
			} catch (IOException e) {
			}
			return new ModelAndView();
		} else {
			String error = rootT.getMessage() + "<br>" +  getErrorMsg(ex, "<br>",handler);
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("msg",error);
			if(rootT instanceof PermissionException) mv = new ModelAndView("redirect:/accessDenied.jsp");
			else if(rootT instanceof LoginException) {
				mv = new ModelAndView("login");
				mv.addObject("msg",rootT.getMessage());
			} else {
				logger.error("SpringMVCExceptionHandler " + rootT.getMessage(),ex);
			}
			
			return mv;
		}
	}
	
	
	private String getErrorMsg(Exception ex,String enter,Object target) {
		HandlerMethod hm = (HandlerMethod)target;
		String targetN = hm.getBean().getClass().getName();
		String prefix = targetN.substring(0, targetN.indexOf(".",5));
		StringBuffer sb = new StringBuffer();
		sb.append(enter);
		//sb.append(ex.getMessage()).append(enter);
		for (StackTraceElement ele : ex.getStackTrace()) {
			if(ele.getClassName().startsWith(prefix) && !ele.getClassName().startsWith(targetN) ) {
				sb.append("at ").append(ele.getClassName()).append(".").append(ele.getMethodName());
				sb.append("(").append(ele.getFileName()).append(":").append(ele.getLineNumber()).append(")");
				sb.append(enter);
			}
		}
		return sb.toString();
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }

	/**
	 * freemarker异常处理
	 */
	/*public void handleTemplateException(TemplateException ex,
			Environment environment, Writer out) throws TemplateException {
	  try  {  
            out.write( "[Error: "  + ex.getMessage() +  "]" );  
            logger.warn( "[Freemarker Error: "  + ex.getMessage() +  "]" );  
        }  catch  (IOException e) {  
        	logger.warn(e.getMessage());  
             throw new  TemplateException("Failed to print error message. Cause: "  + e, environment);  
        }  
	}*/
	
	
}
