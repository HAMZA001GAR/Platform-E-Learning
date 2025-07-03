package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.DoingDTO;

import java.util.List;

public interface ExerciseService {

    DoingDTO save(DoingDTO doingDTO);
    DoingDTO findById(Long id);
    List<DoingDTO> findAll();
    DoingDTO updateDoing(DoingDTO doingDTO);
    void deleteDoing(Long id);
    List<DoingDTO> getDoingByStepId(Long stepsId);

    void deleteByStepsId(Long stepsId);

}
