<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Add an event">
    <h3>You're adding an event to your ${calendar.name} calendar</h3>

    <!-- Form for adding an event -->
    <form method="POST" action="${pageContext.request.contextPath}/AddEvent">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="title">*Title:</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Enter event title" value="" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="fromDate">*From:</label>
                <input type="datetime-local" class="form-control" id="fromDate" name="fromDate" value="" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="toDate">*To:</label>
                <input type="datetime-local" class="form-control" id="toDate" name="toDate" value="" required>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 mb-3">
                <label for="description">Description:</label>
                <textarea class="form-control" id="description" name="description" placeholder="Enter event description" style="height: 100px;"></textarea>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="location">Location:</label>
                <input type="text" class="form-control" id="location" name="location" placeholder="Enter event location" value="">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="activity">*Activity:</label>
                <select class="form-control" id="activity" name="activity" required>
                    <option value="" selected disabled>Choose an activity</option>
                    <c:forEach var="activity" items="${activities}">
                        <option value="a${activity.id}">${activity.name}</option>
                    </c:forEach>
                    <optgroup label="Your Custom Activities">
                        <c:forEach var="customActivity" items="${customActivities}">
                            <option value="u${customActivity.id}">${customActivity.name}</option>
                        </c:forEach>
                        <option value="createNew">Create a new custom activity</option>
                    </optgroup>
                </select>
            </div>
        </div>
        <input type="submit" value="Add Event">
        <input type="hidden" name="calendarId" value="${calendar.id}">
    </form>

    <div id="newActivityForm" style="display: none;">
        <h2>Create a new activity</h2>
        <form method="POST" action="${pageContext.request.contextPath}/AddCustomActivity">
            <table>
                <tr>
                    <td><label for="newActivityName">Name:</label></td>
                    <td><input type="text" class="form-control" id="newActivityName" name="name" placeholder="Enter activity name"></td>
                </tr>
                <tr>
                    <td><label for="newActivityDescription">Description:</label></td>
                    <td><textarea class="form-control" id="newActivityDescription" name="description" placeholder="Enter activity description"></textarea></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button type="submit" class="btn btn-primary">Add Activity</button>
                        <input type="hidden" name="page" value="addEvent">
                        <input type="hidden" name="calendarId" value="${calendar.id}">
                    </td>
                </tr>
            </table>
            <p> By submitting this form, you are adding a new activity to your custom activities list.</p>
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
