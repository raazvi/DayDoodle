package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Activity;
import com.daydoodle.daydoodle.entities.CustomActivity;
import com.daydoodle.daydoodle.entities.User;

import java.time.LocalDate;

public class PostDto {

    Long id;
    LocalDate datePosted;
    String caption;
    User author;
    Activity activity;
    CustomActivity customActivity;

    public PostDto(Long id, LocalDate datePosted, String caption, User author, Activity activity, CustomActivity customActivity) {
        this.id = id;
        this.datePosted = datePosted;
        this.caption = caption;
        this.author = author;
        this.activity = activity;
        this.customActivity = customActivity;
    }
    public PostDto(LocalDate datePosted, String caption, User author, Activity activity, CustomActivity customActivity) {
        this.datePosted = datePosted;
        this.caption = caption;
        this.author = author;
        this.activity = activity;
        this.customActivity = customActivity;
    }
    public PostDto() {
    }
    public PostDto(Long id, LocalDate datePosted, String caption, User author, Activity activity) {
        this.id = id;
        this.datePosted = datePosted;
        this.caption = caption;
        this.author = author;
        this.activity = activity;
    }
    public PostDto(Long id, LocalDate datePosted, String caption, User author, CustomActivity customActivity) {
        this.id = id;
        this.datePosted = datePosted;
        this.caption = caption;
        this.author = author;
        this.customActivity = customActivity;
    }

    public Long getId() {
        return id;
    }
    public LocalDate getDatePosted() {
        return datePosted;
    }
    public String getCaption() {
        return caption;
    }
    public User getAuthor() {
        return author;
    }
    public Activity getActivity() {
        return activity;
    }
    public CustomActivity getCustomActivity() {
        return customActivity;
    }
}
