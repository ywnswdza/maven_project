<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/js/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${ctx}/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/ligerUI/js/core/base.js"></script>
	<script src="${ctx}/js/lib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerRadio.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerSpinner.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="${ctx}/js/lib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="${ctx}/js/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="${ctx}/js/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

   <script type="text/javascript"> 	
        var eee;
        $(function () {
            $.validator.addMethod( 
            	"notnull", function (value, element, regexp) {
                        if (!value) return true;
                        return !$(element).hasClass("l-text-field-null");
                 },"不能为空"
            );
            $.metadata.setType("attr", "validate");
            var v = $("#form1").validate({
                errorPlacement: function (lable, element)  {
                    if (element.hasClass("l-textarea"))  element.addClass("l-textarea-invalid");
                    else if (element.hasClass("l-text-field")) element.parent().addClass("l-text-invalid");
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", lable.html()).ligerTip();
                },
                success: function (lable){
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea")) element.removeClass("l-textarea-invalid");
                    else if (element.hasClass("l-text-field")) element.parent().removeClass("l-text-invalid");
                    $(element).removeAttr("title").ligerHideTip();
                },
                submitHandler: ajaxSubmit
            });
            $("#form1").ligerForm();
  		});
  		
  		function submitForm (opener) {
  			//$("#content").val(CKEDITOR.instances.content.getData());
  			$("#form1").submit();
  		}
  		
  		function ajaxSubmit(){
  			var $form = $("#form1");
  			$.ajax({
  				type : 'POST',
  				url : $form.attr("action"),
  				dataType : 'json',
  				data : $form.serializeArray(),
  				success : successCall,
  				error : errorCall
  			});
  		}
  		
  		function successCall(data) {
  			var $id = $("#input_id");
  			if($id.val() == ""){
  				$id.val(data.id);
  			}
  			$.ligerDialog.success("操作成功！！！");
  			parent.f_refresh({
  				close : true,
  				treeEdit : true,
  				pId : $("#input_pid").val(),
  				depth : $("#input_depth").val(),
  				nodeText : $("#input_nodeText").val(),
  				id : $("#input_id").val(),
  				isLeaf : $("input[name='isLeaf']:checked").val(),
  				kindId : $("#input_kindId").val()
  			});
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
    </style>
  
   </head>
  
   <body style="background-color: #F4FBF4;padding: 10px;">
  	  <form name="form1" method="post"  id="form1" action="${ctx }/system/commonTree/save.do">
   	  		<input type="hidden" id="input_id" name="id" value="${vo.id}"/>
   	  		<input type="hidden" id="input_pid" name="pId" value="${vo.PId }"/>
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	            	<td align="right" class="l-table-edit-td">节点名称:</td>
	                <td align="left" class="l-table-edit-td"  >
	                		<input value="${vo.nodeText}" name="nodeText" id="input_nodeText" ltype="text" validate="{required:true,maxlength:30,notnull:true}"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	
	            	<td align="right" class="l-table-edit-td">节点标识:</td>
	                <td align="left" class="l-table-edit-td"  >
	                		<input value="${vo.flag}" name="flag" id="input_flag" ltype="text" validate="{required:true,maxlength:30,notnull:true}"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	
	            </tr>
	        	<tr>
	                              
	                <td align="right" class="l-table-edit-td">模块:</td>
	                <td align="left" class="l-table-edit-td"  >
						    <input value="${vo.kindId}" name="kindId" id="input_kindId" ltype="spinner" ligerui="{type: 'int'}" validate="{required:false,maxlength:11,notnull:false}" nullText="不能为空!"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	 
	                <td align="right" class="l-table-edit-td">层级:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
						    <input value="${vo.depth}" name="depth" id="input_depth" ltype="spinner" ligerui="{type: 'int'}" validate="{required:false,maxlength:4,notnull:false}" nullText="不能为空!"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	             
	            </tr>
	            
	        	<tr>
	                <td align="right" class="l-table-edit-td">是否有效:</td>
	                <td align="left" class="l-table-edit-td"  >
	                		 是<input type="radio" ${vo.isValid ? 'checked' : '' } name="isValid" id="input_isValid1" value="true"/>
	                  		 否<input type="radio" ${!vo.isValid ? 'checked' : '' } name="isValid" id="input_isValid2" value="false"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	                <td align="right" class="l-table-edit-td">是否子节点:</td>
	                <td align="left" class="l-table-edit-td"  >
	                		 是<input type="radio" ${vo.isLeaf ? 'checked' : '' } name="isLeaf" id="input_isLeaf1" value="true"/>
	                  		 否<input type="radio" ${!vo.isLeaf ? 'checked' : '' } name="isLeaf" id="input_isLeaf2" value="false"/>
	                </td>
	                <td class="l-table-edit-td" width="20"></td>	              
	            </tr>
	            <tr>
	        		<td align="right" class="l-table-edit-td">优先级:</td>
	                <td align="left" class="l-table-edit-td" style="width:160px" >
						    <input value="${vo.priority}" name="priority" id="input_priority" ltype="spinner" ligerui="{type: 'int'}" validate="{required:false,maxlength:4,notnull:false}" nullText="不能为空!"/>	
	                </td>
	                <td class="l-table-edit-td" width="20"></td>
	            </tr>
				<tr>
					<td align="right" class="l-table-edit-td">描述:</td>
	                <td align="left" class="l-table-edit-td"  colspan="4">
	                	<textarea name="desc" id="input_desc" ltype="textarea" style="width: 98%;" validate="{required:false,maxlength:255,notnull:false}">${vo.desc}</textarea>
	                </td>
	        		<td class="l-table-edit-td" width="20"></td>	
				</tr>
	      	</table>
	      	
  	  </form>
  </body>
</html>
