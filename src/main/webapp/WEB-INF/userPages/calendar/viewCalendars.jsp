<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Your calendars">
    <h3>These are your calendars</h3>

    <div class="container text-center">
        <c:if test="${empty yourCalendars}">
            <p>No calendars to show. Proceed with creating a calendar by pressing the button below.</p>
        </c:if>
        <c:if test="${not empty yourCalendars}">
            <h4>Your Calendars</h4>
            <c:forEach var="calendar" items="${yourCalendars}">
                <div class="row">
                    <div class="col">
                        <a href="${pageContext.request.contextPath}/Calendar?calendarId=${calendar.id}">${calendar.name}</a>
                    </div>
                    <div class="col">
                        <p>${calendar.description}</p>
                    </div>
                    <div class="col">
                        <form action="${pageContext.request.contextPath}/DeleteCalendar" method="get">
                            <input type="hidden" name="calendarId" value="${calendar.id}" />
                            <button type="submit">Delete Calendar</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
            <div class="row">
                <a href="${pageContext.request.contextPath}/AddCalendar">Create a new calendar!</a>
            </div>
        </c:if>
        <c:if test="${not empty notYourCalendars}">
            <h4>Calendars You've Been Invited On</h4>
            <c:forEach var="calendar" items="${notYourCalendars}">
                <div class="row">
                    <div class="col">
                        <a href="${pageContext.request.contextPath}/Calendar?calendarId=${calendar.id}">${calendar.name}</a>
                    </div>
                    <div class="col">
                        <p>${calendar.description}</p>
                    </div>
                    <div class="col">
                        <form action="${pageContext.request.contextPath}/LeaveCalendar" method="get">
                            <input type="hidden" name="calendarId" value="${calendar.id}" />
                            <button type="submit">Leave Calendar</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</t:pageTemplate>
