<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Access Denied</title>
    <style type="text/css">
	div.error {
    	width: 260px;
    	border: 2px solid green;
    	background-color: yellow;
    	text-align: center;
	}
    </style>
  </head>
  <body>
    <h2> 当前请求返回以下信息
    <%--  ${requestScope['SPRING_SECURITY_403_EXCEPTION'].message} --%>
    	${msg}
    </div>
    <hr>
  </body>
</html>

