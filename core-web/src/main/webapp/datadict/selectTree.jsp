<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'selectTree.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
	<script src="${ctx}/js/lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		var treeMgr;
		$(function(){
			//var initData = eval("("+ $("#treeInitData").html() +")");
			treeMgr = $("#tree1").ligerTree({
				url : '${ctx}/system/commonTree/loadChildByPid.do?kindId=' + '${param.kindId}',
				checkbox : true,
				autoChildCheckboxEven : false,
				autoParentCheckboxEven : true,
				textFieldName : 'nodeText',
				isLeaf : function(node) {
					return node.isLeaf;
				},
				delay : function(e) {
					var data = e.data;
					return { url: '${ctx}/system/commonTree/loadChildByPid.do?pId=' + data.id }
				}
			});
		});
		
		function confirmSelect(){
			if(!treeMgr) return;
			var nodes = treeMgr.getChecked();
			if(!nodes || nodes.length == 0) return;
			var ids = [],names = [] ,pids = [];
			for(var i in nodes) {
				var node = nodes[i].data;
				ids.push(node.id);
				names.push(node.nodeText);
				pids.push(node.pId);
			}
			//alert(names.join(","));
		}
	</script>
	
  </head>
  
  <body>
   	<ul id="tree1"></ul>
   	<span style="display: none;" id="treeInitData">${treeList}</span>
  </body>
</html>
