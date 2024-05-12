package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.User;

import java.time.LocalDate;

public class DiaryPageDto {

    Long entryId;
    User author;
    LocalDate entryDate;
    String title;
    String entryText;
    String mood;
    boolean favourite;

    public DiaryPageDto(Long entryId, User author, LocalDate entryDate, String title, String entryText, String mood, boolean favourite) {
        this.entryId = entryId;
        this.author = author;
        this.entryDate = entryDate;
        this.title = title;
        this.entryText = entryText;
        this.mood = mood;
        this.favourite = favourite;
    }
    public DiaryPageDto(User author, LocalDate entryDate, String title, String entryText, String mood, boolean favourite) {
        this.author = author;
        this.entryDate = entryDate;
        this.title = title;
        this.entryText = entryText;
        this.mood = mood;
        this.favourite = favourite;
    }
    public DiaryPageDto() {
    }

    public Long getEntryId() {
        return entryId;
    }
    public User getAuthor() {
        return author;
    }
    public LocalDate getEntryDate() {
        return entryDate;
    }
    public String getTitle() {
        return title;
    }
    public String getEntryText() {
        return entryText;
    }
    public String getMood() {
        return mood;
    }
    public boolean isFavourite() {
        return favourite;
    }
}
