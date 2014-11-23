<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	
	<script src="${ctx}/js/jquery-easyui-1.3.6/jquery.min.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.6/themes/default/easyui.css">  
	<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.6/themes/icon.css">  
	<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>  
	
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	
	
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
           		url : '${ctx }/system/userInfo/save.do',
				success:successCall,
				onSubmit : function(){
					return $(this).form("validate");
				}
			});
			
			

  		});
  		
  		function submitForm () {
  			$("#form1").submit();
  		}
  		
/*   		function ajaxSubmit(){
  			var $form = $("#form1");
  			$.ajax({
  				type : 'POST',
  				url : $form.attr("action"),
  				dataType : 'json',
  				data : $form.serializeArray(),
  				success : successCall,
  				error : errorCall
  			});
  		} */
  		
  		function successCall(datas) {
  			var data = eval('(' + datas + ')');
  			var $userId = $("#input_userId");
  			if($userId.val() == ""){
  				$userId.val(data.userId);
  			}
  			$.messager.alert("提示信息","操作成功！！！","info");
  			parent.f_refresh({close:true});
  		}
  		
  </script>
   <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .l-table-edit-td select,.l-table-edit-td input[type!='radio'] {
        	width: 180px;
        }
    </style>
  
   </head>
  
   <body style="background-color: #F4FBF4;padding: 10px;">
  	  <form name="form1" method="post"  id="form1" action="${ctx }/system/userInfo/save.do">
   	  		<input type="hidden" id="input_userId" name="userId" value="${vo.userId}">
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        	<tr>
	                <td align="right" class="l-table-edit-td">登录帐号:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
	                		<input value="${vo.userAccount}" class="easyui-validatebox textbox" name="userAccount" data-options="required:true"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	                <td align="right" class="l-table-edit-td">用户名称:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
	                		<input value="${vo.username}" class="easyui-validatebox textbox" name="username" data-options="required:true"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	            </tr>
	            <c:if test="${empty vo.userId }">
		        	<tr>
		                <td align="right" class="l-table-edit-td">用户密码:</td>
		                <td align="left" class="l-table-edit-td" style="width:160px" >
		                		<input value="${vo.password}" class="easyui-validatebox textbox" id="password" name="password" type="password" data-options="required:true"/>
		                </td>
		                <td class="l-table-edit-td" width="20"></td>
		                <td align="right" class="l-table-edit-td">密码确认:</td>
		                <td align="left" class="l-table-edit-td" style="width:160px" >
		                		<input value="${vo.password}" class="easyui-validatebox textbox" type="password" id="password2" validType="eqPwd['#password']" data-options="required:true" invalidMessage="两次输入密码不匹配"/>
		                </td>
		                <td class="l-table-edit-td" width="20"></td>              
		            </tr>
	            </c:if>
	        	<tr>
	                <td align="right" class="l-table-edit-td">是否超级用户:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
	                		 是<input type="radio" ${vo.isSupperUser and !empty vo.userId ? 'checked' : '' } name="isSupperUser" id="input_isSupperUser1" value="true"/>
	                  		 否<input type="radio" ${!vo.isSupperUser ? 'checked' : '' } name="isSupperUser" id="input_isSupperUser2" value="false"/>
	                </td>
	            	<td class="l-table-edit-td" width="20"></td>	 
	            	<td align="right" class="l-table-edit-td">是否有效:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
	                		 是<input type="radio" ${vo.isEnabled or empty vo.userId  ? 'checked' : '' }  name="isEnabled" id="input_isEnabled1" value="true"/>
	                  		 否<input type="radio" ${!vo.isEnabled and !empty vo.userId ? 'checked' : '' } name="isEnabled" id="input_isEnabled2" value="false"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	            </tr>
	            <tr>
	                <td align="right" class="l-table-edit-td">用户描述 :</td>
	                <td align="left" class="l-table-edit-td" colspan="4" >
	                		<textarea name="userDesc" style="width: 98%;">${vo.userDesc}</textarea>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>
	            </tr>
	            
	      	</table>
  	  </form>
  </body>
</html>
