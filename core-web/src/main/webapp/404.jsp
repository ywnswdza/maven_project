<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<title>404页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
body{background: url(${pageContext.request.contextPath}/images/404-hui_02.png) repeat;}
*{ margin:0; padding:0;}
table{ margin:auto;  font-family:"微软雅黑"; font-size:14px;}
.hongwz{ color:#ff1111;}
.lvwz{ color:#1e790f; text-decoration: none;}
.lvwz a{ color:#1e790f;}
.lvwz a:hover{ color:#ff1111;}
</style>
<script type="text/javascript">
	window.onload = function(){
		setTimeout(goIndex,1000);
	}
	function goIndex(){
		var times = document.getElementById("time").innerHTML;
		if(times > 1) {
			document.getElementById("time").innerHTML = parseInt(times) - 1;
			setTimeout(goIndex,1000);
		} else {
			// dreamGames/index.htm
			window.location.href = "${pageContext.request.contextPath}/index.do";
		}
	}
</script>
</head>
<body>
<table align="center" height="611" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="3">
			<img src="${pageContext.request.contextPath}/images/404_01.png" width="1003" height="180" alt=""></td>
	</tr>
	<tr>
		<td colspan="3">
			<img src="${pageContext.request.contextPath}/images/404_02.png" width="1003" height="141" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="${pageContext.request.contextPath}/images/404_03.png" width="416" height="94" alt=""></td>
		<td style="background:url(${pageContext.request.contextPath}/images/404_04.png) repeat; width:427px; height:94px;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr style="line-height:22px;">
    <td>你访问的页面网址可能已被删、名称已被更改，或者暂时不可用。</td>
  </tr>
  <tr style="line-height:22px;">
    <td class="hongwz"><span id="time">5</span>秒后系统会自动转入首页</td>
  </tr>
  <tr style="line-height:22px;">
    <td>点击返回<span class="lvwz"><a href="${pageContext.request.contextPath}/index.do" class="lvwz">网站首页</a></span>或<span class="lvwz"><a href="javascript:history.go(-1);" class="lvwz">返回上一页面</a></span></td>
  </tr>
</table>

        </td>
		<td>
			<img src="${pageContext.request.contextPath}/images/404_05.png" width="160" height="94" alt=""></td>
	</tr>
	<tr>
		<td colspan="3">
			<img src="${pageContext.request.contextPath}/images/404_06.png" width="1003" height="197" alt=""></td>
	</tr>
</table>
<div style="background:url(${pageContext.request.contextPath}/images/404_07.jpg) repeat-x; height:156px;"></div>
</body>
</html>