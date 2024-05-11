package com.daydoodle.daydoodle.common;

import java.time.LocalDate;

public class UserDto {

    String username;
    String email;
    String password;
    LocalDate dateJoined;
    boolean firstLogin;

    public UserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public UserDto(String username, String email, String password, LocalDate dateJoined) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateJoined = dateJoined;
    }
    public UserDto(String username, String email, String password, LocalDate dateJoined,  boolean firstLogin) {
        this.username = username;
        this.firstLogin = firstLogin;
        this.dateJoined = dateJoined;
        this.password = password;
        this.email = email;
    }
    public UserDto() {}

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public LocalDate getDateJoined() {
        return dateJoined;
    }
    public boolean isFirstLogin() {
        return firstLogin;
    }
}