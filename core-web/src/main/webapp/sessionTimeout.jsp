<%@ page contentType="text/html;charset=gbk"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=gbk"/>
    <title>�û���¼�ѳ�ʱ</title>
    <style type="text/css">
	    body{
			text-align: center;
		}
		
		#sessionOut {
            margin-top: 50px;
			padding: 15px 50px;
			width: 500px;
			border: 2px solid green;
			background-color: yellow;
			text-align: center;
		}
		
		a{
			font-weight:bold;
			font-family:"����";
			font-size:18px;
		}

    </style>
  </head>

<body>

	<div id ="sessionOut">
		����ʱ��δ����ϵͳ��Ϊȷ���������ϼ�������Ϣ��ȫ��
		ϵͳ�Զ���ʱ�˳���������<a href="http://<%=request.getRemoteAddr()%>:<%= request.getServerPort()%>/webapp">��¼</a>ϵͳ��
		
	</div>
	
	
</body>

<script type="text/javascript">
  
if (self != top){
	window.top.location = window.location;
}

</script>

</html>