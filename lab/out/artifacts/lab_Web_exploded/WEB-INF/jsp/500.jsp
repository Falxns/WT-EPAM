<%--
  Created by IntelliJ IDEA.
  User: maksf
  Date: 12.12.2020
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>500</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: #00a7ff; color: #a3ff22} h1{background-color: #0067ac !important; color: #a3ff22 !important;}</style>
</head>
<body>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<c:set var="text">
    500
</c:set>
<custom:header text="${text}"/>
<div class="container"  style="min-height: 100vh; padding-top: 50px; text-align: center">
    <h1>
        <fmt:message key="500_error"/>
    </h1>
</div>
</body>
</html>