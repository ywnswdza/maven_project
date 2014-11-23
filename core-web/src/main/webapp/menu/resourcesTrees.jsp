<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link rel="stylesheet" href="${ctx}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>

	<script type="text/javascript" src="${ctx}/js/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/js/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/js/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	
	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <script type="text/javascript">
  		
  		var zTree = null;
  	
  		window.onload = function(){
  			$("body").height($(document).height() - 35);
  			zTree = $.fn.zTree.init($("#tree1"),{		
				async: {
					enable: true,
					url:"${ctx}/system/resources/loadChildByPid.do",
					autoParam:["id=parentId", "name=n", "level=lv"],
					otherParam:{"otherParam":"zTreeAsyncTest"}
				},			
				callback: {
					onRightClick: OnRightClick,
					beforeDrag: beforeDrag,
					beforeDrop: beforeDrop,
					onDrop : function(srcEvent, treeId, treeNodes, targetNode, moveType, isCopy) {
						var currentParentNode = moveType == "inner" ? targetNode : targetNode.getParentNode();
						var drapNodeIds = [];
						var drapNodePriority = [];
						var drapNode = treeNodes[0];
						var qwDepth = currentParentNode.depth + 1;
						var priority = 1;
						switch(moveType) {
							case "next":
								priority = targetNode.priority + 1;
							break; 
							case "prev":
								priority = targetNode.priority - 1;
							break;
							default :
								if(currentParentNode.children && currentParentNode.children.length > 0) priority = currentParentNode.children[currentParentNode.children.length -1].priority + 1;
							break;
						}
						//console.info(priority);
						for(var i =0 ; i < treeNodes.length ;i++) {
							drapNodeIds.push(treeNodes[i].id);
							var depthcd = qwDepth - treeNodes[i].depth;
							treeNodes[i].priority = priority + i;
							drapNodePriority.push(treeNodes[i].priority);
							recursionUpdateNode(treeNodes[i],0,depthcd);
						}
						$.ajax({
							url : '${ctx}/system/resources/moveMenu.do',
							type :'post',
							data : {
								preId : dropPreNodes.id,
								preIsLeaf : !dropPreNodes.isParent,
								drapNodeIds : drapNodeIds.join(","),
								drapNodePriority : drapNodePriority.join(","), 
								dropNodeId : currentParentNode.id							
							},dataType : 'json',
							success : function(data){
								//alert("success");
								f_refresh();
							},
							error : errorCall
						});
						dropPreNodes = null;
					},
					onClick : function(event, treeId, treeNode, clickFlag) {
						if(treeNode.isParent) {
							parent.reloadByTreeClick({pId : treeNode.id});
						} else {
							parent.selectRowByTreeClick({name : treeNode.name});
						}
						//alert(treeNode.nodeText);
					}
				},edit : {
					drag : {
						isMove: true, 
						prev : true, 
						inner :true,  
						next : true   
					},
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				}
  			},[{name:"所有菜单",id : 0,isParent:true,drop : false}]);
  		};
  		
		
		function OnRightClick(event, treeId, treeNode) {
			var y = event.clientY + 5,x = event.clientX + 10;
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				return;
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
			}
			$("#rMenu ul").show();
			$("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
		}

		function hideRMenu() {
			if ($("#rMenu")) $("#rMenu").css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				$("#rMenu").css({"visibility" : "hidden"});
			}
		}
		
		//var addCount = 0;
		var addAndMoidfy = "add";
		function addTreeNode() {
			hideRMenu();
			var selectNode = zTree.getSelectedNodes()[0] || null;
			if(selectNode) {
				var url = "${ctx}/system/resources/editByTree.do";
				url += "?parentId=" + selectNode.id;
				url += "&depth=" + (parseInt(selectNode.depth) + 1 || 1);
				url += "&isLeaf=true&isEnabled=true" ;
				addAndMoidfy = "add";
				parent.f_openWin("添加菜单",url,$(window).height() * 0.8 ,800);
			}
		}
		
		function modifyTreeNode(){
			hideRMenu();
			var selectNode = zTree.getSelectedNodes()[0] || null;
			if(selectNode) {
				if(selectNode.id == 0) return;	
				var url = "${ctx}/system/resources/edit.do?id=" + selectNode.id;
				addAndMoidfy = "modify";
				parent.f_openWin("修改菜单",url,$(window).height() * 0.8 ,800);
			}
		}
		
		function accessSetting(){
			hideRMenu();
			var selectNode = zTree.getSelectedNodes()[0] || null;
			if(selectNode) {
				if(selectNode.id <= 0) return;
				parent.f_openWin("访问限制",
					"${ctx}/system/rolesResources/showAccessSetting.do?key=" + selectNode.id,
					$(window).height() * 0.8,
					650
				);
			}
		}
		
		function addNodeSuccess(opts){
			var selectNode = zTree.getSelectedNodes()[0] || null;
			if(selectNode && opts) {
				if(addAndMoidfy == "add") {
					var newNode = opts;
					newNode.isParent = opts.isLeaf == "true" ? false : true;
					newNode.checked = selectNode.checked;
					newNode.iconSkin = "type" + opts.type;
					newNode.id = opts.id;
					//zTree.getNodeByParam("")
					zTree.addNodes(selectNode,newNode);
				} else {
					selectNode.name = opts.name;
					selectNode.parentId = opts.parentId;
					selectNode.id = opts.id;
					selectNode.depth = opts.depth;
					selectNode.priority = opts.priority;
					selectNode.iconSkin = "type" + opts.type;
					selectNode.isParent = opts.isLeaf == "true" ? false : true;
					zTree.updateNode(selectNode);
				}
				f_refresh();
			}
		}
		
		
		function removeTreeNode() {
			hideRMenu();
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length>0) {	
				if(nodes[0].id == 0) return;		
				var parentNode = nodes[0].getParentNode();
				deleteNode(nodes[0],parentNode);
			}
		}
		
		
		
	function deleteNode(node,parentNode) {
		 parent.$.messager.confirm('确认删除', '如果删除将连同子节点一起删掉。请确认删除?', function(r){
			if (r){
				zTree.removeNode(node);
				var pIsLeaf = !parentNode.isParent;
				$.ajax({
					url : '${ctx}/system/resources/deleteById.do',
					type :'post',
					data : {
						id : node.id,
						parentId : parentNode.id,
						isLeaf : pIsLeaf
					},dataType : 'json',
					success : function(data){
						//alert("success");
						f_refresh();
					},
					error : errorCall
				});
			}
		});
	}
	
	function f_refresh(){
		parent.loadDataGrid();
	}
	
	var dropPreNodes = null;
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		if(!targetNode) return false;
		var currentParentNode = moveType == "inner" ? targetNode : targetNode.getParentNode();
		var isDrop = currentParentNode ? currentParentNode.drop !== false : true;
		if(isDrop) dropPreNodes = treeNodes[0].getParentNode();
		// kindId .........
		return isDrop;
	}
	
	/**
	*/
	function recursionUpdateNode(node,level,depthcd){
		if(node.children && node.children.length > 0) {
			for(var i in node.children) {
				var childNode = node.children[i];
				recursionUpdateNode(childNode,level + 1,depthcd);
			}
		}
		node.depth = node.depth + depthcd;
		zTree.updateNode(node);
	}
	
  </script>
  <style type="text/css">
 	div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #95BCE2;text-align: left;padding: 2px;}
	div#rMenu ul li{
		margin: 1px 0;
		padding: 0 5px;
		cursor: pointer;
		list-style: none outside none;
		background-color: #CEE6FF;
		font-size: 10pt;
		/* #DCF8A8 */
	}
	div#rMenu ul li:hover {
     background: #DCF8A8;
    }
	div#rMenu ul {
		padding: 0px;
		list-style: none;
		margin: 1px;
	}
	
	.ztree li span.button.type1_ico_open{margin-right:2px; background: url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.type1_ico_close{margin-right:2px; background: url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.type1_ico_docu{margin-right:2px; background:  url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	
	.ztree li span.button.type2_ico_open{margin-right:2px; background: url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.type2_ico_close{margin-right:2px; background: url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.type2_ico_docu{margin-right:2px; background:  url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	
	.ztree li span.button.type3_ico_open{margin-right:2px; background: url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/2.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.type3_ico_close{margin-right:2px; background: url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/2.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.type3_ico_docu{margin-right:2px; background:  url(${ctx}/js/zTree_v3/css/zTreeStyle/img/diy/2.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
  </style>
   </head>
  
  <body style="background-color: #F4FBF4;">
  		<div style="float:left; border:1px solid #ccc; overflow:auto;width: 180px;height: 100%;">
	    	<ul id="tree1" class="ztree"></ul>
	    </div> 
	    <div id="rMenu">
			<ul>
				<li id="m_add" onclick="addTreeNode();">增加菜单</li>
				<li id="m_modify" onclick="modifyTreeNode();">修改菜单</li>
				<li id="m_del" onclick="removeTreeNode();">删除菜单</li>
				<li id="m_del" onclick="accessSetting();">访问限制</li>
			</ul>
		</div>
  </body>
</html>
