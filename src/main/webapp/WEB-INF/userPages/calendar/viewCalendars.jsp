<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Your calendars">
    <h3>These are your calendars</h3>

    <div class="container text-center">
        <c:if test="${empty calendars}">
            <p>No calendars to show. Proceed with creating a calendar by pressing the button below.</p>
            <div class="row">
                <a href="${pageContext.request.contextPath}/AddCalendar">Create a new calendar!</a>
            </div>
        </c:if>
        <c:if test="${not empty calendars}">
            <c:forEach var="calendar" items="${calendars}">
                <div class="row">
                    <div class="col">
                        <a href="${pageContext.request.contextPath}/Calendar?calendarId=${calendar.id}">${calendar.name}</a>
                    </div>
                    <div class="col">
                        <p>${calendar.description}</p>
                    </div>
                </div>
            </c:forEach>
            <div class="row">
                <a href="${pageContext.request.contextPath}/AddCalendar">Create a new calendar!</a>
            </div>
        </c:if>
    </div>
</t:pageTemplate>
