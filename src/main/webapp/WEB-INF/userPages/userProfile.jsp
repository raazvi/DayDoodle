<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Profile">
    <div class="container text-center">
        <div class="row">
            <h3>Hello, ${user.firstName}</h3>
        </div>

        <!-- Profile Picture -->
        <div class="row">
            <div class="col-md-4 offset-md-4">
                <c:if test="${not empty user.profilePicture}">
                    <img src="data:image/<c:out value="${user.profilePicture.imageFormat}" />;base64,${java.util.Base64.getEncoder().encodeToString(user.profilePicture.imageData)}" alt="Profile Picture" width="200" height="200">
                </c:if>
                <c:if test="${empty user.profilePicture}">
                    <img src="default-profile-picture.jpg" alt="Default Profile Picture" width="200" height="200">
                </c:if>
            </div>
        </div>

        <!-- Basic Information -->
        <div class="row">
            <div class="col-md-6 offset-md-3">
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
            <div class="col-md-6 offset-md-3">
                <div>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditProfile">Edit your profile</a>
                </div>
            </div>
        </div>
    </div>

    <h4> Your posts</h4>
    <div class="row">
        <c:forEach var="post" items="${posts}">
            <div class="col-md-2 mb-3">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${post.caption}</h5>
                        <p class="card-text">${post.datePosted}</p>
                        <!-- TODO Numar reactii si comentarii la postare -->
                        <a href="${pageContext.request.contextPath}/ViewPost?postId=${post.id}" class="btn btn-primary">View Post</a>
                        <a href="${pageContext.request.contextPath}/EditPost?postId=${post.id}" class="btn btn-primary">Edit</a>
                        <a href="${pageContext.request.contextPath}/DeletePost?postId=${post.id}" class="btn btn-danger">Delete</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <hr>

    <!-- User's activities and Insert activity form -->
    <h4>Add New Custom Activity</h4>
    <p> By filling in the form below, you can add a new custom activity that you define and that you will be able to use across the whole app.</p>
    <form action="${pageContext.request.contextPath}/AddCustomActivity" method="post">
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

    <hr>

    <h4>Your Available Activities</h4>
    <div class="row">
        <c:forEach var="customActivity" items="${customActivities}">
            <div class="col-md-2 mb-3">
                <div class="card">
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

</t:pageTemplate>
