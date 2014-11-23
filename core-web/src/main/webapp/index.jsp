<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/fn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/su.jsp" %>

<link href="${ctx}/js/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" id="mylink" />
<script src="${ctx}/js/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="${ctx}/js/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="${ctx}/js/lib/jquery.cookie.js"></script>
<script src="${ctx}/js/lib/json2.js"></script>
<script type="text/javascript" src="${ctx }/js/common.js"></script>

<script type="text/javascript">


if(window.self != window.top) {
			window.top.location.href = self.location.href;
		}
		
            var tabChild = null;
            var accordion = null;
             var height = 0;
			var childMenus = null;
            $(function () {
                //布局
                $("#layout1").ligerLayout({ 
                	leftWidth: 150, 
                	height: '100%',
                	heightDiff:-34,
                	space:4, 
                	onHeightChanged: f_heightChanged 
                });
               height = $(".l-layout-center").height();
                //Tab
                $("#framecenter").ligerTab({
                	 showSwitchInTab : true,
                     showSwitch: true,
                     contextmenu : true,
                      height: height
                });
                //面板
                $("#accordion1").ligerAccordion({ height: height - 20, speed: null });
                link_over();
                tabChild = liger.get("framecenter");
                accordion = liger.get("accordion1");
                $("#pageloading").hide();
                
                childMenus = eval("("+ $("#childMenu").html() +")");
                
                //tabParent.selectTabItem('${fisrtId}');
                $("#systemName").change(function(){
                	var sel = $(this).find("option:selected");
                	loadChildMenu(sel.val(),sel.html());
                });
                
                loadChildMenu('${fisrt.id}','${fisrt.name}');
            });
            
         /** 调整面板和tab的高度 **/
         function f_heightChanged(options) {  
             if (tabChild)  tabChild.addHeight(options.diff);
             if (accordion && options.middleHeight - 24 > 0) accordion.setHeight(options.middleHeight - 24);
         }
            
         function f_addTab(tabid, text, url,reload){
         	if(text != '修改密码') {
         		 if(reload && reload == true) tabChild.removeTabItem(tabid);
	             tabChild.addTabItem({
	                 tabid: tabid,
	                 text: text,
	                 url: url                 
	             });
             } else {
             	f_openWin(text,url,400,500);
             }
         }
            
   		
   		function link_over(){
   			$(".l-link").hover(function (){
                $(this).addClass("l-link-over");
            }, function (){
                $(this).removeClass("l-link-over");
            });
   		}
   		
   		var $ligerDialog;
   		function f_openWin(title,url,height,width,_buttons){
   			var buttons = [{ text: '关闭', onclick: function (item, dialog) { dialog.close(); }}];
            if(_buttons) {
            	for(var i = _buttons.length -1; i >= 0 ; i--) {
            		buttons.unshift(_buttons[i]);
            	} 
            } else {
            	buttons.unshift({ text: '保存', onclick: function (item, dialog) {
            			dialog.frame.submitForm();
            	}});
            }
			$ligerDialog = $.ligerDialog.open({title : title , url: url, height: height, width: width, showMax : true, buttons: buttons, isResize: true
           });
           return $ligerDialog;
		}
		
		
		function f_refresh(opts){
			var tabid = null;
			if(!opts || !opts.tabid) 
				tabid = tabChild.getSelectedTabItemID();
			else 
				tabid = opts.tabid;
			if(opts && opts.close == true && $ligerDialog) {
				$ligerDialog.close();
			}
			$(document).contents().find("#" + tabid)[0].contentWindow.refresh(opts);
		}		
   		
   		
   		function loadChildMenu(id,title){
   			//tabChild.removeOther("home");
   			var menu = getTargetMenuByTabId(id,title);
   			accordion.destroy();
   			$("#layout1 div.l-layout-left").append("<div id=\"accordion1\"></div>");
   			var html = "";
   			for(var i = 0 ;i < menu.length ;i++) {
   				var topM = menu[i];
   				html += "<div title=\"" + topM.name + "\"><div style=\"height:7px;\"></div>";
   				if(topM.children && topM.children.length > 0) {
   					var childMs = topM.children;
   					for(var j = 0; j < childMs.length;j++) {
   						var childM = childMs[j];
   						var url = childM.linkUrl;
   						if(!url.startWith("/")) url = "${ctx}/" + url;
   						/* if(childM.name && childM.name == '引入产品列表') 
   							html += "<a class=\"l-link\" href=\"javascript:f_addTab('"+ childM.id +"','"+childM.name +"','"+url+"',true)\">" + childM.name + "</a>";
   						else  */
   							html += "<a class=\"l-link\" href=\"javascript:f_addTab('"+ childM.id +"','"+childM.name +"','"+url+"',"+ childM.isRefresh +")\">" + childM.name + "</a>";
   					}
   				}
   				html += "</div>";
   			}
   			$("#accordion1").html(html);
   			accordion = $("#accordion1").ligerAccordion({ height: height - 24, speed: null });
   			link_over();
   		}
   			
   		var treeMenu = [];	
   		var cacheMenu = {};
   		function getTargetMenuByTabId(tabId,title) {
   			if(!cacheMenu[tabId]) {
	   			if(treeMenu.length == 0) treeMenu = arrayToTree(childMenus,"id","parentId");
	   			var targetData = [];
	   			var rootData = {id : tabId,name : title,children :[]};
	   			for(var i = 0;i < treeMenu.length;i++) {
	   				var links = treeMenu[i];
	   				if(links.parentId == tabId) {
	   					if(links.type == 2) {
	   						rootData.children.push(links);
	   					} else {
	   						targetData.push(links);
	   					}
	   				}
	   			}
	   			if(rootData.children.length > 0) targetData.push(rootData);
	   			cacheMenu[tabId] = targetData;
	   			//console.info("pid " + tabId + ">>>>>>>>>>>>>>>>>>>>> init menu");
   			}
   			return cacheMenu[tabId];
   		}
   		
   		//将ID、ParentID这种数据格式转换为树格式
   		function arrayToTree (data, id, pid) {
            if (!data || !data.length) return [];
            var targetData = [];                    //存储数据的容器(返回) 
            var records = {};
            var itemLength = data.length;           //数据集合的个数
            for (var i = 0; i < itemLength; i++) {
                var o = data[i];
                records[o[id]] = o;
            }
            for (var i = 0; i < itemLength; i++) {
                var currentData = data[i];
                var parentData = records[currentData[pid]];
                if (!parentData)
                {
                    targetData.push(currentData);
                    continue;
                }
                parentData.children = parentData.children || [];
                parentData.children.push(currentData);
            }
            return targetData;
        }
   		
     </script>


