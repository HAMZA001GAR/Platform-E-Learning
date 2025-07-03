package com.jobintechtracking.app.DTO;

public class DoingDTO {

    private Long id;
    private String task;
    private Long stepsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Long getStepsId() {
        return stepsId;
    }

    public void setStepsId(Long stepsId) {
        this.stepsId = stepsId;
    }
}
