package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.LearningDTO;
import com.jobintechtracking.app.entities.Learning;
import com.jobintechtracking.app.entities.Steps;
import com.jobintechtracking.app.mappers.LearningMapper;
import com.jobintechtracking.app.repositories.LearningRepository;
import com.jobintechtracking.app.repositories.ModuleRepository;
import com.jobintechtracking.app.services.facade.LearningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearningServiceImpl implements LearningService {

    private final Logger logger = LoggerFactory.getLogger ( LearningServiceImpl.class );
    private final LearningRepository learningRepository;
    private final LearningMapper learningMapper;
    private final ModuleRepository moduleRepository;

    @Autowired
    public LearningServiceImpl(LearningRepository learningRepository , LearningMapper learningMapper , ModuleRepository moduleRepository) {
        this.learningRepository = learningRepository;
        this.learningMapper = learningMapper;
        this.moduleRepository = moduleRepository;
    }


    @Override
    @Transactional
    public LearningDTO save(LearningDTO learningDTO) {
        if (learningDTO == null || learningDTO.getStepsId ( ) == null) {
            logger.error ( "Invalid LearningDTO or Steps ID is null: {}" , learningDTO );
            throw new IllegalArgumentException ( "LearningDTO and Steps ID must not be null" );
        }

        logger.debug ( "Saving LearningDTO: {}" , learningDTO );

        Steps steps = moduleRepository.findById ( learningDTO.getStepsId ( ) ).orElse ( null );
        if (steps == null) {
            logger.error ( "Invalid Steps ID: {}" , learningDTO.getStepsId ( ) );
            throw new IllegalArgumentException ( "Invalid Steps ID" );
        }

        Learning learning = learningMapper.convertToEntity ( learningDTO );
        learning.setSteps ( steps );
        Learning savedLearning = learningRepository.save ( learning );
        LearningDTO savedLearningDTO = learningMapper.convertToDTO ( savedLearning );

        logger.debug ( "Saved LearningDTO: {}" , savedLearningDTO );
        return savedLearningDTO;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        learningRepository.deleteById ( id );
    }

    @Override
    public LearningDTO findById(Long id) {
        return learningRepository.findById ( id )
                .map ( learningMapper :: convertToDTO )
                .orElse ( null );
    }

    @Override
    public List <LearningDTO> findAll() {
        List <Learning> learnings = learningRepository.findAll ( );
        return learnings.stream ( )
                .map ( learningMapper :: convertToDTO )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    public List <LearningDTO> getLearnByStepId(Long stepsId) {
        List <Learning> learnings = learningRepository.findBystepsId ( stepsId );
        return learnings.stream ( )
                .map ( learningMapper :: convertToDTO )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    @Transactional
    public LearningDTO updateLearning(LearningDTO learningDTO) {
        Learning learning = learningMapper.convertToEntity ( learningDTO );
        Learning updatedLearning = learningRepository.save ( learning );
        return learningMapper.convertToDTO ( updatedLearning );
    }

    @Override
    @Transactional
    public Learning findByStepsId(Long stepsId) {
        return learningRepository.findByStepsId ( stepsId );
    }

    @Override
    @Transactional
    public void deleteByStepsId(Long stepsId) {

        Learning learning = learningRepository.findByStepsId ( stepsId );
        if (learning != null) {
            learning.getUrl ( ).clear ( );
        }
        learningRepository.deleteByStepsId ( stepsId );
    }

}
