<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	
	<script src="${ctx}/js/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerGrid.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/plugins/ligerPanel.js"></script>

	<script type="text/javascript">


	</script>

<style type="text/css">
	body {
		font-size: 10pt;
	}
	.l-panel-content {
		overflow: auto;
	}
	.l-grid td div {
		font-size : 15px;
	}
	.l-grid-row-cell {
	     border-right:0px solid #A3C0E8;border-bottom:0px solid #A3C0E8;  
	}
	.l-grid-header {
		background: #ffffff;
	}
	.l-grid-hd-cell{
	    border-right:0px solid #A3C0E8;   
	}
	.l-grid-body{
		overflow:inherit;
	}
</style>

</head>
<body>
	<div style="width:100%;">
   		<div id="panel1-1" style="margin:10px;">
   			<div id="datalist"></div>
   		</div>
   		<div id="panel1-2" style="float:left; margin:10px;">
   			<div id="datalist2"></div> 
   		 </div>
    </div>
</body>
</html>