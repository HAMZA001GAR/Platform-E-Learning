package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.StepWithStatusDTO;
import com.jobintechtracking.app.DTO.StudentStepDTO;
import com.jobintechtracking.app.entities.StudentStep;


import java.util.List;
import java.util.Optional;

public interface StudentStepService {

    List<StudentStep> getStudentSteps(Long studentId);
    Optional<StudentStep> getStudentStep(Long studentId, Long stepId);
    StudentStep startStep(Long studentId, Long stepId);
    StudentStep completeStep(Long studentId, Long stepId, String taskUrl);
    List<StepWithStatusDTO> getStepsWithCompletionStatus(Long studentId, Long parcoursId);
    void checkAndCompleteSteps();
    List<Object[]> findStepCounts();
    List<StudentStepDTO> getTheSteps(Long stepId);
    void deleteStudentStep(Long studentId ,Long stepId, Long parcoursId);
    List<Object[]> findCompletedStepsByParcoursId(Long parcoursId, Long studentId);

}
