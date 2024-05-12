package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Picture;
import com.daydoodle.daydoodle.entities.User;

public class CustomActivityDto {

    Long id;
    String name;
    String description;
    User user;
    Picture activityPicture;

    public CustomActivityDto(Long id, String name, String description, User user, Picture activityPicture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.activityPicture = activityPicture;
    }
    public CustomActivityDto(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }
    public CustomActivityDto() {
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public User getUser() {
        return user;
    }
    public Picture getActivityPicture() {
        return activityPicture;
    }
}
