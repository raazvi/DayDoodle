package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class User {

    @Id
    private String username;
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    @Basic
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    private boolean firstLogin;
    public boolean isFirstLogin() {
        return firstLogin;
    }
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    //    @OneToMany(mappedBy = "author")
//    private List<Post> posts;
//    public List<Post> getPosts() {
//        return posts;
//    }
//    public void setPosts(List<Post> posts) {
//        this.posts = posts;
//    }

//    @OneToMany(mappedBy = "user")
//    private List<Friendship> friendships;
//    public List<Friendship> getFriendships() {
//        return friendships;
//    }
//    public void setFriendships(List<Friendship> friendships) {
//        this.friendships = friendships;
//    }

    private LocalDate dateJoined;
    public LocalDate getDateJoined() {
        return dateJoined;
    }
    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    @OneToMany(mappedBy="author")
    private List<DiaryPage> diary;
    public List<DiaryPage> getDiaryPages() {
        return diary;
    }
    public void setDiaryPages(List<DiaryPage> diary) {
        this.diary = this.diary;
    }

//    @OneToMany(mappedBy = "user")
//    private List<CalendarEvent> calendarEvents;
//    public List<CalendarEvent> getCalendarEvents() {
//        return calendarEvents;
//    }
//    public void setCalendarEvents(List<CalendarEvent> calendarEvents) {
//        this.calendarEvents = calendarEvents;
//    }
//
//    @ManyToMany
//    @JoinTable(name = "calendar_user", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "calendarId"))
//    private List<Calendar> calendars;
//    public List<Calendar> getCalendars() {
//        return calendars;
//    }
//    public void setCalendars(List<Calendar> calendars) {
//        this.calendars = calendars;
//    }

    public enum Role {
        admin,
        user
    }
    @Enumerated(EnumType.STRING)
    private Role role;
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}