<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>集成办公信息系统登录</title>
<link href="http://blog.java1234.com/favicon.ico" rel="SHORTCUT ICON">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/iconfont.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>

<script type="text/javascript">
	function resetValue(){
		document.getElementById("username").value="";
		document.getElementById("password").value="";
		document.getElementById("errorMsg").innerHTML="";
	}
</script>
</head>
<body>
	<div align="center" style="padding-top: 150px;">
		<form action="${pageContext.request.contextPath}/login/in" method="post">
			<div class="login_box">
				<div class="login_box_title">
					<label>集成办公信息系统</label>
				</div>
				<div class="login_box_input">
					<label for="username"><i class="iconfont icon-iconset0202"></i></label>
					
					<input type="text" value="${username}" name="username" id="username" placeholder="请输入帐号..."; />
				</div>
				<div class="login_box_input">
					<label for="password"><i class="iconfont icon-mima"></i></label>
					
					<input type="password" value="${password}" name="password" id="password" placeholder="请输入密码..."; />
				</div>
				<div class="login_box_botom">
					<input type="submit" value="登录"/>
					<input type="button" value="重置" onclick="resetValue()"/>
				</div>
				<div class="login_box_msg" id="errorMsg">
					${errorMsg}
				</div>
			</div>
		</form>
	</div>
</body>
</html>