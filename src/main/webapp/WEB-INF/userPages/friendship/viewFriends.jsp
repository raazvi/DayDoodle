<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Friends">
    <div class="page-content">
        <c:if test="${not empty friendRequestsReceived}">
            <h2>Friend Requests:</h2>
            <div class="container text-center">
                <c:forEach var="request" items="${friendRequestsReceived}">
                    <div class="row">
                        <div class="col">
                                ${request.requester.username} sent you a friend request.
                        </div>
                        <div class="col">
                            <form action="${pageContext.request.contextPath}/UserProfile" method="GET">
                                <input type="hidden" name="username" value="${request.requester.username}">
                                <button type="submit">View Profile</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/RespondFriendRequest" method="GET">
                                <input type="hidden" name="username" value="${request.requester.username}">
                                <input type="hidden" name="response" value="accept">
                                <input type="hidden" name="frReqId" value="${request.id}">
                                <button type="submit">Accept</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/RespondFriendRequest?response=decline&frReqId=${request.id}&servlet=ViewFriends&username=${request.requester.username}" method="GET">
                                <input type="hidden" name="username" value="${request.requester.username}">
                                <input type="hidden" name="response" value="decline">
                                <input type="hidden" name="frReqId" value="${request.id}">
                                <button type="submit">Reject</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty friends}">
                <p>You have no friends yet :(</p>
                <p>Search for users using the search bar below:</p>
                <form id="searchForm" onsubmit="return false;">
                    <label for="keyword">Search by username:</label>
                    <input type="text" id="keyword" name="keyword" oninput="searchUser()">
                    <button type="submit">Search</button>
                </form>
                <!-- Suggestions div -->
                <div id="suggestions"></div>
            </c:when>
            <c:otherwise>
                <p>Search for more friends:</p>
                <form id="searchForm" onsubmit="return false;">
                    <label for="keyword">Search by username:</label>
                    <input type="text" id="keyword" name="keyword" oninput="searchUser()">
                    <button type="submit">Search</button>
                </form>
                <!-- Suggestions div -->
                <div id="suggestions"></div>
                <h2>Your friends:</h2>
                <div class="container text-center">
                    <c:forEach var="friend" items="${friends}">
                        <div class="row">
                            <div class="col">
                                    ${friend.friend.username}
                            </div>
                            <div class="col">
                                <form action="${pageContext.request.contextPath}/UserProfile" method="GET">
                                    <input type="hidden" name="username" value="${friend.friend.username}">
                                    <button type="submit">View Profile</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/DeleteFriend" method="GET">
                                    <input type="hidden" name="username" value="${friend.friend.username}">
                                    <input type="hidden" name="friendshipId" value="${friend.id}">
                                    <button type="submit">Delete Friend</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- JavaScript for search functionality -->
    <script>
        function searchUser() {
            var keyword = document.getElementById("keyword").value.trim();
            var suggestionsDiv = document.getElementById("suggestions");

            suggestionsDiv.innerHTML = ''; // Clear previous suggestions

            if (keyword !== '') {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', '${pageContext.request.contextPath}/SearchUser?keyword=' + keyword, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            var suggestions = JSON.parse(xhr.responseText);
                            suggestions.forEach(function (username) {
                                var suggestion = document.createElement('div');
                                suggestion.textContent = username;
                                suggestion.classList.add('suggestion');
                                suggestion.addEventListener('click', function () {
                                    // Redirect to the profile page of the clicked user with the username as a parameter
                                    window.location.href = '${pageContext.request.contextPath}/UserProfile?username=' + encodeURIComponent(username);
                                });
                                suggestionsDiv.appendChild(suggestion);
                            });
                        } else {
                            console.error('Error fetching suggestions:', xhr.status);
                        }
                    }
                };
                xhr.send();
            }
        }
    </script>

</t:pageTemplate>
