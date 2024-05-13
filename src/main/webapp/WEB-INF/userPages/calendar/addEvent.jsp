<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Add an event">
    <h3>You're adding an event to your ${calendar.name} calendar</h3>

    <form method="POST" action="${pageContext.request.contextPath}/AddEvent">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="title">Title:</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Enter event title" value="">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="fromDate">From:</label>
                <input type="datetime-local" class="form-control" id="fromDate" name="fromDate" value="">
            </div>
            <div class="col-md-6 mb-3">
                <label for="toDate">To:</label>
                <input type="datetime-local" class="form-control" id="toDate" name="toDate" value="">
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
                <label for="activity">Activity:</label>
                <select class="form-control" id="activity" name="activity">
                    <option value="" selected disabled>Choose an activity</option>
                    <c:forEach var="activity" items="${activities}">
                        <option value="a${activity.id}">${activity.name}</option>
                    </c:forEach>
                    <optgroup label="Your Custom Activities">
                        <c:forEach var="customActivity" items="${customActivities}">
                            <option value="u${customActivity.id}">${customActivity.name}</option>
                        </c:forEach>
                    </optgroup>
                </select>
            </div>
        </div>
        <input type="submit" value="Add Event">
        <input type="hidden" name="calendarId" value=${calendar.id}>
    </form>
</t:pageTemplate>