<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:choose>
    <c:when test="${sessionScope.user.role eq 'admin'}">
        <t:adminTemplate pageTitle="Profile">
            <h3> See what you have missed...</h3>
            <h4> This is just a test for the feed and login at the same time lmao</h4>
        </t:adminTemplate>
    </c:when>
    <c:otherwise>
        <t:userTemplate pageTitle="Profile">
            <h3> See what you have missed...</h3>
            <h4> This is just a test for the feed and login at the same time lmao</h4>
        </t:userTemplate>
    </c:otherwise>
</c:choose>
