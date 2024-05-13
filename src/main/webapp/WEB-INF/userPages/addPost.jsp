<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Create a post!">
    <h1>New post...</h1>
    <form method="POST" action="${pageContext.request.contextPath}/AddPost">
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
                        <option value="createNew">Create a new activity</option>
                    </optgroup>
                </select>
            </div>
        </div>
        <input type="submit" value="Post!">
    </form>

    <!-- Form for creating a new activity -->
    <div id="newActivityForm" style="display: none;">
        <h2>Create a new activity</h2>
        <form method="POST" action="${pageContext.request.contextPath}/AddCustomActivity">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="newActivityName">Name</label>
                    <input type="text" class="form-control" id="newActivityName" name="name" placeholder="Enter activity name">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="newActivityDescription">Description</label>
                    <textarea class="form-control" id="newActivityDescription" name="description" placeholder="Enter activity description"></textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Add Activity</button>
            <input type="hidden" name="page" value="addPost">
        </form>
    </div>

    <script>
        // Function to toggle visibility of new activity form
        document.getElementById('activity').addEventListener('change', function() {
            var selectElement = document.getElementById('activity');
            var newActivityForm = document.getElementById('newActivityForm');
            if (selectElement.value === 'createNew') {
                newActivityForm.style.display = 'block';
            } else {
                newActivityForm.style.display = 'none';
            }
        });
    </script>
</t:pageTemplate>
