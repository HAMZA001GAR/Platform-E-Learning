package com.jobintechtracking.app.DTO;

import java.util.List;

public class LearningDTO {

    private Long id;
    private String title;
    private String description;
    private List<String> url;
    private Long stepsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public Long getStepsId() {
        return stepsId;
    }

    public void setStepsId(Long stepsId) {
        this.stepsId = stepsId;
    }
}
