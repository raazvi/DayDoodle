<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Create a post!">
    <h1>New post...</h1>
    <form method="POST"  action="${pageContext.request.contextPath}/AddPost">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="caption">Caption</label>
                <input type="text" class="form-control" id="caption" name="caption" placeholder="Tell us about your day..." value="">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="activity">Activity:</label>
                <select class="form-control" id="activity" name="activity">
                    <option value="" selected disabled>Choose an activity</option>
                    <c:forEach var="activity" items="${activities}">
                        <option value="a${activity.id}">${activity.name}</option>
                    </c:forEach>
                    <optgroup label="User Activities">
                        <c:forEach var="customActivity" items="${customActivities}">
                            <option value="u${customActivity.id}">${customActivity.name}</option>
                        </c:forEach>
                    </optgroup>
                </select>
            </div>
        </div>
        <input type="submit" value="Post!">
    </form>
</t:pageTemplate>