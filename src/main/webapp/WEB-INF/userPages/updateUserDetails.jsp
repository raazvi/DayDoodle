<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:choose>
    <c:when test="${sessionScope.user.role eq 'admin'}">
        <t:adminTemplate pageTitle="Set up your profile!">
            <p> Admin: ${sessionScope.user.username}</p>
            <h3>Since this is the first time you connected to DayDoodle, tell us more about yourself. This way other users will be able to get to know you better!</h3>
        </t:adminTemplate>
    </c:when>
    <c:otherwise>
        <t:userTemplate pageTitle="Set up your profile!">
            <p> User: ${sessionScope.user.username}</p>
            <h3>Since this is the first time you connected to DayDoodle, tell us more about yourself. This way other users will be able to get to know you better!</h3>
        </t:userTemplate>
    </c:otherwise>
</c:choose>
