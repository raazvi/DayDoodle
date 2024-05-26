<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="${userDetails.username}'s profile">
    <div class="container text-center main-content">

        <div class="profile-container">
            <c:if test="${not empty userDetails.username}">
                <p class="profile-info">Username: ${userDetails.username} <c:if test="${not empty userDetails.pronouns }">(${userDetails.pronouns})</c:if></p>
            </c:if>

            <c:if test="${not empty userDetails.profilePicture}">
                <div class="profile-picture-wrapper">
                    <c:if test="${not empty userDetails.profilePicture}">
                        <img src="data:image/${userDetails.profilePicture.imageFormat};base64,${userDetails.profilePicture.base64ImageData}" alt="Profile Picture" class="profile-picture" width="200" height="200">
                    </c:if>
                    <c:if test="${empty userDetails.profilePicture}">
                        <img src="default-profile-picture.jpg" alt="Default Profile Picture" class="profile-picture">
                    </c:if>
                </div>
            </c:if>

            <c:if test="${not empty userDetails.firstName}">
                <p class="profile-info">Their name is ${userDetails.firstName} ${userDetails.lastName}, but they like to be called ${userDetails.nickname}</p>
            </c:if>

            <c:if test="${not empty userDetails.description}">
                <p class="profile-info">About themselves: ${userDetails.description}</p>
            </c:if>

            <c:if test="${not empty userDetails.birthDate}">
                <p class="profile-info">Birthday: ${userDetails.birthDate}</p>
            </c:if>

            <c:if test="${not empty userDetails.zodiacSign}">
                <p class="profile-info">Zodiac Sign: ${userDetails.zodiacSign}</p>
            </c:if>

            <c:if test="${not empty userDetails.location}">
                <p class="profile-info">Location: ${userDetails.location}</p>
            </c:if>

            <c:if test="${not friends}">
                <div class="friend-request-container">
                    <c:choose>
                        <c:when test="${friendshipRequestPending}">
                            <button class="btn btn-secondary" disabled>Friend Request Pending</button>
                        </c:when>
                        <c:when test="${userSentMeAFriendshipRequest}">
                            <button class="btn btn-secondary" disabled>User is waiting for your reply</button>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/AddFriend" method="POST">
                                <input type="hidden" name="username" value="${userDetails.username}">
                                <button type="submit" class="btn btn-primary">Add Friend</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </div>
</t:pageTemplate>
