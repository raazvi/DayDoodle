<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Your calendars">
    <h3 class="calendars-heading">These are your calendars</h3>

    <div class="calendars-container">
        <c:if test="${empty yourCalendars}">
            <p>No calendars to show. Proceed with creating a calendar by pressing the button below.</p>
        </c:if>
        <c:if test="${not empty yourCalendars}">
            <h4 class="calendars-heading">Your Calendars</h4>
            <table class="calendars-table">
                <thead>
                <tr>
                    <th>Calendar Name</th>
                    <th>Calendar Description</th>
                    <th>Action Buttons</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="calendar" items="${yourCalendars}">
                    <tr>
                        <td>
                            <a class="calendars-link" href="${pageContext.request.contextPath}/Calendar?calendarId=${calendar.id}">${calendar.name}</a>
                        </td>
                        <td>${calendar.description}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/DeleteCalendar" method="get">
                                <input type="hidden" name="calendarId" value="${calendar.id}" />
                                <button type="submit" class="calendars-button">Delete Calendar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <div class="calendars-container">
        <div class="calendars-row">
            <a class="calendars-link" href="${pageContext.request.contextPath}/AddCalendar">Create a new calendar!</a>
        </div>
    </div>

    <c:if test="${not empty notYourCalendars}">
        <div class="calendars-container">
            <h4 class="calendars-heading">Calendars You've Been Invited On</h4>
            <table class="calendars-table">
                <thead>
                <tr>
                    <th>Calendar Name</th>
                    <th>Calendar Description</th>
                    <th>Action Buttons</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="calendar" items="${notYourCalendars}">
                    <tr>
                        <td>
                            <a class="calendars-link" href="${pageContext.request.contextPath}/Calendar?calendarId=${calendar.id}">${calendar.name}</a>
                        </td>
                        <td>${calendar.description}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/LeaveCalendar" method="get">
                                <input type="hidden" name="calendarId" value="${calendar.id}" />
                                <button type="submit" class="calendars-button">Leave Calendar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</t:pageTemplate>
