<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/fn.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<%@include file="/common/su.jsp" %>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<script src="${ctx}/js/jquery-1.5.2.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		if(window.self != window.top) {
			window.top.location.href = self.location.href;
		}
		window.onload = function(){
			if('${msg}' != '') {
				alert('${msg}');
			}
			$("#checkCodeImg").click(function(){
				$(this).attr("src","${ctx}/imageCode.jsp?" + new Date());
			});
			$("#loginForm").submit(function(){
				if($("#yanz").val() == "") {
					alert("请输入认证码!");
					return false;
				}
			});
		};
	</script>
		
  </head>
  
  <body>
 <form action="${ctx}/login.do" method="post" id="loginForm">
	Account  : <input id="user" name="userAccount" value="${param.userAccount}" type="text"/><br/>
	Password : <input id="password" name="password" type="password" /><br/>
	
	<input id="yanz" name="yanz" type="text" />
	<img id="checkCodeImg" alt="快点我" src="${ctx}/imageCode.jsp?<%=System.currentTimeMillis() %>" width="50" height="26" /><br>
	
	<input type="submit" value="确 认" id="signupsubmit"/>
	<input name="重置" type="reset"	value="重 置" />
		
 </form>
  </body>
</html>
