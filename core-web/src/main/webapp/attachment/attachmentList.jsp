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
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerResizable.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerButton.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerGrid.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerToolBar.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerDateEditor.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerTextBox.js"></script>
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
                 { text: '保存', click: itemclick,icon:'save',id:'save'},
                { line:true },
                { text: '<label style="color: red;">提示:点击列名可进行排序！</label>',id:'show'}
            	]
            });
            
            gridMgr = $("#datalist").ligerGrid({
                columns: [ 
                	 { display : '真实名',name :'fileRealName',width : 100},
                	 { display : '文件名',name :'fileName',width : 100},
                	 { display : '路径',name :'fileFolder',width : 100},
                	 { display : '文件大小',name :'fileSize',width : 100 ,render : function(row){
                	 	return row.simpleFileSize;
                	 }},
                	 { display : '上传者',name :'username',width : 100,render : function (row) {
                	 	if(row.uploadUser && row.uploadUser.username) return row.uploadUser.username;
                	 	return '';
                	 }},
                	 { display : '所属组',name :'groupId',width : 100},
                	 { display : '创建时间',name :'tca.createTime',width : 100,render :function (row){if(row.addTime) return new Date(row.addTime).format("yyyy-MM-dd");return ""}},
	               	 { display: '操作', width: 200,render : function(row) {
	               	 	var html = "<a title=\"下载\" href='javascript:itemclick({\"id\":\"save\",\"key\":\""+ row.id +"\"});'>";
	                	html += "<img src=\"${ctx}/js/lib/ligerUI/skins/icons/save.gif\"/></a>";
	                	html += "&nbsp;&nbsp;&nbsp;<a title=\"修改\" href='javascript:itemclick({\"id\":\"modify\",\"key\":\""+ row.id +"\"});'>";
	                	html += "<img src=\"${ctx}/js/lib/ligerUI/skins/icons/modify.gif\"/></a>";
	                	 html += "&nbsp;&nbsp;&nbsp;<a href='javascript:itemclick({\"id\":\"del\",\"key\":\""+ row.id +"\"});'>";
	                	 html += "<img title=\"删除\" src=\"${ctx}/js/lib/ligerUI/skins/icons/delete.gif\"/></a>";
	                	return html;
	                }}
                ],
            	url: '${ctx }/system/attachment/listData.do',
                pageSize: 20,
                sortName: 'id',
                width: '100%', height: '99%',
                checkbox: true,
                rownumbers:true,
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
					$ligerDialog = f_openWin("添加信息","${ctx }/attachment/attachmentExample.jsp",height,width);
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
						ids = rows[0]["id"];
					}
					$ligerDialog = f_openWin("修改信息","${ctx }/system/attachment/edit.do?id=" + ids,height,width);
				break;
				
				case "save" : 
					var ids = [];
					var url = "";
					if(item.key) {
						url = "${ctx }/system/attachment/download.do?id=" + item.key;
					} else {
						var rows = gridMgr.getSelecteds();
						if(rows.length == 0) {
							$.ligerDialog.error("请选择一行进行修改！");
							return;
						} 
						for(var i = 0; i < rows.length ; i++) {
							ids.push(rows[i]["id"]);
						}
						url = "${ctx }/system/attachment/downLoadByIds.do?ids=" + ids.join(",");
					}
					window.open(url,"_blank");
					break;
				case "del" : 
					if('${sessionScope.user.isSupperUser}' != 'true') return;
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
							ids.push(rows[i]["id"]);
						}
					}
					$.ligerDialog.confirm("确认删除所选内容?",function(yes){
						if(!yes) return;
						$.post("${ctx }/system/attachment/delete.do",[{name : "ids" ,value : ids.join(",")}],function(data){
							if(data.status == "error") {
								$.ligerDialog.error(data.msg);
							} else {
								refresh();
							}
						},'json');
					});
				break;
				
				default :
					
				break;
			}
		}
		
  		function f_openWin(title,url,height,width){
			return $.ligerDialog.open({title : title , url: url, height: height, width: width, showMax : true, buttons: [
                { text: '保存', onclick: function (item, dialog) { 
                	dialog.frame.submitForm();
                } },
                { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
             ], isResize: true
           });
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
				<div class="title">文件名：</div>
				<div class="content">
					<input type="text" id="fileRealName" name="fileRealName">
	  			</div>
	  		</div>
	  		<div class="item-cols">
		  		<div class="title">上传者：</div>
				<div class="content">
					<input type="text" id="uploadName" name="uploadUser.username">
	  			</div>
  			</div>
	  		<div class="item-cols">
	  			<div class="title">路径：</div>
				<div class="content">
					<input type="text" id="fileFolder" name="fileFolder">
	  			</div>
	  		</div>
<!-- 			<div class="title">文件名：</div>
			<div class="content">
				<input type="text" id="fileName" name="fileName">
  			</div> -->
<!-- 		 -->
<!-- 			<div class="title">文件大小：</div>
			<div class="content">
				<input type="text" id="fileSize" name="fileSize">
  			</div><br> -->
<!-- 			<div class="title">上传者：</div>
			<div class="content">
				<input type="text" id="uploadName" name="uploadName">
  			</div> -->
  			
	  		<div class="item-cols">
				<div class="title">所属组：</div>
				<div class="content">
					<input type="text" id="groupId" name="groupId">
	  			</div>
	  		</div>
	  		<div class="item-cols">
				<div class="title">创建时间：</div>
				<div class="content">
					<div style="position: relative;">			
	  					<div class="leftDate"><input type="text" id="ltCreateTime" ltype="date" name="ltCreateTime"></div>
	  					<label class="dateLabel">至</label>
	  					<div class="rightDate"><input type="text" id="gtCreateTime" ltype="date" name="gtCreateTime"></div>
					</div>
	  			</div>
	  		</div>
  			<div class="eventbar"><input id="submitBtn" class="l-button" type="button" value="搜索"></div>
  		</div>
  </form>		
  		<div id="toptoolbar"></div>
   		<div id="datalist"></div>
  </body>
</html>
