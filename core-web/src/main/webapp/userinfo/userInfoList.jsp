<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }/css/searchbar2.css" rel="stylesheet" type="text/css" />
	
	<script src="${ctx}/js/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerCheckBox.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerButton.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerGrid.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerToolBar.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerDateEditor.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerTextBox.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerResizable.js"></script>
	<script src="${ctx}/js/lib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
	<script src="${ctx}/js/lib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
	<script src="${ctx}/js/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	
	
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
                  { text: '重置密码', click: itemclick,icon :'refresh',id:'resetPwd' },
                 { line:true },
                { text: '<label style="color: red;">提示:点击列名可进行排序！</label>',id:'show'}
            	]
            });
            
            gridMgr = $("#datalist").ligerGrid({
                columns: [ 
                	 { display : '登录帐号',name :'userAccount',width : 100},
                	// { display : '用户密码',name :'password',width : 100},
                	 { display : '用户名称',name :'username',width : 100},
                	// { display : '是否有效',name :'isEnabled',width : 100},
                	 { display : '是否超级用户',name :'isSupperUser',width : 100},
                	 { display : '用户描述 ',name :'userDesc',width : 100},
                	 { display : '更新时间',name :'updateTime',width : 100,render :function (row){if(row.updateTime) return new Date(row.updateTime).format("yyyy-MM-dd");return ""}},
                	 { display : '创建时间',name :'createTime',width : 100,render :function (row){if(row.createTime) return new Date(row.createTime).format("yyyy-MM-dd");return ""}},
	               	 { display: '操作', width: 150,render : function(row) {
	               	 
	               	 	var html = "<a href='javascript:itemclick({\"id\":\"setRoles\",\"key\":\""+ row.userId +"\"});'>角色信息</a>";
	                	 /* html += "<img title=\"设置角色\" src=\"${ctx}/js/lib/ligerUI/skins/icons/settings.gif\"/></a>"; */
	                	html += "&nbsp;&nbsp;&nbsp;<a title=\"修改\" href='javascript:itemclick({\"id\":\"modify\",\"key\":\""+ row.userId +"\"});'>修改</a>";
	                	/* html += "<img src=\"${ctx}/js/lib/ligerUI/skins/icons/modify.gif\"/></a>"; */
	                	 html += "&nbsp;&nbsp;&nbsp;<a href='javascript:itemclick({\"id\":\"del\",\"key\":\""+ row.userId +"\"});'>删除</a>";
	                	 /* html += "<img title=\"删除\" src=\"${ctx}/js/lib/ligerUI/skins/icons/delete.gif\"/></a>"; */
	                	return html;
	                }}
                ],
            	url: '${ctx }/system/userInfo/listData.do',
                pageSize: 20,
                sortName: 'userId',
                width: '100%', height: '99%',
                checkbox: true,rownumbers:true,selectRowButtonOnly : true,
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
            $("#queryform").ligerForm({inputWidth : 150});
  		}
  		
  		var $ligerDialog = null;
		function  itemclick (item){
			//alert(item.text);
			if(!item) return;
			var width = 800;//$(document).width() * 0.7;
			var height = 600;//$(document).height() * 0.9;
			switch(item.id) {
				case "add" : 
					//alert(item.text);
					$ligerDialog = f_openWin("添加信息","${ctx }/system/userInfo/edit.do",height,width);
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
						ids = rows[0]["userId"];
					}
					$ligerDialog = f_openWin("修改信息","${ctx }/system/userInfo/edit.do?id=" + ids,height,width);
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
							ids.push(rows[i]["userId"]);
						}
					}
					$.ligerDialog.confirm("确认删除所选内容?",function(yes){
						if(!yes) return;
						$.myAjax({
							url : "${ctx }/system/userInfo/delete.do",
							data : {ids : ids.join(",")},
							ajaxSuccess:function(data){
								refresh();
							}
						});
					});
				break;
				case "setRoles" : 
					parent.f_openWin("角色设置",
						"${ctx}/system/userInfoRoles/edit.do?userId=" + item.key,
						height,//$(window).width() * 0.8,
						650,
						[{ text: '保存', onclick: function (item, dialog) {dialog.frame.saveRoles();}}]
					);
				break;
				case "resetPwd" : 
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
							ids.push(rows[i]["userId"]);
						}
					}
					$.myAjax({
						url : "${ctx }/system/userInfo/resetPwd.do",
						data : {ids : ids.join(",")},
						ajaxSuccess:function(data){
							refresh();
						}
					});
				break;
				default :
					
					
				break;
			}
		}
		
  		function f_openWin(title,url,height,width){
	/* 		return $.ligerDialog.open({title : title , url: url, height: height, width: width, showMax : true, buttons: [
                { text: '保存', onclick: function (item, dialog) { 
                	dialog.frame.submitForm();
                } },
                { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
             ], isResize: true
           }); */
           return parent.f_openWin(title,url,height,width);
		}
		
		function refresh(opts){
			$("#submitBtn").click();
			if(opts && opts.close == true) {
				$ligerDialog.close();
			}
		}
  </script>
  
  
   </head>
  
  <body style="background-color: #F4FBF4;">
  <div style="width: 97%;margin: auto;height: 90%;">
  <form id="queryform">
  		<div class="searchbar">
  			<div class="item-cols">
				<div class="title">登录帐号：</div>
				<div class="content">
					<input type="text" id="userAccount" name="userAccount">
	  			</div>	
	  		</div>
	  		<div class="item-cols">
				<div class="title">用户名称：</div>
				<div class="content">
					<input type="text" id="username" name="username">
	  			</div>
	  		</div>
	  		<div class="item-cols">
				<div class="title">是否有效：</div>
				<div class="content">
					<select id="isEnabled" name="isEnabled" ltype="null">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
	  			</div>	
	  		</div>
	<!-- 		<div class="title">更新时间：</div>
			<div class="content">
				<div style="position: relative;top: 10px;">		
  					<div style="float: left;"><input type="text" id="ltUpdateTime" ltype="date" name="ltUpdateTime"></div>
  					<label style="padding: 0px 25px 0px 25px;float: left;">至</label>
  					<div style="margin:auto 0px auto 248px;"><input type="text" id="gtUpdateTime" ltype="date" name="gtUpdateTime"></div>
				</div>
  			</div><br>
			<div class="title">创建时间：</div>
			<div class="content">
				<div style="position: relative;top: 10px;">		
  					<div style="float: left;"><input type="text" id="ltCreateTime" ltype="date" name="ltCreateTime"></div>
  					<label style="padding: 0px 25px 0px 25px;float: left;">至</label>
  					<div style="margin:auto 0px auto 248px;"><input type="text" id="gtCreateTime" ltype="date" name="gtCreateTime"></div>
				</div>
  			</div> -->
  			<div class="eventbar"><input id="submitBtn"  class="l-button" type="button" value="搜索"></div>
  		</div>
  </form>		
  		<div id="toptoolbar"></div>
   		<div id="datalist"></div>
   	</div>
  </body>
</html>
