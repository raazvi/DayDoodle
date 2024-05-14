<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pageTemplate pageTitle="News Feed">
    <div class="container">
        <div class="row">
            <div class="col">
                <a href="${pageContext.request.contextPath}/AddPost" class="btn btn-primary mb-3">Add a new post</a>
            </div>
        </div>
    </div>
    <h3>See what you have missed...</h3>
    <div class="container">
        <c:choose>
            <c:when test="${not empty posts}">
                <div class="row">
                    <c:forEach var="post" items="${posts}">
                        <div class="col-md-4">
                            <div class="card mb-4 box-shadow">
                                <div class="card-body">
                                    <p class="card-text">Author: ${post.author.username}</p>
                                    <p class="card-text">Date Posted: ${post.datePosted}</p>
                                    <p class="card-text">
                                        <c:choose>
                                            <c:when test="${not empty post.activity}">
                                                Activity: ${post.activity.name}
                                            </c:when>
                                            <c:otherwise>
                                                Custom Activity: ${post.customActivity.name}
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p class="card-text">${post.caption}</p>
                                    <br>
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
                                            <button class="btn btn-primary" disabled>Star</button>
                                            <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=like" class="btn btn-primary">Like</a>
                                        </c:when>
                                        <c:when test="${userReactedLike}">
                                            <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=star" class="btn btn-primary">Star</a>
                                            <button class="btn btn-primary" disabled>Like</button>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=star" class="btn btn-primary">Star</a>
                                            <a href="${pageContext.request.contextPath}/PostReact?postId=${post.id}&reaction=like" class="btn btn-primary">Like</a>
                                        </c:otherwise>
                                    </c:choose>
                                    <a href="${pageContext.request.contextPath}/viewPostComments?postId=${post.id}" class="btn btn-primary">Comment</a>
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
</t:pageTemplate>
