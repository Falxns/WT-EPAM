<%--
  Created by IntelliJ IDEA.
  User: maksf
  Date: 04.12.2020
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Authorization</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: #00a7ff; color: #a3ff22} input, select, button{background-color: #0067ac !important; color: #a3ff22 !important;}</style>
</head>
<body>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<c:set var="text">
    <fmt:message key="authorization_title"/>
</c:set>
<custom:header text="${text}"/>
<div class="container" style="min-height: 100vh; padding-top: 50px">
    <form action="${pageContext.request.contextPath}/dev/auth.html" method="post">
        <div class="form-group">
            <label for="username"><fmt:message key="username"/></label>
            <input class="form-control" id="username" name="username" placeholder="<fmt:message key="username"/>">
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="password"/></label>
            <input class="form-control" id="password" name="password" placeholder="<fmt:message key="password"/>">
        </div>
        <div>
            <button class="btn btn-danger" style="border-color: #0067ac"><fmt:message key="submit_auth"/></button>
        </div>
    </form>
</div>
</body>
</html>