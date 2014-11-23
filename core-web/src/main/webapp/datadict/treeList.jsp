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
  		var treeObj = {id : 0,depth:1,kindId : -999,addOrUpdate : 'add'};
  	
  		window.onload = function(){
  			
  			$("#layout1").ligerLayout({ leftWidth: 200});
  			$("div[position='left']").css("height","99%");
  			$("#toptoolbar").ligerToolBar({ items: [
                /* { text: '增加', click: itemclick,icon:'add',id:'add'},
                { line:true }, */
                { text: '<label style="color: red;">提示:点击列名可进行排序！</label>',id:'show'}
            	]
            });
            
            gridMgr = $("#datalist").ligerGrid({
                columns: [ 
                	/*  { display : '节点',name :'id',width : 100}, */
                	// { display : '父节点',name :'pid',width : 100},
                	// { display : '层级',name :'depth',width : 100},
                	 { display : 'id',name :'id',width : 80},
                	 { display : '名称',name :'nodeText',width : 100},
                	 { display : '标识',name :'flag',width : 100},
                	 { display : '优先级',name :'priority',width : 80},
                	 <c:if test="${empty param.kindId}">
                	 { display : '模块Id',name :'kindId',width : 100},
                	 { display : '创建时间',name :'createTime',width : 100,render :function (row){if(row.createTime) return new Date(row.createTime).format("yyyy-MM-dd");return ""}}
                	 </c:if>
                	 /* { display : '是否有效',name :'isValid',width : 100,render : function(row){
                	 	return row.isValid ? "是" : "否";
                	 }},
                	 { display : '是否子节点',name :'isLeaf',width : 100,render : function(row){
                	 	return row.isLeaf ? "是" : "否";
                	 }}, */
                	// { display : '描述',name :'desc',width : 100},
                	 
	               	
                ],
            	url: '${ctx }/system/commonTree/listData.do',
            	parms : [
            		{name : 'pId',value : 0},
            		{name : 'kindId',value : '${param.kindId }'}
            	],
                pageSize: 20,
                sortName: 'id',
                width: '100%', height: '99%',
                checkbox: false,
                detail: {height:'auto', onShowDetail: showDetail },
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
            $("#treeFrame").attr("src","${ctx}/datadict/trees.jsp?kindId=${param.kindId}");
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
					$ligerDialog = f_openWin("添加信息","${ctx }/system/commonTree/edit.do",height,width);
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
					$ligerDialog = f_openWin("修改信息","${ctx }/system/commonTree/edit.do?id=" + ids,height,width);
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
						$.post("${ctx }/system/commonTree/delete.do",[{name : "ids" ,value : ids.join(",")}],function(data){
							if(data.status == "error") {
								$.ligerDialog.error(data.msg);
							} else {
								refresh();
							}
						},'json');
					});
				break;
				case "select" :
					var buttons =	[
		                { text: '确定', onclick: function (item, dialog) { 
		                	dialog.frame.confirmSelect();
		                } },
		                { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
		             ]
					parent.f_openWin("选择结果","${ctx}/system/commonTree/selectTree.do?kindId=-999",400,400,buttons);
				break;
				default :
					
				break;
			}
		}
		
  		function f_openWin(title,url,height,width){
// 	/* 		$ligerDialog = $.ligerDialog.open({title : title , url: url, height: height, width: width, showMax : true, buttons: [
//                 { text: '保存', onclick: function (item, dialog) { 
//                 	dialog.frame.submitForm();
//                 } },
//                 { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
//              ], isResize: true
//            }); */
           $ligerDialog = parent.f_openWin(title,url,height,width);
           return $ligerDialog;
		}
		
		function refresh(opts){
			$("#submitBtn").click();
			if(opts && opts.close == true) {
				if($ligerDialog)$ligerDialog.close();
				else $.ligerDialog.close();
			}
			$(document).contents().find("#treeFrame")[0].contentWindow.addNodeSuccess(opts);
		}
		
		function reloadByTreeClick(opts){
			if(opts.pId >=0) $("#pId").val(opts.pId);
			$("#nodeText").val("");
			$("#submitBtn").click();
		}
		
		function selectRowByTreeClick(opts){
			//alert(opts.id);nodeText
			//gridMgr.select([{id : opts.id}]); 
			$("#nodeText").val(opts.nodeText);
			$("#submitBtn").click();
		}
		
		function showDetail(row,detailPanel) {
			var html = "<div style=\"padding:10px 20px;\"><span style=\"font-size:16px;\"> 描述：</span>&nbsp;<br>" + row.desc + "<div>";
			$(html).appendTo(detailPanel);
		}
  </script>

   </head>
  
  <body style="background-color: #F4FBF4;padding: 10px;">
  <div id="layout1">
  	<div position="left" title="树形表">
  		<iframe id="treeFrame" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
  	</div>
  	<div position="center" title="列表">
		  <form id="queryform">
		  		<div class="searchbar">
					<input type="hidden" id="pId" name="pId" value="0">
					<input type="hidden" id="kindId" name="kindId" value="${param.kindId }">
					<div class="item-cols">
						<div class="title">名称：</div>
						<div class="content">
							<input type="text" id="nodeText" name="nodeText">
			  			</div>
			  		</div>
			  		<div class="item-cols">
						<div class="title">标识：</div>
						<div class="content">
							<input type="text" id="flag" name="flag">
			  			</div>
			  		</div>
<!-- 				<div class="title">是否有效：</div>
					<div class="content">
						<select id="isValid" ltype="null" style="width: 180px;" name="isValid">
							<option value="">所有</option>
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
		  			</div> -->
					
	<!-- 				<div class="title">更新时间：</div>
					<div class="content">
						<div style="position: relative;top: 10px;">		
		  					<div style="float: left;"><input type="text" id="ltUpdateTime" ltype="date" name="ltUpdateTime"></div>
		  					<label style="padding: 0px 25px 0px 25px;float: left;">至</label>
		  					<div style="margin:auto 0px auto 248px;"><input type="text" id="gtUpdateTime" ltype="date" name="gtUpdateTime"></div>
						</div>
		  			</div> -->
		<!-- 			<div class="title">创建时间：</div>
					<div class="content">
						<div style="position: relative;top: 10px;">		
		  					<div style="float: left;"><input type="text" id="ltCreateTime" ltype="date" name="ltCreateTime"></div>
		  					<label style="padding: 0px 25px 0px 25px;float: left;">至</label>
		  					<div style="margin:auto 0px auto 248px;"><input type="text" id="gtCreateTime" ltype="date" name="gtCreateTime"></div>
						</div>
		  			</div> -->
		  			<div class="eventbar"><input id="submitBtn" class="l-button" type="button" value="搜索"></div>
		  		</div>
		  </form>		
		  		<div id="toptoolbar"></div>
		   		<div id="datalist"></div>
   		</div>
   	</div>
  </body>
</html>
