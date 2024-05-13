package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.User;

import java.time.LocalDate;

public class FriendshipRequestDto {

    private Long id;
    private User requester;
    private User receiver;
    private String status;
    private LocalDate dateSent;

    public FriendshipRequestDto(Long id, User requester, User receiver, String status, LocalDate dateSent) {
        this.id = id;
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
        this.dateSent = dateSent;
    }
    public FriendshipRequestDto(User requester, User receiver, String status, LocalDate dateSent) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
        this.dateSent = dateSent;
    }
    public FriendshipRequestDto() {
    }

    public Long getId() {
        return id;
    }
    public User getRequester() {
        return requester;
    }
    public User getReceiver() {
        return receiver;
    }
    public String getStatus() {
        return status;
    }
    public LocalDate getDateSent() {
        return dateSent;
    }
}
