<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ ctx}/commonjs/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ ctx}/commonjs/uploadify/jquery.uploadify.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	
		body{
			padding: 10px 10px 10px 10px;
		}
		
		.formLeft{
			margin:auto;
			padding: 10px;
			float: left;
			clear: left;
		}
		.formRight{
			padding: 10px;
		}
	</style>
	
	<script type="text/javascript">
		
	</script>
	
  </head>
  
  <body>
    <form action="${pageContext.request.contextPath }/system/attachment/uploads.do" method="post" enctype="multipart/form-data">
    	<div>
    		<div class="formLeft"> 文件 : </div> <div class="formRight"><input type="file" name="attach1"/></div>
    		<div class="formLeft"> 文件 : </div> <div class="formRight"><input type="file" name="attach2"/></div>
    		<div class="formLeft"> 文件保存路径 : </div> <div class="formRight"><input type="text" name="fileFolder"/></div>
    		<div class="formLeft"> 所属组 : </div> <div class="formRight"><input type="text" name="groupId"/></div>
    		<div class="formLeft"> <input type="submit" value="submit"/> </div>
    	</div>
    </form>
   	 ${attach.fileSize}${attach.sizeUnit }
   	 <br> ${ctx }/uf/${attach.fileLocation }
   	 <img alt="" src="${ctx }/uf/${attach.fileLocation }">
   	 
  </body>
</html>
