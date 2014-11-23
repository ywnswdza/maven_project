<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }/css/searchbar2.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerCheckBox.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerButton.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerGrid.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerToolBar.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerDateEditor.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerTextBox.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerLayout.js"></script>
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
  		
  			$("#layout1").ligerLayout({ leftWidth: 250});
  			$("div[position='left']").height($("div[position='left']").parent().height());
  		
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
                	 { display : '资源名称',name :'name',width : 100},
                	 { display : '资源类型',name :'type',width : 100},
                	 { display : '资源链接',name :'linkUrl',width : 100},
                	 { display : '优先级',name :'priority',width : 100},
                	 { display : '是否有效',name :'isEnabled',width : 100},
                	 { display : '父级目录',name :'parentId',width : 100},
                	 { display : '描述',name :'desc',width : 100},
                	 { display : '创建时间',name :'updateTime',width : 100,render :function (row){if(row.updateTime) return new Date(row.updateTime).format("yyyy-MM-dd");return ""}},
                	 { display : '更新时间',name :'createTime',width : 100,render :function (row){if(row.createTime) return new Date(row.createTime).format("yyyy-MM-dd");return ""}},
	               	 { display: '操作', width: 200,render : function(row) {
	                	var html = "<a title=\"修改\" href='javascript:itemclick({\"id\":\"modify\",\"key\":\""+ row.id +"\"});'>";
	                	html += "<img src=\"${ctx}/js/lib/ligerUI/skins/icons/modify.gif\"/></a>";
	                	 html += "&nbsp;&nbsp;&nbsp;<a href='javascript:itemclick({\"id\":\"del\",\"key\":\""+ row.id +"\"});'>";
	                	 html += "<img title=\"删除\" src=\"${ctx}/js/lib/ligerUI/skins/icons/delete.gif\"/></a>";
	                	return html;
	                }}
                ],
            	url: '${ctx }/system/resources/listData.do',
                pageSize: 20,
                sortName: 'id',
                width: '100%', height: '99%',
                checkbox: true,rownumbers:false,
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
            $("#queryform").ligerForm();
            $("#treeFrame").attr("src","${ctx}/commontree/trees.jsp");
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
					$ligerDialog = f_openWin("添加信息","${ctx }/system/resources/edit.do",height,width);
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
					$ligerDialog = f_openWin("修改信息","${ctx }/system/resources/edit.do?id=" + ids,height,width);
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
							ids.push(rows[i]["id"]);
						}
					}
					$.ligerDialog.confirm("确认删除所选内容?",function(yes){
						if(!yes) return;
						$.post("${ctx }/system/resources/delete.do",[{name : "ids" ,value : ids.join(",")}],function(data){
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
<div id="layout1">
	<div position="left" title="菜单管理 ">
		<iframe id="treeFrame" scrolling="auto" frameborder="0" width="100%" height="100%"></iframe>
	</div>
	<div position="center" title="菜单列表">
	  	<form id="queryform">
	  		<div class="searchbar">
				<div class="title">资源名称：</div>
				<div class="content">
					<input type="text" id="name" name="name">
	  			</div>
				<div class="title">资源类型：</div>
				<div class="content">
					<input type="text" id="type" name="type">
	  			</div>
				<div class="title">资源链接：</div>
				<div class="content">
					<input type="text" id="linkUrl" name="linkUrl">
	  			</div>
				<div class="title">优先级：</div>
				<div class="content">
					<input type="text" id="priority" name="priority">
	  			</div><br>
				<div class="title">是否有效：</div>
				<div class="content">
					<input type="text" id="isEnabled" name="isEnabled">
	  			</div>
				<div class="title">父级目录：</div>
				<div class="content">
					<input type="text" id="parentId" name="parentId">
	  			</div>
				<div class="title">描述：</div>
				<div class="content">
					<input type="text" id="desc" name="desc">
	  			</div>
				<div class="title">创建时间：</div>
				<div class="content">
					<div style="position: relative;top: 10px;">		
	  					<div style="float: left;"><input type="text" id="ltUpdateTime" ltype="date"></div>
	  					<label style="padding: 0px 25px 0px 25px;float: left;">至</label>
	  					<div style="margin:auto 0px auto 248px;"><input type="text" id="gtUpdateTime" ltype="date"></div>
					</div>
	  			</div><br>
				<div class="title">更新时间：</div>
				<div class="content">
					<div style="position: relative;top: 10px;">		
	  					<div style="float: left;"><input type="text" id="ltCreateTime" ltype="date"></div>
	  					<label style="padding: 0px 25px 0px 25px;float: left;">至</label>
	  					<div style="margin:auto 0px auto 248px;"><input type="text" id="gtCreateTime" ltype="date"></div>
					</div>
	  			</div>
	  			<div class="eventbar"><input id="submitBtn" type="button" value="搜索"></div>
	  		</div>
	  </form>		
  		<div id="toptoolbar"></div>
   		<div id="datalist"></div>
  </div> 
  </body>
</html>
