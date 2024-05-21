package com.daydoodle.daydoodle.entities;

import com.daydoodle.daydoodle.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import javax.xml.stream.events.Comment;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePosted;

    @Basic
    @Lob
    private String caption;

    @ManyToOne
    @JoinColumn(name = "username")
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReaction> reactions;
    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "customActivityId")
    private CustomActivity customActivity;

    @NotNull(message = "Either activity or custom activity must be provided")
    @AssertTrue(message = "Only one of activity or custom activity must be provided")
    public boolean isActivityOrUserActivitySet() {
        return activity != null ^ customActivity != null;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToOne(cascade = CascadeType.ALL)
    private Picture picture;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public List<PostComment> getComments() {
        return comments;
    }
    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }
    public List<PostReaction> getReactions() {
        return reactions;
    }
    public void setReactions(List<PostReaction> reactions) {
        this.reactions = reactions;
    }
    public Activity getActivity() {
        return activity;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public CustomActivity getCustomActivity() {
        return customActivity;
    }
    public void setCustomActivity(CustomActivity customActivity) {
        this.customActivity = customActivity;
    }
    public List<Notification> getNotifications() {
        return notifications;
    }
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    public Picture getPicture() {
        return picture;
    }
    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
