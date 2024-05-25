<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Diary">
    <div class="diary-container">
        <h3 class="welcome-title">Welcome to Your Diary!</h3>
        <p class="welcome-message">Express yourself, reflect on your thoughts, and capture your experiences. Your diary is a safe space for your innermost thoughts.</p>
        <div class="idea-section">
            <p>If you don't feel inspired, here's something to reflect on, maybe you can write about it:</p>
            <div class="idea-cloud">
                <c:if test="${not empty diaryIdeas}">
                    <p id="diaryIdea" class="idea-text">${diaryIdeas[0].prompt}</p>
                    <button id="newIdeaButton" class="idea-button">Shuffle Ideas</button>
                </c:if>
            </div>
        </div>
        <div class="diary-pages-section">
            <c:if test="${empty pages}">
                <p class="no-pages">No pages yet.</p>
            </c:if>
            <c:if test="${not empty pages}">
                <table class="diary-table">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="page" items="${pages}">
                        <tr class="diary-row">
                            <td><a href="${pageContext.request.contextPath}/ViewDiaryPage?pageId=${page.entryId}" class="page-link">${page.title}</a></td>
                            <td>${page.entryDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        <div class="new-entry-section">
            <p>Don't hesitate to start writing! Click the button below to add a new entry to your diary.</p>
            <form action="${pageContext.request.contextPath}/AddDiaryPage" method="GET">
                <button type="submit" class="submit-btn">Write in your diary</button>
            </form>
        </div>
    </div>
    <script>
        document.getElementById("newIdeaButton").addEventListener("click", function() {
            fetch('${pageContext.request.contextPath}/DiaryIdea')
                .then(response => response.text())
                .then(data => {
                    document.getElementById("diaryIdea").innerText = data;
                });
        });
    </script>
</t:pageTemplate>
