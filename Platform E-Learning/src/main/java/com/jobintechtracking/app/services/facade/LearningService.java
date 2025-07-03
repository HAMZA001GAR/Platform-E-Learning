package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.LearningDTO;
import com.jobintechtracking.app.entities.Learning;

import java.util.List;

public interface LearningService {
    LearningDTO save(LearningDTO learningDTO);
    void deleteById(Long id);
    LearningDTO findById(Long id);
    List<LearningDTO> findAll();
    List<LearningDTO> getLearnByStepId(Long stepsId);
    LearningDTO updateLearning(LearningDTO learningDTO);

    void deleteByStepsId(Long stepsId);
    Learning findByStepsId(Long stepsId);
}
