package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

@Entity
public class FunFact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Basic
    String fact;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFact() {
        return fact;
    }
    public void setFact(String fact) {
        this.fact = fact;
    }
}
