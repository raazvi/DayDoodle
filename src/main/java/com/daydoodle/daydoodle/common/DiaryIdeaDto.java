package com.daydoodle.daydoodle.common;

public class DiaryIdeaDto {

    Long id;
    String prompt;

    public DiaryIdeaDto(Long id, String prompt) {
        this.id = id;
        this.prompt = prompt;
    }
    public DiaryIdeaDto() {
    }

    public Long getId() {
        return id;
    }
    public String getPrompt() {
        return prompt;
    }
}
