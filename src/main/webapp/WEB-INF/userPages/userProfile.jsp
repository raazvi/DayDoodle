<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Profile">
    <div class="container">
        <div class="row">
            <!-- Sidebar for Activities -->
            <div class="col-md-3">
                <h4>Add New Custom Activity</h4>
                <p>By filling in the form below, you can add a new custom activity that you define and that you will be able to use across the whole app.</p>
                <form action="${pageContext.request.contextPath}/AddCustomActivity" method="post" class="sticky-top">
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

                <h4>Available Activities</h4>
                <div class="list-group activities-section" style="max-height: 400px; overflow-y: scroll;">
                    <c:forEach var="customActivity" items="${customActivities}">
                        <div class="mb-3">
                            <div class="card activity-card">
                                <div class="card-body">
                                    <h5 class="card-title">${customActivity.name}</h5>
                                    <p class="card-text">${customActivity.description}</p>
                                    <a href="${pageContext.request.contextPath}/EditCustomActivity?activityId=${customActivity.id}" class="btn btn-primary">Edit</a>
                                    <a href="${pageContext.request.contextPath}/DeleteCustomActivity?activityId=${customActivity.id}" class="btn btn-danger">Delete</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-md-6 main-content">
                <div class="text-center">
                    <h3>Hello, ${user.firstName}</h3>
                </div>

                <!-- Profile Picture -->
                <div class="row">
                    <div class="col-md-12 text-center">
                        <c:if test="${not empty user.profilePicture}">
                            <img class="profile-picture" src="data:image/${user.profilePicture.imageFormat};base64,${user.profilePicture.base64ImageData}" alt="Profile Picture" width="200" height="200">
                        </c:if>
                        <c:if test="${empty user.profilePicture}">
                            <img class="profile-picture" src="default-profile-picture.jpg" alt="Default Profile Picture" width="200" height="200">
                        </c:if>
                    </div>
                </div>

                <!-- Basic Information -->
                <div class="row">
                    <div class="col-md-12">
                        <c:if test="${not empty user.firstName}">
                            <p>Full name: ${user.firstName} ${user.lastName} (Your nickname is: ${user.nickname})</p>
                        </c:if>
                        <c:set var="currentDate" value="${java.time.LocalDate.now()}" />
                        <c:choose>
                            <c:when test="${not empty user.birthDate}">
                                <c:set var="birthdayMonth" value="${user.birthDate.month}" />
                                <c:choose>
                                    <c:when test="${not empty birthdayMonth}">
                                        <c:if test="${user.birthDate ne currentDate}">
                                            <p>Birthday: ${user.birthDate} (There are ${daysTilBirthday} days left until your birthday)</p>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Birthday: Not specified</p>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <p>Birthday: ${currentDate}</p>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${not empty user.zodiacSign}">
                            <p>Zodiac Sign: ${user.zodiacSign}</p>
                        </c:if>
                        <c:if test="${not empty user.location}">
                            <p>Location: ${user.location}</p>
                        </c:if>
                        <c:if test="${not empty user.description}">
                            <p>About yourself: ${user.description}</p>
                        </c:if>
                    </div>
                </div>

                <!-- Edit Links -->
                <div class="row">
                    <div class="col-md-12">
                        <div>
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditProfile">Edit your profile</a>
                        </div>
                    </div>
                </div>

                <h4>Your posts</h4>
                <div class="row posts-section">
                    <c:forEach var="post" items="${posts}">
                        <div class="col-md-6 mb-3">
                            <div class="card post-card">
                                <c:if test="${not empty post.picture}">
                                    <img class="card-img-top" src="data:image/${post.picture.imageFormat};base64,${post.picture.base64ImageData}" alt="Post picture" style="width: 100%; height: auto;">
                                </c:if>
                                <div class="card-body">
                                    <h5 class="card-title">${post.caption}</h5>
                                    <p class="card-text">${post.datePosted}</p>
                                    <p class="card-text">Reactions: ${post.reactionsCount}</p>
                                    <p class="card-text">Comments: ${post.commentsCount}</p>
                                    <a href="${pageContext.request.contextPath}/ViewPost?postId=${post.id}" class="btn btn-primary">View Post</a>
                                    <a href="${pageContext.request.contextPath}/EditPost?postId=${post.id}" class="btn btn-primary">Edit</a>
                                    <a href="${pageContext.request.contextPath}/DeletePost?postId=${post.id}" class="btn btn-danger">Delete</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <hr>
            </div>

            <!-- Sidebar for Notifications -->
            <div class="col-md-3">
                <h4>Notifications</h4>
                <p>You have ${unreadNotificationsCount} unread notifications</p>
                <div class="list-group notifications-section" style="max-height: 400px; overflow-y: scroll;">
                    <!-- Unread Notifications -->
                    <c:forEach var="notification" items="${unreadNotifications}">
                        <a href="${pageContext.request.contextPath}/ViewNotification?notificationId=${notification.id}" class="list-group-item list-group-item-action list-group-item-warning notification-item">
                                ${notification.message} <br> <small>${notification.formattedCreatedAt}</small>
                        </a>
                    </c:forEach>

                    <!-- Read Notifications -->
                    <c:forEach var="notification" items="${readNotifications}">
                        <a href="${pageContext.request.contextPath}/ViewNotification?notificationId=${notification.id}" class="list-group-item list-group-item-action notification-item">
                                ${notification.message} <br> <small>${notification.formattedCreatedAt}</small>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>
