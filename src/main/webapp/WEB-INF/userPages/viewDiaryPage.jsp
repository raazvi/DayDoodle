<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:pageTemplate pageTitle="Diary Entry">
    <h2>Diary Entry Details</h2>
    <div>
        <p><strong>Title:</strong> ${diaryEntry.title}</p>
        <p><strong>Date:</strong> ${diaryEntry.entryDate}</p>
        <p>You were feeling <strong> ${diaryEntry.mood} </strong> when you wrote this.</p>
        <p><strong>More Details:</strong> ${diaryEntry.entryText}</p>
    </div>
</t:pageTemplate>
