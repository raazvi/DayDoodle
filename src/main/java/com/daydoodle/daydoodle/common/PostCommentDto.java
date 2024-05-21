package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostCommentDto {

    Long id;
    String content;
    LocalDateTime postedAt;
    User user;
    Post post;

    public PostCommentDto(Long id, String content, LocalDateTime postedAt, User user, Post post) {
        this.id = id;
        this.content = content;
        this.postedAt = postedAt;
        this.user = user;
        this.post = post;
    }
    public PostCommentDto(String content, LocalDateTime postedAt, User user, Post post) {
        this.content = content;
        this.postedAt = postedAt;
        this.user = user;
        this.post = post;
    }
    public PostCommentDto() {
    }

    public String getFormattedPostedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return postedAt.format(formatter);
    }

    public Long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getPostedAt() {
        return postedAt;
    }
    public User getUser() {
        return user;
    }
    public Post getPost() {
        return post;
    }
}
