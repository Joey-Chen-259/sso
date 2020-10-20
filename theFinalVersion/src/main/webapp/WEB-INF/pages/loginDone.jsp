<%--
  Created by IntelliJ IDEA.
  User: lasuerte
  Date: 2020/10/15
  Time: 3:42 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
这是app1的登陆完成页面
<form action="${pageContext.request.contextPath }/logout" method="post">
    用户名：<input type="text" name="username"><br/>
    密码：<input type="password" name="password"><br/>
    <input type="submit" value="注销">
</form>
</body>
</html>
