<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generalTemplate pageTitle="Login">
    <h1>Login to Your Account</h1>

    <j:if test="${message}!= null">
        <div class="alert alert-warning" role="alert">
            ${message}
        </div>
    </j:if>
    <form action="j_security_check" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="j_username" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="j_password" required>
        </div>
        <button type="submit">Login</button>
    </form>
    <p>Don't have an account? <a href="${pageContext.request.contextPath}/Register">Register here</a></p>
</t:generalTemplate>