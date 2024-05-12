<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Home">
    <h3>This is your diary.</h3>
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
        <form action="${pageContext.request.contextPath}/AddDiaryPage" method="GET">
            <button type="submit">Write in your diary</button>
        </form>
    </div>
</t:pageTemplate>
