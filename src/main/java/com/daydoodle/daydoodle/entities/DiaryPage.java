package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class DiaryPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;
    public Long getEntryId() {
        return entryId;
    }
    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    @ManyToOne
    @JoinColumn(name = "username")
    private User author;
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }

    private LocalDate entryDate;
    public LocalDate getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @Basic
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Lob
    private String entryText;
    public String getEntryText() {
        return entryText;
    }
    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    @Basic
    private String mood;
    public String getMood() {return mood;}
    public void setMood(String mood) {this.mood = mood;}

    @Basic
    boolean favourite;
    public boolean isFavourite() {return favourite;}
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

}
