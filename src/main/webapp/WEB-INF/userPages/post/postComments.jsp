<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="${post.author.username}'s Post">
    <h1>Welcome to DayDoodle!</h1>
    <div>
        <h3>${post.author.username}'s Post</h3>
        <p>Date Posted: ${post.datePosted}</p>
        <p>${post.caption}</p>
    </div>

    <div>
        <h4>Reactions</h4>
        <ul>
            <c:forEach var="reaction" items="${reactions}">
                <li>${reaction.user.username} reacted with ${reaction.reactionType}</li>
            </c:forEach>
            <c:if test="${empty reactions}">
                <li>No reactions yet.</li>
            </c:if>
        </ul>
    </div>

    <div style="max-height: 300px; overflow-y: auto;">
        <h4>Comments</h4>
        <ul>
            <c:forEach var="comment" items="${comments}">
                <li>
                    <p>${comment.content}</p>
                    <p>Posted by: ${comment.user.username}</p>
                    <p> <small> Date Posted: ${comment.formattedPostedAt} </small></p>
                </li>
            </c:forEach>
            <c:if test="${empty comments}">
                <li>No comments yet.</li>
            </c:if>
        </ul>
    </div>

    <div>
        <form action="${pageContext.request.contextPath}/PostComment?postId=${post.id}" method="GET">
            <input type="hidden" name="postId" value="${post.id}">
            <label for="commentContent">Add Comment:</label>
            <textarea id="commentContent" name="commentContent"></textarea>
            <button type="submit">Add Comment</button>
        </form>
    </div>

</t:pageTemplate>
