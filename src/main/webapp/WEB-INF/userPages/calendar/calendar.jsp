<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Calendar">
    <style>
        #calendar {
            max-width: 100%;
            margin: 0 auto;
        }
        .user-list {
            list-style-type: none;
            padding: 0;
        }
        .user-list li {
            display: inline-block;
            margin-right: 10px;
            padding: 5px;
            border-radius: 5px;
            color: #fff;
        }
    </style>
    <!-- Include Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <div class="container mt-3">
        <div class="row mt-3">
            <div class="col mt-3">
                <a href="${pageContext.request.contextPath}/AddEvent?calendarId=${calendar.id}" data-toggle="tooltip" data-placement="bottom" title="Click on the button to add a new event to the current calendar">Add an event to your calendar</a>
            </div>
            <c:if test="${ownedByThisUser eq true}">
                <div class="col mt-3">
                    <button class="btn btn-primary" data-toggle="modal" data-target="#addUserModal">Add a user to the calendar</button>
                </div>
            </c:if>
        </div>
        <div class="row mt-3">
            <div class="col">
                <h4>Users in this calendar:</h4>
                <ul class="user-list">
                    <c:forEach var="userInCalendar" items="${usersInCalendar}">
                        <li style="background-color: ${userColors[userInCalendar.username]}">${userInCalendar.username}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col">
                <h3 class="text-center">Your calendar: ${calendar.name}</h3>
                <div id="calendar"></div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Modal for Adding User to Calendar -->
    <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addUserModalLabel">Add User to Calendar</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/AddUserToCalendar" method="post">
                        <div class="form-group">
                            <label for="friendUsername">Select Friend:</label>
                            <select class="form-control" id="friendUsername" name="friendUsername">
                                <c:forEach var="friend" items="${friendsNotInCalendar}">
                                    <option value="${friend.username}">${friend.username}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input type="hidden" name="calendarId" value="${calendar.id}">
                        <button type="submit" class="btn btn-primary">Add</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Modal for Event Details -->
    <div class="modal fade" id="eventModal" tabindex="-1" role="dialog" aria-labelledby="eventModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="eventModalLabel">Event Details</h5>
                </div>
                <div class="modal-body">
                    <p><strong>Title:</strong> <span id="eventTitle"></span></p>
                    <p><strong>Description:</strong> <span id="eventDescription"></span></p>
                    <p><strong>Created by:</strong> <span id="eventCreatedBy"></span></p>
                    <p><strong>Activity:</strong> <span id="eventActivity"></span></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="deleteEventBtn">Delete Event</button>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>

<!-- Include FullCalendar CSS -->
<link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css" rel="stylesheet" />

<!-- Include FullCalendar JS -->
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js"></script>

<!-- Include jQuery and Bootstrap JS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    // Initialize tooltips
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });

    document.addEventListener('DOMContentLoaded', function() {
        const calendarEl = document.getElementById('calendar');

        const events = [
            <c:forEach items="${events}" var="event">
            {
                title: '${event.title}',
                start: '${event.start}',
                end: '${event.end}',
                description: '${event.description}',
                createdBy: '${event.user.username}',
                activityName: '${event.activity != null ? event.activity.name : event.userActivity.name}',
                id: '${event.id}', // Make sure the event id is included
                color: '${userColors[event.user.username]}', // Add this line to set the event color
            },
            </c:forEach>
        ];

        const calendarObj = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            headerToolbar: {
                start: 'prev,next today',
                center: 'title',
                end: 'dayGridMonth,timeGridWeek,dayGridDay'
            },
            events: events,
            aspectRatio: 1.75,
            eventClick: function(info) {
                document.getElementById('eventTitle').textContent = info.event.title;
                document.getElementById('eventDescription').textContent = info.event.extendedProps.description;
                document.getElementById('eventCreatedBy').textContent = info.event.extendedProps.createdBy;
                document.getElementById('eventActivity').textContent = info.event.extendedProps.activityName;
                $('#eventModal').modal('show');

                // Add event listener for the delete button in the modal
                document.getElementById('deleteEventBtn').addEventListener('click', function() {
                    const eventId = info.event.id;
                    const calendarId = '${calendar.id}';
                    window.location.href = '${pageContext.request.contextPath}/DeleteEvent?eventId=' + eventId + '&calendarId=' + calendarId;
                });
            }
        });

        calendarObj.render();
    });
</script>
