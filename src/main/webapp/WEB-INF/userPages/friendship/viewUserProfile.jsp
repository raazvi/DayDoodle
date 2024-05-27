<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:profileTemplate pageTitle="${userDetails.username}'s profile">
    <div class="container main-content text-center">

        <div class="row mb-3">
            <div class="col-md-6 offset-md-3 card profile-card">
                <div class="card-body">
                    <c:if test="${not empty userDetails.username}">
                        <p class="card-title">Username: ${userDetails.username}
                            <c:if test="${not empty userDetails.pronouns}">
                                (${userDetails.pronouns})
                            </c:if>
                        </p>
                    </c:if>

                    <c:if test="${not empty userDetails.profilePicture}">
                        <!-- Profile Picture -->
                        <div class="row mb-3">
                            <div class="col-md-12 d-flex justify-content-center">
                                <div class="profile-picture-container">
                                    <c:choose>
                                        <c:when test="${not empty userDetails.profilePicture}">
                                            <img class="profile-picture-fixed" src="data:image/${userDetails.profilePicture.imageFormat};base64,${userDetails.profilePicture.base64ImageData}" alt="Profile Picture">
                                        </c:when>
                                        <c:otherwise>
                                            <img class="profile-picture-fixed" src="default-profile-picture.jpg" alt="Default Profile Picture">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty userDetails.firstName}">
                        <p class="card-text">Their name is ${userDetails.firstName} ${userDetails.lastName}
                        <c:if test="${not empty userDetails.nickname}">
                            , but they like to be called ${userDetails.nickname}</p>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty userDetails.description}">
                        <p class="card-text">About themselves: ${userDetails.description}</p>
                    </c:if>
                    <c:if test="${not empty userDetails.birthDate}">
                        <p class="card-text">Birthday: ${userDetails.birthDate}</p>
                    </c:if>
                    <c:if test="${not empty userDetails.zodiacSign}">
                        <p class="card-text">Zodiac Sign: ${userDetails.zodiacSign}</p>
                    </c:if>
                    <c:if test="${not empty userDetails.location}">
                        <p class="card-text">Location: ${userDetails.location}</p>
                    </c:if>
                </div>
            </div>
        </div>

        <c:if test="${not friends}">
            <div class="row">
                <div class="col-md-6 offset-md-3">
                    <div class="card">
                        <div class="card-body text-center">
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
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</t:profileTemplate>
