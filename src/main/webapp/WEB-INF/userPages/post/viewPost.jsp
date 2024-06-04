<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:feedTemplate pageTitle="${post.author.username}'s Post">
    <div class="outer-container"> <!-- Updated container for centering -->
        <div class="inner-container"> <!-- Inner container to hold the content -->
            <div class="post-container">
                <div class="post-header">
                    <c:if test="${not empty authorProfilePicture}">
                        <img class="user-photo" src="data:image/${authorProfilePicture.imageFormat};base64,${authorProfilePicture.base64ImageData}" alt="Author's Profile Picture">
                    </c:if>
                    <h3>${post.author.username}'s Post</h3>
                    <p>Date Posted: ${post.datePosted}</p>
                </div>

                <div class="post-content">
                    <p>${post.caption}</p>
                    <c:if test="${not empty post.picture}">
                        <img class="big-post-image" src="data:image/${post.picture.imageFormat};base64,${post.picture.base64ImageData}" alt="Post picture">
                    </c:if>
                </div>

                <div class="reactions-container">
                    <h4>Reactions</h4>
                    <ul class="reactions-list">
                        <c:forEach var="reaction" items="${reactions}">
                            <li>
                                <c:if test="${not empty userPicturesMap[reaction.user.username]}">
                                    <img class="reaction-photo" src="data:image/${userPicturesMap[reaction.user.username].imageFormat};base64,${userPicturesMap[reaction.user.username].base64ImageData}" alt="User's Profile Picture">
                                </c:if>
                                    ${reaction.user.username} reacted with
                                <c:choose>
                                    <c:when test="${reaction.reactionType eq 'LIKE'}">
                                        <i class="fas fa-thumbs-up"></i>
                                    </c:when>
                                    <c:when test="${reaction.reactionType eq 'STAR'}">
                                        <i class="fas fa-star"></i>
                                    </c:when>
                                </c:choose>
                            </li>
                        </c:forEach>
                        <c:if test="${empty reactions}">
                            <li>No reactions yet.</li>
                        </c:if>
                    </ul>
                </div>

                <div class="comments-container">
                    <h4>Comments</h4>
                    <ul class="comments-list">
                        <c:forEach var="comment" items="${comments}">
                            <li>
                                <div class="comment-header">
                                    <c:if test="${not empty userPicturesMap[comment.user.username]}">
                                        <img class="comment-photo" src="data:image/${userPicturesMap[comment.user.username].imageFormat};base64,${userPicturesMap[comment.user.username].base64ImageData}" alt="User's Profile Picture">
                                    </c:if>
                                    <p>${comment.user.username}</p>
                                </div>
                                <div class="comment-content">
                                    <p>${comment.content}</p>
                                </div>
                                <div class="comment-date">
                                    <p><small>Date Posted: ${comment.formattedPostedAt}</small></p>
                                </div>
                            </li>
                        </c:forEach>
                        <c:if test="${empty comments}">
                            <li>No comments yet.</li>
                        </c:if>
                    </ul>
                </div>

                <div class="comment-form-container">
                    <form action="${pageContext.request.contextPath}/PostComment?postId=${post.id}" method="GET">
                        <input type="hidden" name="postId" value="${post.id}">
                        <label for="commentContent">Add Comment:</label>
                        <textarea id="commentContent" name="commentContent"></textarea>
                        <button type="submit" class="btn-primary-custom">Add Comment</button>
                    </form>
                </div>
            </div>
        </div>
    </div> <!-- End container div -->
</t:feedTemplate>
