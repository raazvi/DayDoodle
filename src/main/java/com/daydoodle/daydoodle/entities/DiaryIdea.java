package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

@Entity
public class DiaryIdea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Basic
    String prompt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPrompt() {
        return prompt;
    }
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
