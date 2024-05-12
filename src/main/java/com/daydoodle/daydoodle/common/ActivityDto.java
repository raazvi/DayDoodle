package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Picture;

public class ActivityDto {
    Long id;
    String name;
    String description;
    Picture activityPicture;

    public ActivityDto(Long id, String name, String description, Picture activityPicture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.activityPicture = activityPicture;
    }
    public ActivityDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public ActivityDto() {
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
    public Picture getActivityPicture() {
        return activityPicture;
    }
}
