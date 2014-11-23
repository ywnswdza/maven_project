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
					url:"${ctx}/system/commonTree/loadChildByPid.do?kindId=${param.kindId}",
					autoParam:["id=pId", "name=n", "level=lv"],
					otherParam:{"otherParam":"zTreeAsyncTest"}
				},			
				data: {
					key: {
						name : "nodeText"
					}
				}, callback: {
					onRightClick: OnRightClick,
					beforeDrag: beforeDrag,
					beforeDrop: beforeDrop,
					onDrop : function(srcEvent, treeId, treeNodes, targetNode, moveType, isCopy) {
						var currentParentNode = moveType == "inner" ? targetNode : targetNode.getParentNode();
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
						var drapNodeIds = [];
						var drapNodePriority = [];
						var drapNode = treeNodes[0];
						var qwDepth = currentParentNode.depth + 1;
						//alert(qwDepth);
						//alert(JSON.stringify(currentParentNode.depth));
						for(var i =0 ; i < treeNodes.length ;i++) {
							drapNodeIds.push(treeNodes[i].id);
							var depthcd = qwDepth - treeNodes[i].depth;
							treeNodes[i].priority = priority + i;
							drapNodePriority.push(treeNodes[i].priority);
							recursionUpdateNode(treeNodes[i],0,{depth:depthcd,kindId : currentParentNode.kindId});
						}
						$.ajax({
							url : '${ctx}/system/commonTree/moveTree.do',
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
						parent.reloadByTreeClick({id : treeNode.id,depth : treeNode.depth});
						//alert(treeNode.nodeText);
					}<c:if test="${!empty param.kindId}">,onNodeCreated : function(event, treeId, treeNode){
						 if(treeNode.depth == 1) {
						 	zTree.selectNode(treeNode);
						 	zTree.expandNode(treeNode,true);
						 	parent.reloadByTreeClick({pId : treeNode.id});
						 }
					}</c:if>
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
  			},[
  			<c:if test="${empty param.kindId}">
  				{nodeText:"梦想手游",id : 0,isParent:true,drop : false}//zTree.expandAll(true);
  			</c:if>
  			]);
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
			<c:if test="${!empty param.kindId}">
				 if(treeNode.pId == 0) $("#m_del").hide();
				 else $("#m_del").show();
			</c:if>
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
				if("${param.kindId}" != "" && selectNode.id==0) {
					alert("无权操作!!");
					return;
				}
				var url = "${ctx}/system/commonTree/editByTree.do";
				url += "?pId=" + selectNode.id;
				url += "&depth=" + (parseInt(selectNode.depth) + 1 || 1);
				url += "&kindId=" + (selectNode.kindId || -999);
				url += "&isLeaf=true&isValid=true" ;
				addAndMoidfy = "add";
				top.f_openWin("添加节点",url,$(window).height() * 0.8 ,800);
			}
		}
		
		var $ligerDialog = null;
		function modifyTreeNode(){
			hideRMenu();
			var selectNode = zTree.getSelectedNodes()[0] || null;
			if(selectNode) {
				if(selectNode.id == 0) return;	
				var url = "${ctx}/system/commonTree/edit.do?id=" + selectNode.id;
				addAndMoidfy = "modify";
				top.f_openWin("修改节点",url,$(window).height() * 0.8 ,800);
			}
		}
		
		function addNodeSuccess(opts){
			var selectNode = zTree.getSelectedNodes()[0] || null;
			if(selectNode && opts) {
				if(addAndMoidfy == "add") {
					var newNode = opts;
					newNode.isParent = false;
					newNode.checked = selectNode.checked;
					zTree.addNodes(selectNode,newNode);
				} else {
					selectNode.nodeText = opts.nodeText;
					selectNode.pId = opts.pId;
					selectNode.id = opts.id;
					selectNode.kindId = opts.kindId;
					selectNode.depth = opts.depth;
					selectNode.isLeaf = opts.isLeaf;
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
				if ((nodes[0].children && nodes[0].children.length > 0 ) || nodes[0].isParent == true) {
					parent.$.ligerDialog.confirm("要删除的节点是父节点，如果删除将连同子节点一起删掉。请确认！",function(y){
						if(y) {
							zTree.removeNode(nodes[0]);
							var parentNode = nodes[0].getParentNode();
							deleteNode(nodes[0].id,parentNode.id,!parentNode.isParent);
						}
					});
				} else {
					parent.$.ligerDialog.confirm("请确认删除?",function(y){
						if(y) {
							var parentNode = nodes[0].getParentNode();
							deleteNode(nodes[0].id,parentNode.id,!parentNode.isParent,nodes[0]);
						}
					});
				}
			}
		}
		
	function deleteNode(id,pid,pIsLeaf,node) {
		$.myAjax({
			url : '${ctx}/system/commonTree/deleteById.do',
			data : {
				id : id,
				pId : pid,
				isLeaf : pIsLeaf
			},
			ajaxSuccess : function(data){
				//alert("success");
				zTree.removeNode(node);
				//f_refresh();
			}
		});
	}
	
	function f_refresh(){
		parent.refresh();
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
	function recursionUpdateNode(node,level,opts){
		var depthcd = opts.depth;
		if(opts.kindId) {
			node.kindId = opts.kindId;
		}
		if(node.children && node.children.length > 0) {
			for(var i in node.children) {
				var childNode = node.children[i];
				recursionUpdateNode(childNode,level + 1,opts);
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
  </style>
   </head>
  
  <body style="background-color: #F4FBF4;">
  		<div style="float:left; border:1px solid #ccc; overflow:auto;width: 180px;height: 100%; ">
	    	<ul id="tree1" class="ztree"></ul>
	    </div> 
	    <div id="rMenu">
			<ul>
				<li id="m_add" onclick="addTreeNode();">增加类型</li>
				<li id="m_modify" onclick="modifyTreeNode();">修改类型</li>
				<li id="m_del" onclick="removeTreeNode();">删除类型</li>
			</ul>
		</div>
  </body>
</html>
