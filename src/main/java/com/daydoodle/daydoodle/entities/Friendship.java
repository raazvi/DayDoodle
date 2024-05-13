package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateCreated;

    @ManyToOne
    private User user;

    @ManyToOne
    private User friend;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getFriend() {
        return friend;
    }
    public void setFriend(User friend) {
        this.friend = friend;
    }
}
