<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerListBox.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

   <script type="text/javascript"> 	
  
        $(function () {
      /*     	$("#listbox1,#listbox2").ligerListBox({
                isShowCheckBox: true,
                isMultiSelect: true,
                valueField : "roleId",
                textField : "roleName",
                height: 400
            });
            box2 = liger.get("listbox2");
            box2.setData(eval("("+ $("#roleData").html() +")"));
            var hasRole = eval("("+ $("#hasRoleList").html() +")");
            if(hasRole && hasRole.length > 0) liger.get("listbox1").setData(hasRole); */
            var listOps = {
        		isShowCheckBox: true,
                isMultiSelect: true,
                valueField : "roleId",
                textField : "roleName",
                height: 400
        	};
          	$("#listbox1").ligerListBox($.extend({},listOps,{
          		onSelected : function(index,text,row,status){
          			//alert($(this).attr("id"));
          			if(status == false) {
          				liger.get("listbox2").unSelectByValue(row);
          				liger.get("listbox1").removeItemByValue(row.roleId);
          				liger.get("listbox1").selectAll();
          			}
          		}
          	}));
          	$("#listbox2").ligerListBox($.extend({},listOps,{
          		onSelected : function(index,text,row,status){
          			if(status == true && !liger.get("listbox1").hasItemByValue(row.roleId)) {
          				liger.get("listbox1").addItem(row);
          				//liger.get("listbox1").selectAll();
          			} else {
          				liger.get("listbox1").removeItemByValue(row.roleId);
          			}
          			liger.get("listbox1").selectAll();
          		}
          	}));
          
            box2 = liger.get("listbox2");
            box2.setData(eval("("+ $("#roleData").html() +")"));
            var hasRole = eval("("+ $("#hasRoleList").html() +")");
            if(hasRole && hasRole.length > 0) {
            	liger.get("listbox1").setData(hasRole);
            	liger.get("listbox1").selectAll();
            	box2.selectItems(hasRole);
            }
  		});
  		
  		function moveToLeft()
        {
            var box1 = liger.get("listbox1"),box2 = liger.get("listbox2");
            var selecteds = box2.getSelectedItems();
            if (!selecteds || !selecteds.length) return;
            if(box1.data == null || box1.data.length == 0) {
            	box1.addItems(selecteds);
            } else {
            	var targetData = [];
	            for(var i = 0 ;  i <  selecteds.length ;i++) {
	            	if(box1.getDataByValue(selecteds[i].roleId) == null) targetData.push(selecteds[i]);
	            }
	            box1.addItems(targetData);
            }
        }
        function moveToRight()
        {
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box1.getSelectedItems();
            if (!selecteds || !selecteds.length) return;
            box1.removeItems(selecteds);
        }
        function moveAllToLeft()
        { 
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box2.data;
            if (!selecteds || !selecteds.length) return;
            if(box1.data == null || box1.data.length == 0) {
            	box1.addItems(selecteds);
            } else {
            	var targetData = [];
	            for(var i = 0 ; i <  selecteds.length ;i++) {
	            	if(box1.getDataByValue(selecteds[i].roleId) == null) targetData.push(selecteds[i]);
	            }
	            box1.addItems(targetData);
            }
        }
        
        function moveAllToRight()
        { 
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box1.data;
            if (!selecteds || !selecteds.length) return;
            box1.removeItems(selecteds);
           
        }
        
        function submitForm(){
        	var data = liger.get("listbox1").data;
        	var rolesId = [];
        	for(var i = 0 ;data && i < data.length;i++) {
        		rolesId.push(data[i].roleId);
        	}
        	$.myAjax({
  				type : 'POST',
  				url : '${ctx}/system/rolesResources/updateRRMapping.do',
  				data : {
  					roleIds : rolesId.join(","),
  					key : '${res.id}'
  				},
  				success : function(data){
  					$.ligerDialog.alert(data,function(){
  						parent.f_refresh({close:true});
  					});
  				}
  			});
        }
        
        
  	function searchBox2() {
  		liger.get("listbox2").searchByText(this.value);
  	}	
  	
  	function searchBox1() {
  		liger.get("listbox1").searchByText(this.value);
  	}	
  </script>
   <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .l-table-edit-td select,.l-table-edit-td input {
        	width: 180px;
        }
        .middle input {
            display: block;width:60px; margin:2px;
        }
        .l-listbox {
        	padding: 5px;
        }
    </style>
  
   </head>
  
   <body style="background-color: #F4FBF4;padding: 10px;">
  	 <span style="font-size:12pt;font-weight: normal;padding: 5px auto;">当前操作  >> 访问限制  >> 资源名称  [&nbsp;<span style="font-weight: bolder;">${res.name }</span>&nbsp;]</span>
	 <div style="padding: 10px;width: 80%;margin: auto;">
	  	 <div style="margin:4px;float:left;">
	  	 	 <div>允许访问[&nbsp;<span style="font-weight: bolder;">${res.name }</span>&nbsp;]的角色</div>
	          <input type="text" style="width: 160px;border: 1px solid #aecaf0;margin-bottom: 1px;" oninput="searchBox1.call(this)" onpropertychange="searchBox1.call(this)" value=""/>
	         <div id="listbox1"></div>  
	     </div>
	     <div style="margin:4px;vertical-align: middle;padding-top: 150px;float:left;width: 50px;" class="middle">
	       <!--   <input type="button" onclick="moveToLeft()" value="添加所选" />
	          <input type="button" onclick="moveToRight()" value="删除所选" />
	          <input type="button" onclick="moveAllToLeft()" value="添加所有" />
	         <input type="button" onclick="moveAllToRight()" value="删除所有" /> -->
	     </div>
	     <div style="margin:4px;float:left;">
	     	<div>系统有效角色</div>
	         <input type="text" style="width: 160px;border: 1px solid #aecaf0;margin-bottom: 1px;" oninput="searchBox2.call(this)" onpropertychange="searchBox2.call(this)" value=""/>
	        <div id="listbox2"></div> 
	     </div>
	 </div>
	 
	 <span id="roleData" style="display: none;">${roleList }</span>
	 <span id="hasRoleList" style="display: none;">${hasRoleList }</span>
  </body>
</html>
