package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.User;

import java.time.LocalDate;

public class FriendshipDto {

    Long id;
    LocalDate dateCreated;
    User user;
    User friend;

    public FriendshipDto(Long id, LocalDate dateCreated, User user, User friend) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.user = user;
        this.friend = friend;
    }
    public FriendshipDto(LocalDate dateCreated, User user, User friend) {
        this.dateCreated = dateCreated;
        this.user = user;
        this.friend = friend;
    }
    public FriendshipDto() {
    }

    public Long getId() {
        return id;
    }
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    public User getUser() {
        return user;
    }
    public User getFriend() {
        return friend;
    }
}
