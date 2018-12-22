<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生信息管理系统登录</title>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript">
	function resetValue(){
		document.getElementById("userName").value="";
		document.getElementById("password").value="";
	}
</script>
<style type="text/css">

</style>
</head>
<body>
	<div class="outer-wrapper">
		<div>
	        <img class="yc-logo" src="images/logo.png">
	        <ul class="face-index">
	            <li>
	                <a href="passwordEntry.html">密码登录</a>
	            </li>
	            <li>
	                <a>|</a>
	            </li>
	            <li>
	                <a href="faceRegister1.html">刷脸注册</a>
	            </li>
	            <li>
	                <a>|</a>
	            </li>
	            <li>
	                <a href="update.html">更改注册照</a>
	            </li>
	        </ul>
	    </div>
		<div class="login-wrapper">
			<div class="left-word">
		                云从科技OA管理系统
		    </div>
			<div class="right-wrapper">
				<form action="login" method="post">
				<table  width="400px;" height="400px;" background="images/password.png" >
					<tr height="180">
						<td colspan="4"></td>
					</tr>
					<tr height="10">
						<td width="40%"></td>
						<td width="10%">用户名：</td>
						<td><input type="text" value="${userName }" name="userName" id="userName"/></td>
						<td width="30%"></td>
					</tr>
					<tr height="10">
						<td width="40%"></td>
						<td width="10%">密  码：</td>
						<td><input type="password" value="${password }" name="password" id="password"/></td>
						<td width="30%"></td>
					</tr>
					<tr height="10">
						<td width="40%"></td>
						<td width="10%"><input type="submit" value="登录"/></td>
						<td><input type="button" value="重置" onclick="resetValue()"/></td>
						<td width="30%"></td>
					</tr>
					<tr height="10">
						<td width="40%"></td>
						<td colspan="3">
							<font color="red">${error }</font>
						</td>
					</tr>
					<tr >
						<td></td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>