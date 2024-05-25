<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generalTemplate pageTitle="Login">
    <div class="login-register">
        <h2 class="form-title">Connect to your account</h2>

        <j:if test="${message != null}">
            <div class="alert-login-register" role="alert">
                    ${message}
            </div>
        </j:if>
        <form action="${pageContext.request.contextPath}/Login" method="post">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" name="j_username" value="${not empty j_username ? j_username : ''}" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="j_password" required>
            </div>
            <button type="submit">Login</button>
        </form>
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/Register">Register here</a></p>
    </div>
</t:generalTemplate>
