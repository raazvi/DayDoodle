<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generalTemplate pageTitle="Register">
    <div class="login-register">
        <h2 class="form-title">Create Your Account</h2>

        <j:if test="${not empty errorMessage}">
            <div class="alert-login-register" role="alert">
                    ${errorMessage}
            </div>
        </j:if>

        <form action="${pageContext.request.contextPath}/Register" method="post">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${empty email ? '' : email}" required>
            </div>
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${empty username ? '' : username}" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value="${empty password ? '' : password}" required>
            </div>
            <div>
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="${pageContext.request.contextPath}/Login">Login here</a></p>
    </div>
</t:generalTemplate>
