<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	
	<link rel="stylesheet" type="text/css" href="${mainframe }/css/form.css">  
	<link rel="stylesheet" type="text/css" href="${mainframe }/js/jquery-easyui-1.3.6/themes/default/easyui.css">  
	<link rel="stylesheet" type="text/css" href="${mainframe }/js/jquery-easyui-1.3.6/themes/icon.css">  
	<script src="${mainframe }/js/jquery-easyui-1.3.6/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${mainframe }/js/common.js"></script>
	<script type="text/javascript" src="${mainframe }/js/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${mainframe }/js/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>  
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

   <script type="text/javascript"> 	
       $(function () {
           $("#form1").form({
				onSubmit : function(){
					if($(this).form("validate") == true) {
						$.myAjax({
							url : $(this).attr("action"),
							data : $(this).serializeArray(),
							ajaxSuccess : successCall
						});
					}
					return false;
				}
			});
  		});
  		
  		function submitForm () {
  			$("#form1").submit();
  		}
  		
  		function successCall(data) {
  			//$.messager.alert("提示信息","操作成功！！！","info");
  			parent.f_refresh({close : true,id : data.id});
  		}
  		
  </script>

  
   </head>
  
   <body style="background-color: #F4FBF4;padding: 10px;">
  	  <form name="form1" method="post"  id="form1" action="${ctx }/model/save.do">
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        	<tr>              
	                <td align="right" class="l-table-edit-td">名称:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px">
	                		<input value="${vo.name}" name="name" class="easyui-validatebox textbox" data-options="required:true,validType:{length:[0,255]}"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>              
	            </tr>
	            <tr>              
	                <td align="right" class="l-table-edit-td">key:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px">
	                		<input value="${vo.key}" name="key" class="easyui-validatebox textbox" data-options="required:true,validType:{length:[0,255]}"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>              
	            </tr>
	        	<tr>
	                <td align="right" class="l-table-edit-td">类别:</td>
	                <td align="left" class="l-table-edit-td" >
	                		<input value="${vo.category}" name="category" class="easyui-validatebox textbox" data-options="required:false,validType:{length:[0,255]}"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td">描述:</td>
	                <td align="left" class="l-table-edit-td" >
	                	<textarea name="description" class="easyui-validatebox textbox" data-options="required:false,validType:{length:[0,255]}"></textarea>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>
	            </tr>
	      	</table>
  	  </form>
  </body>
</html>
