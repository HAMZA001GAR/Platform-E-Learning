package com.jobintechtracking.app.DTO;

import com.jobintechtracking.app.entities.Parcours;
import com.jobintechtracking.app.enums.StepProcess;
import lombok.Data;

import java.util.List;
@Data
public class StepsDoingLeranings {

    private String description;
    private int durationInMinutes;
    private Long stepId;
    private String imageUrl;
    private Long parcoursId;
    private StepProcess stepProcess;
    private String title;
    private String descriptionLearning;
    private Long learningId;
    private Long stepsId;
    private String learningtitle;
    private List<String> url;
    private Long doingId;
    private Long stepsIdInDoing;
    private String task;

}
