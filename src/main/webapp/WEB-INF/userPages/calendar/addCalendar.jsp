<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:pageTemplate pageTitle="Create a calendar">
    <div class="form-container">
        <h3>Create a new calendar</h3>
        <form method="POST" action="${pageContext.request.contextPath}/AddCalendar">
            <div class="row">
                <div class="col-md-6 mb-3 form-group">
                    <label for="title">Title:</label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="The name of the calendar..." value="">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3 form-group">
                    <label for="description">Describe the purpose of the calendar:</label>
                    <input type="text" class="form-control" id="description" name="description" placeholder="" value="" style="height: 300px;">
                </div>
            </div>
            <input type="submit" class="submit-btn" value="Create Calendar">
        </form>
    </div>
</t:pageTemplate>
