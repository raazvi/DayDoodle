package com.daydoodle.daydoodle.common;

public class FunFactDto {

    Long id;
    String fact;

    public FunFactDto(Long id, String fact) {
        this.id = id;
        this.fact = fact;
    }
    public FunFactDto() {
    }

    public Long getId() {
        return id;
    }
    public String getFact() {
        return fact;
    }
}