</head>

<body style="padding:0px;background:#EAEEF5;">
	<div id="pageloading"></div>
	<div id="topmenu" class="l-topmenu">
		
			<div class="l-topmenu-logo">Losy--管理平台</div>
			
			<div class="l-topmenu-welcome">
				<%-- <select id="systemName">
					<c:forEach var="menu" items="${pmenus }" varStatus="_index">
						<option value="${menu.id }">${menu.name }</option>
					</c:forEach>
				</select> --%>
			<span class="space">欢迎你，</span><span
				style="color:#FFFFFF;font-size:14px;font-family:'微软雅黑';">${sessionScope.user.username }
				&nbsp;|&nbsp;<a
				href="javascript:f_addTab('110','修改密码','${ctx }/userinfo/resetPassword.jsp')" class="space">修改密码</a>&nbsp;|&nbsp;<a
				href="${ctx }/logout.do" class="space"
				style="color:#FFFFFF;font-size:14px;font-family:'微软雅黑';">安全退出</a>
			</span>
		</div>
	</div>
	<div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px;">
		<div position="left" title="主要菜单" id="accordion1">		
			<div title="系统管理">
				<div style=" height:7px;"></div>
				<a class="l-link" href="javascript:f_addTab('resource','菜单管理','${ctx }/system/resources/showList.do')">菜单管理</a>
				<a class="l-link" href="javascript:f_addTab('resource1','菜单管理1','${ctx }/system/userInfo/showList.do')">菜单管理1</a>
			</div>
		</div>

		<div position="center" id="framecenter">
			<div tabid="home" title="我的主页" style="height:300px">
				<iframe frameborder="0" name="home" id="home" src="${ctx }/home.jsp"></iframe>
			</div>
		</div>
		
	</div>

	<div style="height:32px; line-height:32px; text-align:center;">
		
	</div>
	<div style="display:none"></div>
	<span style="display: none;" id="childMenu">${childMenu }</span>
</body>
</html>

