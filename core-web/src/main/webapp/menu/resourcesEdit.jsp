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
        $(function () {
           $("#form1").form({
				success: successCall
				,onSubmit : function(){
					return $(this).form("validate");
				}
			});
  		});
  		
  		function submitForm () {
  			$("#form1").submit();
  		}
  		
  		function successCall(datas) {
  			var $id = $("#id");
  			var data = eval('(' + datas + ')');
  			if($id.val() == ""){
  				$id.val(data.id);
  			}
  			$.messager.alert("提示信息","操作成功！！！","info");
  			parent.f_refresh({
  				close : true,
  				treeEdit : true,
  				type : $("input[name='type']").val(),
  				parentId : $("input[name='parentId']").val(),
  				depth : $("input[name='depth']").val(),
  				name : $("input[name='name']").val(),
  				priority : $("input[name='priority']").val(),
  				id : $("#id").val(),
  				isLeaf : $("input[name='isLeaf']:checked").val(),
  			});
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
  	  <form name="form1" method="post"  id="form1" action="${ctx }/system/resources/save.do">
   	  		<input type="hidden" id="id" name="id" value="${vo.id}">
   	  		<input type="hidden" name="parentId" value="${vo.parentId}">
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        	<tr>
	                <td align="right" class="l-table-edit-td">菜单名称:</td>
	                <td align="left" class="l-table-edit-td">
	                		<input value="${vo.name}" name="name" class="easyui-validatebox textbox" data-options="required:true"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	                <td align="right" class="l-table-edit-td">资源类型:</td>
	                <td align="left" class="l-table-edit-td">
						 <select class="easyui-combobox" name="type" style="width: 130px;">
						 	<option value="1" ${vo.type eq 1 ? 'selected' : ''}>系统或目录</option>
						 	<option value="2" ${vo.type eq 2 ? 'selected' : ''}>菜单链接</option>
						 	<option value="3" ${vo.type eq 3 ? 'selected' : ''}>方法链接</option>
						 </select>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	            </tr>
	             <tr>
	                <td align="right" class="l-table-edit-td">深度:</td>
	                <td align="left" class="l-table-edit-td">
						    <input value="${vo.depth}" name="depth" class="easyui-numberspinner" readonly="readonly"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>
	                <td align="right" class="l-table-edit-td">优先级:</td>
	                <td align="left" class="l-table-edit-td">
						    <input value="${vo.priority}" name="priority" class="easyui-numberspinner"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	
	            </tr>
	        	<tr>
	                <td align="right" class="l-table-edit-td">资源链接:</td>
	                <td align="left" class="l-table-edit-td" colspan="4">
	                		<input value="${vo.linkUrl}" style="width: 98%;" name="linkUrl" class="textbox">
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	            </tr>
	            <tr>
	                <td align="right" class="l-table-edit-td">点击刷新:</td>
	                <td align="left" class="l-table-edit-td">
	                	<input type="radio" ${vo.isRefresh eq true ? 'checked' : '' } name="isRefresh" value="true"/>  是
	                  	<input type="radio" ${empty vo.isRefresh or vo.isRefresh eq false ? 'checked' : '' } name="isRefresh" value="false"/> 否
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	            </tr>
	        	<tr>
	                <td align="right" class="l-table-edit-td">是否有效:</td>
	                <td align="left" class="l-table-edit-td">
	                	<input type="radio" ${vo.isEnabled ? 'checked' : '' } name="isEnabled" value="true"/>  是
	                  	<input type="radio" ${!vo.isEnabled ? 'checked' : '' } name="isEnabled" value="false"/>  否
	                </td>
	                <td class="l-table-edit-td" width="20"></td>
	            </tr>
	           <tr>
	           		<td align="right" class="l-table-edit-td">是否叶子节点:</td>
	                <td align="left" class="l-table-edit-td">
	                	<input type="radio" ${vo.isLeaf ? 'checked' : '' } name="isLeaf" value="true"/>  是
	                  	<input type="radio" ${!vo.isLeaf ? 'checked' : '' } name="isLeaf" value="false"/>  否
	                </td>
	           </tr>
	            <tr>          	         
	                <td align="right" class="l-table-edit-td">描述:</td>
	                <td align="left" class="l-table-edit-td" colspan="4">
	                		<textarea name="desc" style="width: 100%;" class="easyui-textbox">${vo.desc }</textarea>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	
	            </tr>
	      	</table>
  	  </form>
  </body>
</html>
