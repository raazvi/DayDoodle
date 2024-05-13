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
