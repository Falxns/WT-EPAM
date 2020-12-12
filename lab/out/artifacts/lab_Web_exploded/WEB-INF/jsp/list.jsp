<%--
  Created by IntelliJ IDEA.
  User: maksf
  Date: 10.12.2020
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Publications</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: #00a7ff; color: #a3ff22} td, th{background-color: #0067ac !important; color: #a3ff22 !important;}</style>
</head>
<body>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="text"/>
    <c:set var="text">
        <fmt:message key="publications_title"/>
    </c:set>
    <custom:header text="${text}"/>
    <div class="container" style="min-height: 100vh; padding-top: 50px">
        <div>
            <c:if test="${sessionScope.user.role == 'admin'}">
                <a class="btn btn-danger" style="text-align: center; background-color: #0067ac; color: #a3ff22; border-color: #0067ac" href="${pageContext.request.contextPath}/dev/add-publ.html">
                    <fmt:message key="add_publication"/>
                </a>
            </c:if>
        </div>
        <table class="table table-dark">
            <thead>
            <tr>
                <th style="width: 5%">№</th>
                <th style="width: 65%"><fmt:message key="link_publication"/></th>
                <c:choose>
                    <c:when test="${sessionScope.user.role == 'admin'}">
                        <th style="width: 10%"><fmt:message key="delete_publication"/></th>
                    </c:when>
                    <c:otherwise>
                        <th style="width: 10%"><fmt:message key="order_publication"/></th>
                    </c:otherwise>
                </c:choose>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="publication" items="${publications}" varStatus="loop">
                <tr>
                    <td style="vertical-align: baseline">${loop.index + 1}</td>
                    <td style="vertical-align: baseline">
                        <a style="text-decoration: none; color: #a3ff22" href="${pageContext.request.contextPath}/dev/details.html?id=${publication.id}">
                            <fmt:message key="view_publication"/>
                                ${publication.name}
                        </a>
                    </td>
                    <c:choose>
                        <c:when test="${sessionScope.user.role == 'admin'}">
                            <td style="vertical-align: baseline">
                                <a style="text-decoration: underline; color: #a3ff22" href="${pageContext.request.contextPath}/dev/delete-publ.html?id=${publication.id}">
                                    <fmt:message key="delete_publication"/>
                                </a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td style="vertical-align: baseline">
                                <a style="text-decoration: underline; color: #a3ff22" href="${pageContext.request.contextPath}/dev/order.html?id=${publication.id}">
                                    <fmt:message key="order_publication"/>
                                </a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${sessionScope.user.role == 'reader'}">
            <div>
                <a class="btn btn-danger" style="text-align: center; background-color: #0067ac; color: #a3ff22; border-color: #0067ac" href="${pageContext.request.contextPath}/dev/payment.html">
                    <fmt:message key="payment_submit"/>
                </a>
            </div>
        </c:if>
    </div>
</body>
</html>