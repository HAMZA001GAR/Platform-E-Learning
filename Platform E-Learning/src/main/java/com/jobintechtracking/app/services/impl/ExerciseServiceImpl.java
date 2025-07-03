package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.DoingDTO;
import com.jobintechtracking.app.entities.Doing;

import com.jobintechtracking.app.mappers.ExerciseMapper;
import com.jobintechtracking.app.repositories.ExerciseRepository;
import com.jobintechtracking.app.repositories.ModuleRepository;
import com.jobintechtracking.app.services.facade.ExerciseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final ModuleRepository stepsRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository , ExerciseMapper exerciseMapper , ModuleRepository stepsRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
        this.stepsRepository = stepsRepository;
    }

    @Override
    @Transactional
    public DoingDTO save(DoingDTO doingDTO) {
        Doing doing = exerciseMapper.convertToEntity ( doingDTO );
        doing.setSteps ( stepsRepository.findById ( doingDTO.getStepsId ( ) ).orElse ( null ) );
        Doing savedDoing = exerciseRepository.save ( doing );
        return exerciseMapper.convertToDTO ( savedDoing );
    }

    @Override
    public DoingDTO findById(Long id) {
        return exerciseRepository.findById ( id )
                .map ( exerciseMapper :: convertToDTO )
                .orElse ( null );
    }

    @Override
    public List <DoingDTO> findAll() {
        return exerciseRepository.findAll ( ).stream ( )
                .map ( exerciseMapper :: convertToDTO )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    @Transactional
    public DoingDTO updateDoing(DoingDTO doingDTO) {
        Doing doing = exerciseMapper.convertToEntity ( doingDTO );
        Doing updatedDoing = exerciseRepository.save ( doing );
        return exerciseMapper.convertToDTO ( updatedDoing );
    }

    @Override
    @Transactional
    public void deleteDoing(Long id) {
        exerciseRepository.deleteById ( id );
    }

    @Override
    public List <DoingDTO> getDoingByStepId(Long stepsId) {
        return exerciseRepository.findByStepsId ( stepsId ).stream ( )
                .map ( exerciseMapper :: convertToDTO )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    @Transactional
    public void deleteByStepsId(Long stepsId) {
        exerciseRepository.deleteByStepsId ( stepsId );
    }
}
