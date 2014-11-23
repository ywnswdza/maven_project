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
                { text: '<label style="color: red;">提示:点击列名可进行排序！</label>',id:'show'}
            	]
            });
            
            gridMgr = $("#datalist").ligerGrid({
                columns: [ 
                	 { display : '角色名称',name :'roleName',width : 100},
                	 { display : '角色标识',name :'roleFlag',width : 100},
                	 { display : '角色描述',name :'roleDesc',width : 200},
                	// { display : '是否有效',name :'isEnabled',width : 100,render : function(row){return row.isEnabled ? "是" : "否";}},
	               	 { display: '操作', width: 200,render : function(row) {
	                	var html = "<a href='javascript:itemclick({\"id\":\"permission\",\"key\":\""+ row.roleId +"\"});'>设置权限</a>";
	                	 html += "&nbsp;&nbsp;&nbsp;<a title=\"修改\" href='javascript:linkUsers(\""+ row.roleId +"\");'>关联用户</a>";
	                	 html += "&nbsp;&nbsp;&nbsp;<a title=\"修改\" href='javascript:itemclick({\"id\":\"modify\",\"key\":\""+ row.roleId +"\"});'>修改</a>";
	                	 html += "&nbsp;&nbsp;&nbsp;<a href='javascript:itemclick({\"id\":\"del\",\"key\":\""+ row.roleId +"\"});'>删除</a>";
	                	return html;
	                }}
                ],
            	url: '${ctx }/system/roles/listData.do',
                pageSize: 20,
                sortName: 'roleId',
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
			var height = $(window).height() * 0.8;
			switch(item.id) {
				case "add" : 
					//alert(item.text);
					$ligerDialog = f_openWin("添加信息","${ctx }/system/roles/edit.do",height,width);
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
						ids = rows[0]["roleId"];
					}
					$ligerDialog = f_openWin("修改信息","${ctx }/system/roles/edit.do?id=" + ids,height,width);
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
							ids.push(rows[i]["roleId"]);
						}
					}
					$.ligerDialog.confirm("确认删除所选内容?",function(yes){
						if(!yes) return;
						$.post("${ctx }/system/roles/delete.do",[{name : "ids" ,value : ids.join(",")}],function(data){
							if(data.status == "error") {
								$.ligerDialog.error(data.msg);
							} else {
								refresh();
							}
						},'json');
					});
				break;
				case "permission" : 
					parent.f_openWin("权限设置",
						"${ctx}/system/rolesResources/editPermission.do?key=" + item.key,
						height,//$(window).width() * 0.8,
						500,
						[{ text: '保存', onclick: function (item, dialog) {dialog.frame.savePermission();}}]
					);
				break;
				default :
					
				break;
			}
		}
		
		
		function linkUsers(rolesId){
			var width = 800;//$(document).width() * 0.7;
			var height = $(window).height() * 0.8;
			parent.f_openWin("角色设置",
						"${ctx}/system/userInfoRoles/linkUsers.do?roleId=" + rolesId,
						height,//$(window).width() * 0.8,
						650,
						[{ text: '保存', onclick: function (item, dialog) {dialog.frame.saveRoles();}}]
			);
		}
		
  		function f_openWin(title,url,height,width){
			/* return $.ligerDialog.open({title : title , url: url, height: height, width: width, showMax : true, buttons: [
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
  
  <body style="background-color: #F4FBF4;padding: 10px;">
  <form id="queryform">
  		<div class="searchbar">
	  		<div class="item-cols">
				<div class="title">角色名称：</div>
				<div class="content">
					<input type="text" id="roleName" name="roleName">
	  			</div>
	  		</div>
	  		<div class="item-cols">
	  			<div class="title">角色标识：</div>
				<div class="content">
					<input type="text" id="roleFlag" name="roleFlag">
	  			</div>
	  		</div>
	  		<div class="item-cols">
				<div class="title">是否有效：</div>
				<div class="content">
					<select id="isEnabled" name="isEnabled" ltype="null" style="width: 150px;">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
	  			</div>
	  		</div>
  			<div class="eventbar"><input id="submitBtn" class="l-button"  type="button" value="搜索"></div>
  		</div>
  </form>		
  		<div id="toptoolbar"></div>
   		<div id="datalist"></div>
  </body>
</html>
