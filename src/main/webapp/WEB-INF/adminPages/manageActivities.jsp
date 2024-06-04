<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Manage Activities">
    <h3>Add a new activity, that will be available to all the users.</h3>

    <form action="${pageContext.request.contextPath}/ManageActivities" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Add Activity</button>
    </form>

    <hr>

    <h4>Available Activities</h4>
    <div class="row">
        <c:forEach var="activity" items="${activities}">
            <div class="col-md-2 mb-3">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${activity.name}</h5>
                        <p class="card-text">${activity.description}</p>
                        <a href="${pageContext.request.contextPath}/EditActivity?activityId=${activity.id}" class="btn btn-primary">Edit</a>
                        <a href="${pageContext.request.contextPath}/DeleteActivity?activityId=${activity.id}" class="btn btn-danger">Delete</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</t:pageTemplate>
