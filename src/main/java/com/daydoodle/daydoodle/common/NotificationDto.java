package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Notification;
import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationDto {

    Long id;
    String message;
    boolean seen;
    LocalDateTime createdAt;
    User recipient;
    Notification.NotificationType type;
    Post post;

    public NotificationDto(Long id, String message, boolean seen, LocalDateTime createdAt, User recipient, Notification.NotificationType type, Post post) {
        this.id = id;
        this.message = message;
        this.seen = seen;
        this.createdAt = createdAt;
        this.recipient = recipient;
        this.type = type;
        this.post = post;
    }
    public NotificationDto(String message, boolean seen, LocalDateTime createdAt, User recipient, Notification.NotificationType type, Post post) {
        this.message = message;
        this.seen = seen;
        this.createdAt = createdAt;
        this.recipient = recipient;
        this.type = type;
        this.post = post;
    }
    public NotificationDto() {
    }

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }

    public Long getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public boolean isSeen() {
        return seen;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public User getRecipient() {
        return recipient;
    }
    public Notification.NotificationType getType() {
        return type;
    }
    public Post getPost() {
        return post;
    }
}
