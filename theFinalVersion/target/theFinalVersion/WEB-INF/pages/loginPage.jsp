<%--
  Created by IntelliJ IDEA.
  User: lasuerte
  Date: 2020/10/14
  Time: 9:20 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>用户登陆</title>
</head>

<body>
<form action="${pageContext.request.contextPath }/ssoServer" method="post">
    用户名：<input type="text" name="username"><br/>
    密码：<input type="password" name="password"><br/>
    <input type="submit" value="登陆">
</form>



</body>
</html>
