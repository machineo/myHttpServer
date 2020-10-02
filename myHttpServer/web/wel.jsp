<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1>请登录</h1>
<p style="color: red">${msg }</p>
<form action="http://172.27.131.180:8686/login" method="post">
    用户名：<input type="text" name="username" value="${form.username }"/><br/>
    密&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" value="${form.password }"/><br/>
    <input type="submit" value="点击登录"/>
</form>
</body>
</html>
