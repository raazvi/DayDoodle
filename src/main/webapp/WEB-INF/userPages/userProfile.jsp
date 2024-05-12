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
            </div>
        </div>

        <!-- Description and Edit Links -->
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <p>About yourself: ${user.description}</p>
                <div>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditProfile">Edit your profile</a>
                </div>
                <div>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/ViewFriendRequests?username=${user.username}">Friend Requests</a>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>
