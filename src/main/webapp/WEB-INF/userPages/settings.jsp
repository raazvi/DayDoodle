<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:pageTemplate pageTitle="Settings">
    <div class="content">
        <h3 class="form-title">Settings page</h3>

        <h2>Here you can change your password:</h2>

        <!-- Change Password Form -->
        <div class="login-register">
            <form action="${pageContext.request.contextPath}/ChangeDetails?detail=password" method="post">
                <label for="oldPassword">Old Password:</label>
                <input type="password" id="oldPassword" name="oldPassword" required>

                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword" required>

                <label for="confirmNewPassword">Confirm New Password:</label>
                <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>

                <button type="submit">Change Password</button>
            </form>
        </div>

    </div>
</t:pageTemplate>
