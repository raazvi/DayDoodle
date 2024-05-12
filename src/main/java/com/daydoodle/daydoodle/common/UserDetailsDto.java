package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Picture;
import com.daydoodle.daydoodle.entities.UserDetails;

import java.time.LocalDate;

public class UserDetailsDto {

    String username;
    String firstName;
    String lastName;
    String nickname;
    LocalDate birthDate;
    String location;
    String description;
    String pronouns;
    Picture profilePicture;
    UserDetails.ZodiacSign zodiacSign;

    public UserDetailsDto(String username, String firstName, String lastName, String nickname, String description, String pronouns, String location, LocalDate birthDate, Picture profilePicture, UserDetails.ZodiacSign zodiacSign) {
        this.username = username;
        this.zodiacSign = zodiacSign;
        this.profilePicture = profilePicture;
        this.pronouns = pronouns;
        this.description = description;
        this.location = location;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.lastName = lastName;
        this.firstName = firstName;
    }
    public UserDetailsDto() {
    }

    public String getUsername() {
        return username;
    }
    public UserDetails.ZodiacSign getZodiacSign() {
        return zodiacSign;
    }
    public Picture getProfilePicture() {
        return profilePicture;
    }
    public String getPronouns() {
        return pronouns;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public String getNickname() {
        return nickname;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
}
