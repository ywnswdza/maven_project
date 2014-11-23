<%@page import="com.losy.common.utils.SpringContextListener"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//String ufPath = SpringContextListener.getContextProValue("uploadBasePath",basePath + "/uf/");
	
	String ufPath = SpringContextListener.getContextProValue("cdpBasePath", "http://m.xxwan.com/admin_new/");
	String ufbPath = SpringContextListener.getContextProValue("uploadBasePath","");
	String bbsDownLoadFilePath = SpringContextListener.getContextProValue("bbscdnpath","http://m.xxwan.com");;
%>

<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<c:set var="mainframe" value="http://localhost:8080/losy"></c:set>

<c:set var="ufPath" value="<%= ufPath %>"></c:set>
<c:set var="ufbPath" value="<%= ufbPath %>"></c:set>

<c:set var="bbsDownLoadFilePath" value="<%=bbsDownLoadFilePath %>"/>
