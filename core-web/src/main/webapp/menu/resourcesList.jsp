<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<link href="${ctx }/css/searchbar2.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/jquery-1.5.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.6/themes/default/easyui.css"/>  
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.6/themes/icon.css"/>  
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>  

	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerDialog.js"></script>

	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	
	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <script type="text/javascript">
  	
  		var $grid = null;
  
  		$(function(){
  			$("#layout1").layout();
  			$("div[position='left']").height($("div[position='left']").parent().height());
  			$("#layout1").height($("#layout1").height() - 20).addClass("layout-bottom");
  			$("#treeFrame").attr("src","${ctx}/menu/resourcesTrees.jsp").css("height","99%");
  			$("#gridList").height($("#layout1").height() - $("div.searchbar").outerHeight() - 28).datagrid({
			    url:'${ctx }/system/resources/listData.do',
			    columns:[[
			    	{field:'checkbox',checkbox:true,width:100},
			    	{field:'id', title:'id',width:100},
			    	{field:'parentId',title:'parentId',width:100},
			    	{field:'name',title:'Name',width:100},
			    	{field:'linkUrl',title:'linkUrl',width:200},
			    	{field:'isRefresh',title:'点击刷新',width:80,formatter:function(value,row,index){
			   			if(value == true) return "是";
			   			return "否";
			   		}},
			    	{field:'priority',title:'优先级',width:80},
			   		{field:'depth',title:'depth',width:100,align:'right'},
			   		{field:'updateTime',title:'更新时间',width:100,formatter:function(value,row,index){
			   			if(value) return new Date(value).format("yyyy-MM-dd");
			   		}},
			   		{field:'createTime',title:'创建时间',width:100,formatter:function(value,row,index){
			   			if(value) return new Date(value).format("yyyy-MM-dd");
			   		}}
			    ]],
			    pagination : true,
			    rownumbers : true,
			    toolbar : '#tb',
			    queryParams : {
			    	'parentId' : 0
			    }
  			});
  			$grid = $("#gridList");
  		});
  		
  		
  		function refresh(opts){
  			//alert(JSON.stringify(opts));
  			 refreshTree(opts);
  			//
  		}
  		
  		function loadDataGrid(){
  			$grid.datagrid("load",ajaxToPost($("#queryform").serializeArray()));
  		}
  		
  		function refreshTree(opts){
  			if(opts.close && opts.close == true && $ligerDialog) $ligerDialog.close();
  			if(opts.treeEdit && opts.treeEdit == true) $(document).contents().find("#treeFrame")[0].contentWindow.addNodeSuccess(opts);
  		}
  		
  		var $ligerDialog = null;
  		function f_openWin(title,url,height,width){
			$ligerDialog = parent.f_openWin(title,url,height,width);
		}
  		
  		function reloadByTreeClick(opts){
			if(opts.pId >=0) $("#pId").val(opts.pId);
			$("input[name='name']").val("");
			loadDataGrid();
		}
		
		function selectRowByTreeClick(opts){
			$("#pId").val("");
			$("input[name='name']").val(opts.name);
			loadDataGrid();
		}
		
		function itemclick(item){
			var width = 700;//$(document).width() * 0.7;
			var height = $(document).height() * 0.8;
			switch (item.id) {
			case "modify":
				var selectRow = $grid.datagrid("getSelections");
				//console.info(selectRow);
				if(!selectRow || selectRow.length == 0) {
					$.messager.alert("提示信息","请至少选择一行进行操作!!!","info");
					return;
				} else if(selectRow.length > 1)  {
					$.messager.alert("提示信息","最多选择一行进行操作!!!","info");
					return;
				} 
				var url = "${ctx}/system/resources/edit.do?id=" + selectRow[0].id;
				f_openWin("修改菜单",url,height,width);
				break;

			default:
				break;
			}
		}
  </script>
  
  <style type="text/css">
  	.layout-bottom {
  		border-bottom: 1px solid #95B8E7;
  	}
  </style>
  
   </head>
  
  
  
<body style="background-color: #F4FBF4;padding: 10px;">
<div id="layout1" class="easyui-layout" fit="true">
	<div data-options="region:'west',title:'菜单管理',split:false" style="width: 200px;">
		<iframe id="treeFrame" scrolling="auto" frameborder="0" width="100%"></iframe>
	</div>
	<div data-options="region:'center',title:'菜单列表'" style="height: 100%;">
   		<table id="gridList"></table>
   		 <div id="tb" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
			<!-- 	<a onclick="itemclick({id:'add'})" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加</a> 
				<a onclick="itemclick({id:'modify'})" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				 <a onclick="itemclick({id:'delete'})" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a> -->
			</div>
			<div>
				<form id="queryform">
				<input type="hidden" id="pId" name="parentId" value="0"/>
				资源名称：<input class="textbox" name="name" style="width:100px">
				是否有效：
				<select class="easyui-combobox" panelHeight="auto" style="width:104px" name="isEnabled">
					<option value="">所有</option>
					<option value="true" selected="selected">是</option>
					<option value="false">否</option>
				</select>
				<br>
				创建时间：<input class="easyui-datebox" style="width:104px;">
				&nbsp;&nbsp;&nbsp;&nbsp;至  &nbsp;&nbsp;&nbsp;  <input class="easyui-datebox" style="width:104px;margin-left: 14px;">
				<a href="#" onclick="refresh();" class="easyui-linkbutton" iconCls="icon-search">Search</a>
				</form>
			</div>
		</div>
  </div> 
  </body>
</html>
