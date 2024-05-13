package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Activity;
import com.daydoodle.daydoodle.entities.Calendar;
import com.daydoodle.daydoodle.entities.CustomActivity;
import com.daydoodle.daydoodle.entities.User;

import java.time.LocalDateTime;

public class CalendarEventDto {

    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private String location;
    private User user;
    private Activity activity;
    private CustomActivity userActivity;
    private Calendar calendar;

    public CalendarEventDto(Long id, String title, LocalDateTime start, LocalDateTime end, String description, String location, User user, Activity activity, CustomActivity userActivity, Calendar calendar) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
        this.location = location;
        this.user = user;
        this.activity = activity;
        this.userActivity = userActivity;
        this.calendar = calendar;
    }
    public CalendarEventDto(String title, LocalDateTime start, LocalDateTime end, String description, String location, User user, Activity activity, CustomActivity userActivity, Calendar calendar) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
        this.location = location;
        this.user = user;
        this.activity = activity;
        this.userActivity = userActivity;
        this.calendar = calendar;
    }
    public CalendarEventDto() {
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public LocalDateTime getStart() {
        return start;
    }
    public LocalDateTime getEnd() {
        return end;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public User getUser() {
        return user;
    }
    public Activity getActivity() {
        return activity;
    }
    public CustomActivity getCustomActivity() {
        return userActivity;
    }
    public Calendar getCalendar() {
        return calendar;
    }
}
