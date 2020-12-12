<%--
  Created by IntelliJ IDEA.
  User: maksf
  Date: 11.12.2020
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Add publication</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: #00a7ff; color: #a3ff22} input, textarea, button{background-color: #0067ac !important; color: #a3ff22 !important;}</style>
</head>
<body>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="text"/>
    <c:set var="text">
        <fmt:message key="add_title"/>
    </c:set>
    <custom:header text="${text}"/>
    <div class="container" style="min-height: 100vh; padding-top: 50px">
        <form action="${pageContext.request.contextPath}/dev/save-publ.html" method="post">
            <div class="form-group">
                <label for="publication-name"><fmt:message key="publication_name"/>: </label>
                <input class="form-control" id="publication-name" name="publication-name" placeholder="<fmt:message key="publication_name"/>">
            </div>
            <div class="form-group">
                <label for="publication-cost"><fmt:message key="publication_cost"/>: </label>
                <input type="number" class="form-control" id="publication-cost" name="publication-cost" placeholder="<fmt:message key="publication_cost"/>">
            </div>
            <div class="form-group">
                <label for="publication-description"><fmt:message key="publication_description"/>: </label>
                <textarea class="form-control" id="publication-description" name="publication-description" placeholder="<fmt:message key="publication_description"/>"></textarea>
            </div>
            <div>
                <button class="btn btn-danger" style="border-color: #0067ac"><fmt:message key="add_submit"/></button>
            </div>
        </form>
    </div>
</body>
</html>