<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="userRole" value="${sessionScope.user != null ? sessionScope.user.role : ''}" />

<header>
    <nav class="navbar navbar-expand-md fixed-top bg-dark">
        <div class="container-fluid">
            <!-- Burger menu toggle button -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <!-- Left side menu items -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav flex-fill">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Feed"> DayDoodle </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Diary"> Diary </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/ViewCalendars"> Calendars </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/ViewFriends"> Friends </a>
                    </li>
                </ul>
                <!-- Right side menu items -->
                <ul class="navbar-nav flex-fill justify-content-end">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Profile"> ${sessionScope.user.username} </a>
                    </li>
                    <c:if test="${userRole eq 'admin'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle active" href="#" id="adminDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Admin Settings
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="adminDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Stats">Stats</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ManageActivities">Manage Activities</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Settings">
                            <i class="bi bi-gear"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Help">
                            <i class="bi bi-question-circle"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Logout"> Log Out </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
