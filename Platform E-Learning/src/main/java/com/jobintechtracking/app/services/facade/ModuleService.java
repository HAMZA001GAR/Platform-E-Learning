package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.StepsDTO;
import com.jobintechtracking.app.DTO.StepsDoingLeranings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ModuleService {

    StepsDTO saveStep(StepsDTO stepsDTO, MultipartFile imageFile) throws IOException;

    StepsDTO findById(Long id);

    List<StepsDTO> findAll();

    public StepsDTO updateStep(StepsDTO stepsDTO, MultipartFile file);
    StepsDTO  updateStepsWithDoingAndLearning (StepsDoingLeranings stepsDoingLeranings , MultipartFile imageFile) throws IOException;

    void deleteStep(Long id);

    Page<StepsDTO> getStepsByParcoursId(Long parcoursId, Pageable pageable);

    Optional<StepsDTO> getStepById(Long id);

    Map<Long, Long> countStepsByFormationId(Long formationId);
}
