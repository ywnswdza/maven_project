<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	
	<link rel="stylesheet" type="text/css" href="${'$'}{mainframe }/css/form.css">  
	<link rel="stylesheet" type="text/css" href="${'$'}{mainframe }/js/jquery-easyui-1.3.6/themes/default/easyui.css">  
	<link rel="stylesheet" type="text/css" href="${'$'}{mainframe }/js/jquery-easyui-1.3.6/themes/icon.css">  
	<script src="${'$'}{mainframe }/js/jquery-easyui-1.3.6/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${'$'}{mainframe }/js/common.js"></script>
	<script type="text/javascript" src="${'$'}{mainframe }/js/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${'$'}{mainframe }/js/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>  
	
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
  		<#list table.keyCoulmnInfo as column>
  			var ${'$'}${column.fieldName} = ${'$'}("#${column.fieldName}");
  			if(${'$'}${column.fieldName}.val() == ""){
  				${'$'}${column.fieldName}.val(data.${column.fieldName});
  			}
  			<#break>
		</#list>
  			${'$'}.messager.alert("提示信息","操作成功！！！","info");
  			parent.f_refresh({close : true});
  		}
  		
  </script>

  
   </head>
  
   <body style="background-color: #F4FBF4;padding: 10px;">
  	  <form name="form1" method="post"  id="form1" action="${'$'}{ctx }/${colltrollerPath}/save.do">
<#list table.keyCoulmnInfo as column>
   	  		<input type="hidden" id="${column.fieldName}" name="${column.fieldName}" value="${'$'}{vo.${column.fieldName}}">
</#list>	
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
<#assign _isOpen=false>
<#list table.notKeyCoulmnInfo as column>
	<#assign _javaType="${column.javaType ? lower_case}">
	<#assign tindex= column_index + 1>
	<#if column_index % 2 == 0 && _isOpen == false>
		<#assign _isOpen=true>
	        	<tr>
	</#if>
	                <td align="right" class="l-table-edit-td">${column.label}:</td>
	                <td align="left" class="l-table-edit-td" <#if column_index lt 2>style="width:160px"</#if>>
	<#if _javaType == "date">
	                		<input value="<fmt:formatDate value="${'$'}{vo.${column.fieldName}}" pattern="yyyy-MM-dd"/>" name="${column.fieldName}"  class="easyui-validatebox easyui-datebox" data-options="required:${column.notNull},validType:{length:[0,${column.length}]}"/>
	<#elseif _javaType == "boolean">
	                  		 <select name="${column.fieldName}" class="easyui-combobox">
	                  		 	<option value="true" ${'$'}{vo.${column.fieldName} eq true ? 'selected' : '' }>是</option>
	                  		 	<option value="false" ${'$'}{vo.${column.fieldName} eq false ? 'selected' : '' }>否</option>
	                  		 </select>
	<#elseif _javaType == "integer" || _javaType == "int" || _javaType == "double">
						    <input value="${'$'}{vo.${column.fieldName}}" name="${column.fieldName}" class="easyui-validatebox easyui-numberspinner" data-options="required:${column.notNull},validType:{length:[0,${column.length}]}"/>	
	<#else>
	                		<input value="${'$'}{vo.${column.fieldName}}" name="${column.fieldName}" class="easyui-validatebox textbox" data-options="required:${column.notNull},validType:{length:[0,${column.length}]}"/>	
	</#if>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	<#if (column_index + 1) % 2 == 0 && column_index != 0>
	            </tr>
	     <#assign _isOpen=false>
	</#if>
	<#if column_has_next == false && _isOpen==true>
				</tr>
	</#if>
</#list>		
	      	</table>
  	  </form>
  </body>
</html>
