package com.jobintechtracking.app.DTO;

import com.jobintechtracking.app.enums.StepProcess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class StepsDTO {
    private Long id;
    private String title;

    private String description;
    private int durationInMinutes;
    private Long parcoursId;
    private StepProcess stepProcess;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


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

    public Long getParcoursId() {
        return parcoursId;
    }

    public void setParcoursId(Long parcoursId) {
        this.parcoursId = parcoursId;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getDescription() {
        return description;
    }

    public StepProcess getStepProcess() {
        return stepProcess;
    }

    public void setStepProcess(StepProcess stepProcess) {
        this.stepProcess = stepProcess;
    }
}
