<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<h1>单文件上传</h1>
<form method="POST" action="${pageContext.request.contextPath}/upload/upload" enctype="multipart/form-data">
    <input type="text" name="username" /><br/><br/>
    <input type="file" name="file" /><br/><br/>
    <input type="submit" value="Submit" />
</form>

<h1>文件上传状态</h1>
${message}
</body>
</html>
