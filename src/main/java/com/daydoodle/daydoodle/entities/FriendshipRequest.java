package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class FriendshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User requester;

    @ManyToOne
    private User receiver;

    private LocalDate dateSent;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getRequester() {
        return requester;
    }
    public void setRequester(User requester) {
        this.requester = requester;
    }
    public User getReceiver() {
        return receiver;
    }
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
    public LocalDate getDateSent() {
        return dateSent;
    }
    public void setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
    }
    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
