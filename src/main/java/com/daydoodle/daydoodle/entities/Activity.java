package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Lob
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    Picture activityPicture;
    public Picture getActivityPicture() {
        return activityPicture;
    }
    public void setActivityPicture(Picture activityPicture) {
        this.activityPicture = activityPicture;
    }

//    @OneToMany(mappedBy = "activity")
//    private List<CalendarEvent> calendarEvents;
//    public List<CalendarEvent> getCalendarEvents() {
//        return calendarEvents;
//    }
//    public void setCalendarEvents(List<CalendarEvent> calendarEvents) {
//        this.calendarEvents = calendarEvents;
//    }
}
