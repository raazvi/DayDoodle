<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pageTemplate pageTitle="News Feed">
    <p>${sessionScope.user.role}: ${sessionScope.user.username}</p>
    <h3> See what you have missed...</h3>
    <h4> This is just a test for the feed and login at the same time lmao</h4>
</t:pageTemplate>