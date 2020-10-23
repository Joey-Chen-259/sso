<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>主页</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/app1/Login" method="post">
    点击进入系统一：<input type="submit" value="系统1">
</form>
<form action="${pageContext.request.contextPath }/app2/Login" method="post">
    点击进入系统二：<input type="submit" value="系统2">
</form>
<form action="${pageContext.request.contextPath }/toAddUser" method="post">
    点击添加用户：<input type="submit" value="添加">
</form>
</body>
</html>
