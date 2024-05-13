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
                <p>Full name: ${user.firstName} ${user.lastName} (Your nickname is: ${user.nickname})</p>
                <p>Birthday: ${user.birthDate} (There are ${daysTilBirthday} days left until your birthday)</p>
                <p>Zodiac Sign: ${user.zodiacSign}</p>
                <p>Location: ${user.location}</p>
                <p>About yourself: ${user.description}</p>
            </div>
        </div>

        <!-- Edit Links -->
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditProfile">Edit your profile</a>
                </div>
                <div>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/ViewFriendRequests?username=${user.username}">Friend Requests</a>
                </div>
            </div>
        </div>
    </div>


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
