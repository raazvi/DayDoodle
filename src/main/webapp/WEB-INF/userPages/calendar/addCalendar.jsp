<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:pageTemplate pageTitle="Create a calendar">
    <h3>Create a new calendar</h3>
    <form method="POST"  action="${pageContext.request.contextPath}/AddCalendar">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="title">Title:</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="" value="The name of the calendar...">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="description">Describe the purpose of the calendar:</label>
                <input type="text" class="form-control" id="description" name="description" placeholder="" value="" style="height: 300px;">
            </div>
        </div>
        <input type="submit" value="Create Calendar">
    </form>
</t:pageTemplate>