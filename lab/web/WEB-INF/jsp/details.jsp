<%--
  Created by IntelliJ IDEA.
  User: maksf
  Date: 11.12.2020
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: #00a7ff; color: #a3ff22} h3{background-color: #0067ac !important; color: #a3ff22 !important;} h4{color: #a3ff22}</style>
</head>
<body>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="text"/>
    <c:set var="text">
        ${publication.name}
    </c:set>
    <custom:header text="${text}"/>
    <div class="container" style="min-height: 100vh; padding-top: 50px">
        <h3><fmt:message key="publication_name"/>: </h3>
        <h4>${publication.name}</h4>
        <h3><fmt:message key="publication_cost"/>: </h3>
        <h4>${publication.cost}</h4>
        <h3><fmt:message key="publication_description"/>: </h3>
        <h4>${publication.description}</h4>
    </div>
</body>
</html>