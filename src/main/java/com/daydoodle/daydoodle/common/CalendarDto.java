package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.CalendarEvent;
import com.daydoodle.daydoodle.entities.User;

import java.util.List;

public class CalendarDto {

    Long id;
    String name;
    String description;
    private List<User> users;
    private List<CalendarEvent> events;

    public CalendarDto(Long id, String name, String description, List<User> users, List<CalendarEvent> events) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.users = users;
        this.events = events;
    }
    public CalendarDto(String name, String description, List<User> users, List<CalendarEvent> events) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.events = events;
    }
    public CalendarDto() {
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
    public List<User> getUsers() {
        return users;
    }
    public List<CalendarEvent> getEvents() {
        return events;
    }
}
