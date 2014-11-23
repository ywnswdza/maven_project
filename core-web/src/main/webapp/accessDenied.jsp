<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
  <%@include file="/common/su.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    
    <style type="text/css">
	div.error {
    	border: 1px solid green;
	}
    </style>
  </head>
  <body>
  	<div style="width: 80%;margin: auto;margin-top: 100px;padding-left: 20px;" class="error">
	    <h2>抱歉，你无权访问该页面</h2>
	    <h3>提示：</h3>
	   	<ul>
	   		<li>你不在允许的范围里</li>
	   		<li>请取得允可后再试</li>
	   		<li>访问被拒绝</li>
	   	</ul>
   	</div>
  </body>
</html>

