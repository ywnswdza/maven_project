<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>
<#list table.columns as column><#if column.id == true><#assign tablesId="${column.name}"><#break></#if></#list>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link href="${'$'}{mainframe }/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${'$'}{mainframe }/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<link href="${'$'}{mainframe }/css/searchbar2.css" rel="stylesheet" type="text/css" />
	
	<script src="${'$'}{mainframe}/js/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${'$'}{mainframe}/js/lib/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${'$'}{mainframe}/js/lib/ligerUI/js/plugins/ligerDialog.js"></script>
	<script type="text/javascript" src="${'$'}{mainframe}/js/lib/ligerUI/js/plugins/ligerGrid.js"></script>
	<script type="text/javascript" src="${'$'}{mainframe}/js/lib/ligerUI/js/plugins/ligerToolBar.js"></script>
	<script type="text/javascript" src="${'$'}{mainframe}/js/lib/ligerUI/js/plugins/ligerResizable.js"></script>
	<script type="text/javascript" src="${'$'}{mainframe}/js/common.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${'$'}{mainframe}/js/jquery-easyui-1.3.6/themes/default/easyui.css">  
	<link rel="stylesheet" type="text/css" href="${'$'}{mainframe}/js/jquery-easyui-1.3.6/themes/icon.css">  
	<script type="text/javascript" src="${'$'}{mainframe}/js/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${'$'}{mainframe}/js/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>  
	
	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <script type="text/javascript">
  		
  		var gridMgr = null;
  		
  		window.onload = function(){
  			$("#toptoolbar").ligerToolBar({ items: [
                { text: '增加', click: itemclick,icon:'add',id:'add'},
                { line:true },
                { text: '修改', click: itemclick,icon:'modify',id:'modify'},
         		{ line:true },
                { text: '删除', click: itemclick,icon :'delete',id:'del' },
                 { line:true },
                { text: '<label style="color: red;">提示:点击列名可进行排序！</label>',id:'show'}
            	]
            });
            
            gridMgr = $("#datalist").ligerGrid({
                columns: [ 
                	<#list table.notKeyCoulmnInfo as column>
                	 { display : '${column.label}',name :'${column.fieldName}',width : 100<#if column.javaType == 'Date'>,render :function (row){if(row.${column.fieldName}) return new Date(row.${column.fieldName}).format("yyyy-MM-dd");return ""}<#elseif column.javaType == "boolean" || column.javaType == "Boolean">,render : function(row){return row.${column.fieldName} ? "是" : "否";}</#if>},
                	 </#list>
	               	 { display: '操作', width: 200,render : function(row) {
	                	var html = "<a title=\"修改\" href='javascript:itemclick({\"id\":\"modify\",\"key\":\""+ row.${tablesId} +"\"});'>修改</a>";
	                	 html += "&nbsp;&nbsp;&nbsp;<a href='javascript:itemclick({\"id\":\"del\",\"key\":\""+ row.${tablesId} +"\"});'>删除</a>";
	                	return html;
	                }}
                ],
            	url: '${'$'}{ctx }/${colltrollerPath}/listData.do',
                pageSize: 20,
                sortName: '${tablesId}',
                width: '100%', height: '99%',
                checkbox: true,rownumbers:true,
                fixedCellHeight:false,
                frozenCheckbox : false
            });
            
            
             $("#submitBtn").click(function(){
            	if(!gridMgr) return ;
            	gridMgr.options.newPage = 1;
				var $form = $("#queryform");
            	gridMgr.setOptions({parms : $form.serializeArray()});
            	gridMgr.loadData(true);
            });
  		}
  		
  		var $ligerDialog = null;
		function  itemclick (item){
			//alert(item.text);
			if(!item) return;
			var width = 800;//$(document).width() * 0.7;
			var height = ${'$'}(window).height() * 0.8;
			switch(item.id) {
				case "add" : 
					//alert(item.text);
					$ligerDialog = parent.f_openWin("添加信息","${'$'}{ctx }/${colltrollerPath}/edit.do",height,width);
				break;
				case "modify" : 
					var ids = 0;
					if(item.key) {
						ids = item.key;
					} else {
						var rows = gridMgr.getSelecteds();
						if(!rows || rows.length != 1) {
							$.ligerDialog.error("请选择一行进行修改！");
							return;
						} 
						ids = rows[0]["${tablesId}"];
					}
					$ligerDialog = parent.f_openWin("修改信息","${'$'}{ctx }/${colltrollerPath}/edit.do?id=" + ids,height,width);
				break;
				case "del" : 
					var ids = [];
					if(item.key) {
						ids.push(item.key);
					} else {
						var rows = gridMgr.getSelecteds();
						if(rows.length == 0) {
							$.ligerDialog.error("请至少选择一行！");
							return;
						} 
						for(var i = 0; i < rows.length ; i++) {
							ids.push(rows[i]["${tablesId}"]);
						}
					}
					$.ligerDialog.confirm("确认删除所选内容?",function(yes){
						if(!yes) return;
						$.myAjax({
							url : "${'$'}{ctx }/${colltrollerPath}/delete.do",
							data : {ids : ids.join(",")},
							ajaxSuccess:function(data){
								refresh();
							}
						});
					});
				break;
				default :
					
				break;
			}
		}
		
		function refresh(opts){
			$("#submitBtn").click();
			if(opts && opts.close == true) {
				$ligerDialog.close();
			}
		}
  </script>
  
  
   </head>
  
  <body style="background-color: #F4FBF4;padding: 10px;">
  <form id="queryform">
  		<div class="searchbar">
<#list table.notKeyCoulmnInfo as column>
			<div class="item-cols">
				<div class="title">${column.label}：</div>
				<div class="content">
		<#if column.javaType == 'Date'>
					<div style="position: relative;">		
	  					<div class="leftDate">
	  						<input type="text" name="lt${column.fieldName ? cap_first}" class="easyui-datebox">
	  					</div>
	  					<label class="dateLabel">至</label>
	  					<div class="rightDate">
	  						<input type="text"  name="gt${column.fieldName ? cap_first}" class="easyui-datebox">
	  					</div>
					</div>
		<#elseif column.javaType == 'boolean' || column.javaType == 'Boolean'>
					<select name="${column.fieldName}" class="easyui-combobox">
						<option value="">所有</option>
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
		<#elseif column.javaType == "Integer" || column.javaType == "int" || column.javaType == "double" || column.javaType=="Double">
					<input type="text" name="${column.fieldName}" class="easyui-validatebox easyui-numberspinner"/>
		<#else>
					<input type="text" name="${column.fieldName}" class="textbox">
	  	</#if>
	  			</div>
	  		</div>	
</#list>		
  			<div class="eventbar">
  				<a id="submitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">搜索</a>
  			</div>
  		</div>
  </form>		
  		<div id="toptoolbar"></div>
   		<div id="datalist"></div>
  </body>
</html>
