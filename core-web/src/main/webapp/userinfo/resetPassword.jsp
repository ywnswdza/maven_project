<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	
	<link rel="stylesheet" type="text/css" href="${ctx }/css/form.css">  
	<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.6/themes/default/easyui.css">  
	<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.6/themes/icon.css">  
	<script src="${ctx}/js/jquery-easyui-1.3.6/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>  
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

   <script type="text/javascript"> 	
   
   
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});
   
   
       $(function () {
           $("#form1").form({
				url : '${ctx }/system/userInfo/modifyPassword.do',
				success:successCall,
				onSubmit : function(){
					return $(this).form("validate");
				}
			});
  		});
  		
  		function submitForm () {
  			$("#form1").submit();
  		}
  		
  		function successCall(data) {
  			if(typeof data === "string") data = eval("(" + data + ")");
  			if(data && data.message) {
  				$.messager.alert("提示信息",data.message,"info");
  			//parent.refresh({close : true});
  			} else {
  				parent.$ligerDialog.close();
  			}
  		}
  		
  </script>
   <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .easyui-datebox,.easyui-datebox,.textbox{
  			width: 180px;
  		}
  		.easyui-combobox {
  			width: 182px;
  		}
    </style>
  
   </head>
  
   <body style="background-color: #F4FBF4;padding: 10px;">
  	  <form name="form1" method="post"  id="form1">
   	  		<input type="hidden" id="userId" name="userId" value="${sessionScope.user.userId}">
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        	<tr>
	                <td align="right" class="l-table-edit-td">原始密码:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
	                		<input class="easyui-validatebox textbox" id="oldPassword" name="oldPassword" type="password" data-options="required:true"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>  	              
	            </tr>
	        
	        	<tr>
	                <td align="right" class="l-table-edit-td">新密码:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
	                		<input value="" class="easyui-validatebox textbox" id="password" name="password" type="password" data-options="required:true"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>  	              
	            </tr>
	        	<tr>
	                <td align="right" class="l-table-edit-td">密码确认:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
	                		<input value="" class="easyui-validatebox textbox" type="password" id="password2" validType="eqPwd['#password']" data-options="required:true" invalidMessage="两次输入密码不匹配"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>              
	            </tr>
  	  </form>
  </body>
</html>
