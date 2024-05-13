<t:pageTemplate pageTitle="Diary">
    <h3>Welcome to Your Diary!</h3>
    <p>Express yourself, reflect on your thoughts, and capture your experiences. Your diary is a safe space for your innermost thoughts.</p>
    <div>
        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="page" items="${pages}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/ViewDiaryPage?pageId=${page.entryId}">${page.title}</a></td>
                    <td>${page.entryDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div>
        <p>Don't hesitate to start writing! Click the button below to add a new entry to your diary.</p>
        <form action="${pageContext.request.contextPath}/AddDiaryPage" method="GET">
            <button type="submit">Write in your diary</button>
        </form>
    </div>
</t:pageTemplate>
