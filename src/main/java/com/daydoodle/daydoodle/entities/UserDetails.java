package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class UserDetails {

    @Id
    private String username; // Use username as the primary key

    @OneToOne
    @MapsId //Leaga cheia primara din User cu cea din UserDetails
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Basic
    private String firstName;

    @Basic
    private String lastName;

    @Basic
    private String nickname;

    private LocalDate birthDate;

    @Basic
    private String location;

    @Basic
    @Lob
    private String description;

    @Basic
    private String pronouns;

    @Basic
    @Enumerated(EnumType.STRING)
    private ZodiacSign zodiacSign;

    public enum ZodiacSign {
        Aries,
        Taurus,
        Gemini,
        Cancer,
        Leo,
        Virgo,
        Libra,
        Scorpio,
        Sagittarius,
        Capricorn,
        Aquarius,
        Pisces
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Picture profilePicture;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public ZodiacSign getZodiacSign() {
        return zodiacSign;
    }
    public void setZodiacSign(ZodiacSign zodiacSign) {
        this.zodiacSign = zodiacSign;
    }
    public Picture getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getPronouns() {
        return pronouns;
    }
    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
