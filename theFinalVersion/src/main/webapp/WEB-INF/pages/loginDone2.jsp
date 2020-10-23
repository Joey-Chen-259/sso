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
    <meta charset="UTF-8">
    <title>注销页面</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/logout.css"/>
</head>
<body>


<div id="logout_frame">

    <p id="image_logo"><img src="${pageContext.request.contextPath}/images/fly.png"></p>

    <form action="${pageContext.request.contextPath }/logout" method="post">

        <p><label class="text_field">您已成功登录,这是app2登陆完成页面</label></p>

        <div id="logout_control">
            <input type="submit" value="注销">
        </div>
    </form>
</div>

</body>
</html>
