package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.*;

import java.time.LocalDate;
import java.util.List;

public class PostDto {

    Long id;
    LocalDate datePosted;
    String caption;
    User author;
    Activity activity;
    CustomActivity customActivity;
    List<PostComment> comments;
    List<PostReaction> reactions;
    Picture picture;

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
    public PostDto(Long id, LocalDate datePosted, String caption, User author, Activity activity, List<PostComment> comments, List<PostReaction> reactions, Picture picture) {
        this.id = id;
        this.datePosted = datePosted;
        this.caption = caption;
        this.author = author;
        this.activity = activity;
        this.comments = comments;
        this.reactions = reactions;
        this.picture = picture;
    }
    public PostDto(Long id, LocalDate datePosted, String caption, User author, CustomActivity customActivity, List<PostComment> comments, List<PostReaction> reactions, Picture picture) {
        this.id = id;
        this.datePosted = datePosted;
        this.caption = caption;
        this.author = author;
        this.customActivity = customActivity;
        this.comments = comments;
        this.reactions = reactions;
        this.picture = picture;
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
    public List<PostComment> getComments() {
        return comments;
    }
    public List<PostReaction> getReactions() {
        return reactions;
    }
    public Picture getPicture() {
        return picture;
    }
    public int getCommentsCount() {
        return comments != null ? comments.size() : 0;
    }
    public int getReactionsCount() {
        return reactions != null ? reactions.size() : 0;
    }
}
