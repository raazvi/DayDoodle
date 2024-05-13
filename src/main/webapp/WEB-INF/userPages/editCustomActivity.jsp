<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:pageTemplate pageTitle="Edit Activity">
    <h1>Modify the detail you want, then hit submit.</h1>
    <form action="${pageContext.request.contextPath}/EditCustomActivity" method="post">
        <input type="hidden" name="activityId" value="${activity.id}">
        <label for="activityName">Activity Name:</label>
        <input type="text" id="activityName" name="activityName" value="${activity.name}"><br>
        <label for="activityDescription">Activity Description:</label>
        <textarea id="activityDescription" name="activityDescription">${activity.description}</textarea><br>
        <input type="submit" value="Submit!">
    </form>
</t:pageTemplate>
