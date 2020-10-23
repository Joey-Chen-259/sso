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
    <meta charset="UTF-8">
    <title>登录页面</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}css/login.css"/>
</head>

<body>
<div id="login_frame">

    <p id="image_logo"><img src="${pageContext.request.contextPath}images/fly.png" ></p>
    已经存在用户名为1234，密码为1234的用户，可以使用该账户进行用户检测
    <form action="${pageContext.request.contextPath }/ssoServer" method="post">

        用户名：<input type="text" name="username"><br/>
        密码：<input type="password" name="password"><br/>
        <input type="submit" value="登录">

    </form>
</div>

</body>
</html>
