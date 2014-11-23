<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link href="${'$'}{mainframe }/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${'$'}{mainframe }/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />

	<script src="${'$'}{mainframe }/js/jquery-1.5.2.min.js" type="text/javascript" ></script>
	<script src="${'$'}{mainframe }/js/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
    <script src="${'$'}{mainframe }/js/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="${'$'}{mainframe }/js/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="${'$'}{mainframe }/js/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
	<script src="${'$'}{mainframe }/js/common.js" type="text/javascript"></script>
	
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

   <script type="text/javascript"> 	
        var eee;
        $(function () {
            $.validator.addMethod( 
            	"notnull", function (value, element, regexp) {
                        if (!value) return true;
                        return !$(element).hasClass("l-text-field-null");
                 },"不能为空"
            );
            $.metadata.setType("attr", "validate");
            var v = $("#form1").validate({
                errorPlacement: function (lable, element)  {
                    if (element.hasClass("l-textarea"))  element.addClass("l-textarea-invalid");
                    else if (element.hasClass("l-text-field")) element.parent().addClass("l-text-invalid");
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", lable.html()).ligerTip();
                },
                success: function (lable){
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea")) element.removeClass("l-textarea-invalid");
                    else if (element.hasClass("l-text-field")) element.parent().removeClass("l-text-invalid");
                    $(element).removeAttr("title").ligerHideTip();
                },
                submitHandler: ajaxSubmit
            });
            $("#form1").ligerForm();
  		});
  		
  		function submitForm () {
  			$("#form1").submit();
  		}
  		
  		function ajaxSubmit(){
  			var $form = $("#form1");
  			$.ajax({
  				type : 'POST',
  				url : $form.attr("action"),
  				dataType : 'json',
  				data : $form.serializeArray(),
  				success : successCall,
  				error : errorCall
  			});
  		}
  		
  		function successCall(data) {
  		<#list table.keyCoulmnInfo as column>
  			var ${'$'}${column.fieldName} = ${'$'}("#input_id");
  			if(${'$'}${column.fieldName}.val() == ""){
  				${'$'}${column.fieldName}.val(data.${column.fieldName});
  			}
  			<#break>
		</#list>
  			$.ligerDialog.success("操作成功！！！");
  			parent.refresh({close : true});
  		}
  		
  </script>
   <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .l-table-edit-td select,.l-table-edit-td input {
        	width: 180px;
        }
    </style>
  
   </head>
  
   <body style="background-color: #F4FBF4;padding: 10px;">
  	  <form name="form1" method="post"  id="form1" action="${'$'}{ctx }/${colltrollerPath}/save.do">
<#list table.keyCoulmnInfo as column>
   	  		<input type="hidden" id="input_${column.fieldName}" name="${column.fieldName}" value="${'$'}{vo.${column.fieldName}}">
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
	                <td align="left" class="l-table-edit-td" <#if column_index gt 2>style="width:160px"</#if>>
	<#if _javaType == "date">
	                		<input value="<fmt:formatDate value="${'$'}{vo.${column.fieldName}}" pattern="yyyy-MM-dd"/>" name="${column.fieldName}" id="input_${column.fieldName}" ltype="date" validate="{notnull:${column.notNull}}"/>
	<#elseif _javaType == "boolean">
	                		 是<input type="radio" ${'$'}{vo.${column.fieldName} ? 'checked' : '' } name="${column.fieldName}" id="input_${column.fieldName}1" value="true"/>
	                  		 否<input type="radio" ${'$'}{!vo.${column.fieldName} ? 'checked' : '' } name="${column.fieldName}" id="input_${column.fieldName}2" value="false"/>
	<#elseif _javaType == "integer">
						    <input value="${'$'}{vo.${column.fieldName}}" name="${column.fieldName}" id="input_${column.fieldName}" ltype="spinner" ligerui="{type: 'int'}" validate="{required:${column.requiredStr},maxlength:${column.length},notnull:${column.notNull}}" />	
	<#elseif _javaType == "int">
						    <input value="${'$'}{vo.${column.fieldName}}" name="${column.fieldName}" id="input_${column.fieldName}" ltype="spinner" ligerui="{type: 'int'}" validate="{required:${column.requiredStr},maxlength:${column.length},notnull:${column.notNull}}" />	
	<#elseif _javaType == "double">
						    <input value="${'$'}{vo.${column.fieldName}}" name="${column.fieldName}" id="input_${column.fieldName}" ltype="spinner" validate="{required:${column.requiredStr},maxlength:${column.length},notnull:${column.notNull}}"/>	
	<#else>
	                		<input value="${'$'}{vo.${column.fieldName}}" name="${column.fieldName}" id="input_${column.fieldName}" ltype="text" validate="{required:${column.requiredStr},maxlength:${column.length},notnull:${column.notNull}}"/>
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
