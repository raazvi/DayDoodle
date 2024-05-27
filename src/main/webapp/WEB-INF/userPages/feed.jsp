<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:feedTemplate pageTitle="News Feed">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/AddPost" class="btn btn-primary-custom mb-3">Add a new post</a>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <c:if test="${not empty funFact}">
                    <div class="fun-fact-cloud">
                        <p>${funFact.fact}</p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <h3>See what you have missed...</h3>
    <div class="container">
        <c:choose>
            <c:when test="${not empty posts}">
                <div class="row">
                    <c:forEach var="post" items="${posts}">
                        <div class="col-md-4 d-flex align-items-stretch">
                            <div class="card mb-4 box-shadow">
                                <div class="card-body">
                                    <!-- Display profile picture and user name -->
                                    <div class="user-info">
                                        <c:set var="profilePicture" value="${friendsProfilePicturesMap[post.author.username]}"/>
                                        <c:if test="${not empty profilePicture}">
                                            <img src="data:image/${profilePicture.imageFormat};base64,${profilePicture.base64ImageData}" alt="Profile Picture" class="user-photo">
                                        </c:if>
                                        <p class="card-text mb-0">${post.author.username}</p>
                                    </div>

                                    <!-- Display date posted -->
                                    <p class="card-text">Date Posted: ${post.datePosted}</p>

                                    <!-- Display caption -->
                                    <p class="card-text">${post.caption}</p>

                                    <!-- Display post picture -->
                                    <c:if test="${not empty post.picture}">
                                        <img class="card-img-top" src="data:image/${post.picture.imageFormat};base64,${post.picture.base64ImageData}" alt="Post picture">
                                    </c:if>

                                    <!-- Reaction and comment buttons -->
                                    <div class="reaction-buttons">
                                        <c:set var="userReactedLike" value="false"/>
                                        <c:set var="userReactedStar" value="false"/>
                                        <c:forEach var="reaction" items="${post.reactions}">
                                            <c:if test="${reaction.user.username eq sessionScope.user.username}">
                                                <c:choose>
                                                    <c:when test="${reaction.reactionType eq 'LIKE'}">
                                                        <c:set var="userReactedLike" value="true"/>
                                                    </c:when>
                                                    <c:when test="${reaction.reactionType eq 'STAR'}">
                                                        <c:set var="userReactedStar" value="true"/>
                                                    </c:when>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>

                                        <c:choose>
                                            <c:when test="${userReactedStar}">
                                                <button class="btn-link" disabled><i class="fas fa-star"></i></button>
                                                <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=like" class="btn-link"><i class="fas fa-thumbs-up"></i></a>
                                            </c:when>
                                            <c:when test="${userReactedLike}">
                                                <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=star" class="btn-link"><i class="fas fa-star"></i></a>
                                                <button class="btn-link" disabled><i class="fas fa-thumbs-up"></i></button>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=star" class="btn-link"><i class="fas fa-star"></i></a>
                                                <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=like" class="btn-link"><i class="fas fa-thumbs-up"></i></a>
                                            </c:otherwise>
                                        </c:choose>
                                        <a href="${pageContext.request.contextPath}/ViewPost?postId=${post.id}" class="btn-link"><i class="fas fa-comment"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p>Nothing to see for now...</p>
            </c:otherwise>
        </c:choose>
    </div>
</t:feedTemplate>
