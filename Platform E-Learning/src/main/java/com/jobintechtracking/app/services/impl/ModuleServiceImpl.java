package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.StepsDTO;
import com.jobintechtracking.app.DTO.StepsDoingLeranings;
import com.jobintechtracking.app.entities.Doing;
import com.jobintechtracking.app.entities.Learning;
import com.jobintechtracking.app.entities.Parcours;
import com.jobintechtracking.app.entities.Steps;
import com.jobintechtracking.app.mappers.ModuleMapper;
import com.jobintechtracking.app.repositories.*;
import com.jobintechtracking.app.services.facade.ImageService;
import com.jobintechtracking.app.services.facade.ModuleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final ParcoursRepository parcoursRepository;

    private final LearningRepository learningRepository;

    private final ExerciseRepository exerciseRepository;

    private final StudentModuleRepository studentModuleRepository;
    private final ImageService imageService;


    public ModuleServiceImpl(ModuleRepository moduleRepository , ModuleMapper moduleMapper , ParcoursRepository parcoursRepository , LearningRepository learningRepository , ExerciseRepository exerciseRepository , StudentModuleRepository studentModuleRepository
                             , ImageService imageService)
    {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
        this.parcoursRepository = parcoursRepository;
        this.learningRepository = learningRepository;
        this.exerciseRepository = exerciseRepository;
        this.studentModuleRepository = studentModuleRepository;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public StepsDTO saveStep(StepsDTO stepsDTO , MultipartFile imageFile) throws IOException {
        Steps steps = moduleMapper.convertToEntity ( stepsDTO );
        steps.setParcours ( parcoursRepository.findById ( stepsDTO.getParcoursId ( ) ).orElse ( null ) );

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "uploads/";

            Files.createDirectories(Paths.get(uploadDir)); // Ensure folder exists
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, imageFile.getBytes());

            steps.setImageUrl("/uploads/" + fileName); // Relative path for DB
        }

        Steps savedStep = moduleRepository.save ( steps );
        return moduleMapper.convertToDTO ( savedStep );
    }


    @Override
    public StepsDTO findById(Long id) {
        return moduleRepository.findById ( id )
                .map ( moduleMapper :: convertToDTO )
                .orElse ( null );
    }

    @Override
    public List <StepsDTO> findAll() {
        return moduleRepository.findAll ( ).stream ( )
                .map ( moduleMapper :: convertToDTO )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    @Transactional
    public StepsDTO updateStep(StepsDTO stepsDTO , MultipartFile file) {
        Steps steps = moduleMapper.convertToEntity ( stepsDTO );
        if (file != null && !file.isEmpty ( )) {
            String blobFileUrl = steps.getImageUrl ( );
            String imageUrl = imageService.updateFile ( blobFileUrl , file );
            steps.setImageUrl ( imageUrl );
        }
        steps = moduleRepository.save ( steps );
        return moduleMapper.convertToDTO ( steps );
    }

    @Override
    @Transactional
    public StepsDTO updateStepsWithDoingAndLearning(StepsDoingLeranings stepsDoingLeranings , MultipartFile imageFile )  throws  IOException {

        Steps step = moduleRepository.findById ( stepsDoingLeranings.getStepsId ( ) )
                .orElseThrow ( () -> new EntityNotFoundException ( "Step not found" ) );

        step.setDescription ( stepsDoingLeranings.getDescription ( ) );
        step.setDurationInMinutes ( stepsDoingLeranings.getDurationInMinutes ( ) );
        Parcours parcours = parcoursRepository.findById ( stepsDoingLeranings.getParcoursId ( ) )
                .orElseThrow ( () -> new EntityNotFoundException ( "Parcours not found" ) );
        step.setParcours ( parcours );
        step.setStepProcess ( stepsDoingLeranings.getStepProcess ( ) );
        step.setTitle ( stepsDoingLeranings.getTitle ( ) );

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "uploads/";
            Files.createDirectories ( Paths.get ( uploadDir ) );
            Path path = Paths.get(uploadDir, fileName);
            Files.write(path, imageFile.getBytes());

            step.setImageUrl("/uploads/" + fileName);
        } else {
            step.setImageUrl(stepsDoingLeranings.getImageUrl()); // Keep old URL if no new image
        }

        moduleRepository.save ( step );

        Learning learning = learningRepository.findById ( stepsDoingLeranings.getLearningId ( ) )
                .orElseThrow ( () -> new EntityNotFoundException ( "learning not found" ) );
        learning.setTitle ( stepsDoingLeranings.getLearningtitle ( ) );
        learning.setDescription ( stepsDoingLeranings.getDescriptionLearning ( ) );
        learning.setUrl ( stepsDoingLeranings.getUrl ( ) );

        learningRepository.save ( learning );

        // Update Doing
        Doing doing = exerciseRepository.findById ( stepsDoingLeranings.getDoingId ( ) )
                .orElseThrow ( () -> new EntityNotFoundException ( "Doing not found" ) );

        doing.setTask ( stepsDoingLeranings.getTask ( ) );
        // Other fields if needed

        exerciseRepository.save ( doing );


        return moduleMapper.convertToDTO ( step );
    }

    @Override
    @Transactional
    public void deleteStep(Long id) {
        // Delete from Learning
        learningRepository.deleteByStepsId ( id );
        // Delete from Doing
        exerciseRepository.deleteByStepsId ( id );
        // Delete from StudentStep
        studentModuleRepository.deleteByStepId ( id );
        // Finally delete the step itself
        moduleRepository.deleteById ( id );
    }


    @Override
    public Page <StepsDTO> getStepsByParcoursId(Long parcoursId , Pageable pageable) {
        Page <Steps> stepsPage = moduleRepository.findByParcoursId ( parcoursId , pageable );
        // Sort the stepsPage content
        List <Steps> sortedSteps = stepsPage.getContent ( ).stream ( )
                .sorted ( Comparator.comparing ( Steps :: getId ) )
                .collect ( Collectors.toList ( ) );
        // Return a new PageImpl with the sorted list and original pageable information
        return new PageImpl <> ( sortedSteps , pageable , stepsPage.getTotalElements ( ) ).map ( moduleMapper :: convertToDTO );
    }

    @Override
    public Optional <StepsDTO> getStepById(Long id) {
        return moduleRepository.findById ( id )
                .map ( moduleMapper :: convertToDTO );
    }


    public Map <Long, Long> countStepsByFormationId(Long formationId) {
        List <Long> parcoursIds = parcoursRepository.findParcoursIdsByFormationId ( formationId );
        Map <Long, Long> stepCounts = new HashMap <> ( );

        for (Long parcoursId : parcoursIds) {
            Long count = moduleRepository.countByParcoursId ( parcoursId );
            stepCounts.put ( parcoursId , count );
        }

        return stepCounts;
    }
}
