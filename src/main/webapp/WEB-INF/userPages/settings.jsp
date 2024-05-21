<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:pageTemplate pageTitle="Settings">
    <h3>Settings page</h3>

    <p>Here you can change your password and username:</p>

    <!-- Change Password Form -->
    <form action="${pageContext.request.contextPath}/ChangeDetails?detail=password" method="post">
        <label for="oldPassword">Old Password:</label>
        <input type="password" id="oldPassword" name="oldPassword" required><br>
        <label for="newPassword">New Password:</label>
        <input type="password" id="newPassword" name="newPassword" required><br>
        <label for="confirmNewPassword">Confirm New Password:</label>
        <input type="password" id="confirmNewPassword" name="confirmNewPassword" required><br>
        <button type="submit">Change Password</button>
    </form>

    <!-- Change Username Form -->
    <!--
    <form action="${pageContext.request.contextPath}/ChangeDetails?detail=username" method="post">
        <label for="currentUsername">Your Current Username:</label>
        <input type="text" id="currentUsername" name="currentUsername" value="${user.username}" disabled><br>
        <label for="newUsername">New Username:</label>
        <input type="text" id="newUsername" name="newUsername" required><br>
        <button type="submit">Change Username</button>
    </form>
    -->
</t:pageTemplate>
