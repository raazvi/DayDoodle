package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private LocalDate datePosted;
    public LocalDate getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    @Basic
    @Lob
    private String caption;
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
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

}
