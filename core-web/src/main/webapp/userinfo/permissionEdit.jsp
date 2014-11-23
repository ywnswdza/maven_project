<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
	<script src="${ctx}/js/lib/ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
	<script src="${ctx}/js/lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	--> 

   <script type="text/javascript"> 	
   		var urlTreeMgr = null;
        $(function () {
        	var height = $(window).height() - 45;
           $("#tabId").ligerTab({
           		height : height,
           		contextmenu : false
           });
           var treeData = eval("("+ $("#treeData").html() +")");
           urlTreeMgr = $("#urlTree").ligerTree({
           		nodeWidth: 200,
           		textFieldName : 'name',
           		autoChildCheckboxEven : false,
           		checkIncomplete : true,
           		data : treeData,
           		isExpand : function(data,level) {
           			if(data.data.type >= 2) return false;
           			return true;
           		},
           		isLeaf : function(node){
           			return node.isLeaf;
           		}
           });
           
           var hasPer = eval("("+ $("#hasPer").html() +")");
          	for(var i = 0; i < hasPer.length;i++) {
          		var $li = $("#urlTree").find("#" + hasPer[i].resourceId);
          		$li.find("> .l-body .l-checkbox-unchecked").removeClass("l-checkbox-unchecked").addClass("l-checkbox-checked");
          	}
          	
  		});
  	
  		function savePermission(){
  			var nodes = urlTreeMgr.getChecked();
  			if(!nodes || nodes.length == 0) return;
  			var selecedRid = [];
  			for(var i =0;i<nodes.length;i++) {
  				var node = nodes[i];
  				selecedRid.push(node.data.id);
  			}
  			//console.info(selecedRid.join(","));
  			ajaxSubmit(selecedRid.join(","));
  		}
  		
  		function ajaxSubmit(resourceIds){
  			$.myAjax({
  				type : 'POST',
  				url : '${ctx}/system/rolesResources/savePer.do',
  				data : {
  					resourceIds : resourceIds,
  					rolesId : '${roles.roleId}'
  				},
  				ajaxSuccess : function(data){
  					$.ligerDialog.alert("操作成功!!!!",function(){
  						parent.f_refresh({close : true});
  					});
  				}
  			});
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
  	  	<span style="font-size:12pt;font-weight: normal;padding: 5px auto;">当前操作  >> 设置权限  >> 角色名称  [&nbsp;<span style="font-weight: bolder;">${roles.roleName }</span>&nbsp;]</span>
 		<div id="tabId" style="margin-top: 3px;">
 			<div title="分配权限" tabid="urlt" style="overflow:auto;">
 				<div style="border:1px solid #ccc; overflow:auto; padding: 10px 30px;">
 					<ul id="urlTree"></ul>
 				</div>
 			</div>
 			<!-- <div title="方法权限" tabid="mt">
 				<div style="border:1px solid #ccc; overflow:auto; padding: 10px 30px;">
 					<ul id="treeMethod"></ul>
 				</div>
 			</div> -->
		</div>
		
		<span id="treeData" style="display: none;">${menus }</span>
		<span id="hasPer" style="display: none;">${hasPer }</span>
  </body>
</html>
