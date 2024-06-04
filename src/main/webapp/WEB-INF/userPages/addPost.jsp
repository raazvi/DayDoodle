<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Create a post!">
    <body class="content">
    <h1 class="form-title">New post...</h1>
    <div class="login-register">
        <form method="POST" action="${pageContext.request.contextPath}/AddPost" enctype="multipart/form-data">
            <div class="row">
                <div class="col-md-12 mb-3">
                    <label for="caption">Caption</label>
                    <input type="text" class="form-control" id="caption" name="caption" placeholder="Tell us about your day..." value="" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 mb-3">
                    <label for="activity">Activity:</label>
                    <select class="form-control" id="activity" name="activity" required>
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
            <div class="row">
                <div class="col-md-12 mb-3">
                    <label for="picture">Picture</label>
                    <input type="file" class="form-control" id="picture" name="picture">
                </div>
            </div>
            <input type="submit" value="Post!" class="btn btn-primary">
        </form>
    </div>

    <!-- Form for creating a new activity -->
    <div id="newActivityForm" class="login-register" style="display: none;">
        <h2 class="form-title">Create a new activity</h2>
        <form method="POST" action="${pageContext.request.contextPath}/AddCustomActivity">
            <div class="row">
                <div class="col-md-12 mb-3">
                    <label for="newActivityName">Name</label>
                    <input type="text" class="form-control" id="newActivityName" name="name" placeholder="Enter activity name" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 mb-3">
                    <label for="newActivityDescription">Description</label>
                    <textarea class="form-control" id="newActivityDescription" name="description" placeholder="Enter activity description"></textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Add Activity</button>
            <input type="hidden" name="page" value="addPost">
        </form>
    </div>

    <script>
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
    </body>
</t:pageTemplate>
