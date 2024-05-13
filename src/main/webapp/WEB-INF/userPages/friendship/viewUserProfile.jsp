<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="${userDetails.username}'s profile">
    <div class="container text-center">

        <div class="row">
            <div class="col-md-6 offset-md-3">
                <c:if test="${not empty userDetails.username}">
                    <p>Username: ${userDetails.username} (${userDetails.pronouns})</p>
                </c:if>
                <c:if test="${not empty userDetails.firstName}">
                    <p>Full name: ${userDetails.firstName} ${userDetails.lastName}, but they like to be called ${userDetails.nickname}</p>
                </c:if>
                <c:if test="${not empty userDetails.description}">
                    <p>About themselves: ${userDetails.description}</p>
                </c:if>
                <c:if test="${not empty userDetails.birthDate}">
                    <p>Birthday: ${userDetails.birthDate}</p>
                </c:if>
                <c:if test="${not empty userDetails.zodiacSign}">
                    <p>Zodiac Sign: ${userDetails.zodiacSign}</p>
                </c:if>
                <c:if test="${not empty userDetails.location}">
                    <p>Location: ${userDetails.location}</p>
                </c:if>
            </div>
        </div>

        <c:if test="${not friends}">
            <div class="row">
                <div class="col-md-6 offset-md-3">
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
        </c:if>
    </div>
</t:pageTemplate>