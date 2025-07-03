package com.jobintechtracking.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentStepDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String taskUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean stepStatus;

    private Long studentId;
    private Long stepId;
    private Long parcoursId;
}
