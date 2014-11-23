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
	<link href="${ ctx}/js/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ ctx}/js/uploadify/jquery.uploadify.js"></script>
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
		$(function(){
			$("#attach").uploadify ({
				height        : 30,
		        swf           : '${ ctx}/commonjs/uploadify/uploadify.swf',
		        uploader      : '${ctx}/system/attachment/uploads.do?JSESSIONID=${pageContext.session.id}',
		        'cancelImg'   : '${ctx}/js/uploadify/uploadify-cancel.png',  
		        width         : 80 ,
		        auto : false,
		        buttonText : '选择',
		        formData : {
		        	groupId : 'attachmentExaple',
		        	fileFolder : 'attachmentExaple',
		        	"uploadUser.userId" : '${sessionScope.user.userId}'
		        },
		        'queueID' : 'filequeue',
		        removeCompleted : true,
		        onCancel : function(file){
		        	//alert(JSON.stringify(file));
		        },
		        onUploadSuccess : function(file,data,response) {
		        	data.ids[0]
		        	//file['xxxid'] = "xxxxxx";
		        }
		    });
		});
		
		/* <input type="button" onclick="jQuery('#attach').uploadify('upload','*');"> */
		
	</script>
	
  </head>
  
  <body>
   		<div id="filequeue" class="uploadify-queue" style="border: 1px solid #F5F5F5;min-height: 30px;width: 300px;padding-bottom: 5px;">
   		</div>
   	 	<input  id="attach" class="upload_button" type="button">
   	 	<input type="button" class="uploadify-button" style="width: 80px;height: 30px;" onclick="jQuery('#attach').uploadify('upload','*');" value="上传"> 
   	 	
  </body>
</html>
