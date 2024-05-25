<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:pageTemplate pageTitle="Diary Entry">
    <div class="diary-entry-container">
        <h2 class="entry-title">${diaryEntry.title}</h2>
        <div class="entry-content">
            <p class="entry-detail"><strong>Date:</strong> ${diaryEntry.entryDate}</p>
            <p class="entry-detail">You were feeling <strong class="mood-text">${diaryEntry.mood}</strong> when you wrote this.</p>
            <p class="entry-detail"><strong>More Details:</strong> ${diaryEntry.entryText}</p>
        </div>
    </div>
</t:pageTemplate>
