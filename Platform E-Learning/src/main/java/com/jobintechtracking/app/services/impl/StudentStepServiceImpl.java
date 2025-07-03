package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.StepWithStatusDTO;
import com.jobintechtracking.app.DTO.StudentStepDTO;
import com.jobintechtracking.app.entities.Steps;
import com.jobintechtracking.app.entities.Student;
import com.jobintechtracking.app.entities.StudentStep;
import com.jobintechtracking.app.mappers.ModuleWithStatusMapper;
import com.jobintechtracking.app.mappers.StudentStepMapper;
import com.jobintechtracking.app.repositories.ModuleRepository;
import com.jobintechtracking.app.repositories.StudentModuleRepository;
import com.jobintechtracking.app.repositories.StudentRepository;
import com.jobintechtracking.app.services.facade.StudentStepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentStepServiceImpl implements StudentStepService {

    private static final Logger logger = LoggerFactory.getLogger ( StudentStepServiceImpl.class );

    private final StudentModuleRepository studentModuleRepository;
    private final ModuleRepository stepsRepository;
    private final StudentRepository studentRepository;
    private final ModuleWithStatusMapper moduleWithStatusMapper;

    private final StudentStepMapper studentStepMapper;


    public StudentStepServiceImpl(StudentModuleRepository studentModuleRepository , ModuleRepository stepsRepository ,
                                  StudentRepository studentRepository , ModuleWithStatusMapper moduleWithStatusMapper , StudentStepMapper studentStepMapper) {
        this.studentModuleRepository = studentModuleRepository;
        this.stepsRepository = stepsRepository;
        this.studentRepository = studentRepository;
        this.moduleWithStatusMapper = moduleWithStatusMapper;
        this.studentStepMapper = studentStepMapper;
    }

    @Override
    public List <StudentStep> getStudentSteps(Long studentId) {
        logger.info ( "Retrieving steps for student ID: {}" , studentId );
        return studentModuleRepository.findByStudentId ( studentId );
    }

    @Override
    public Optional <StudentStep> getStudentStep(Long studentId , Long stepId) {
        logger.info ( "Retrieving step {} for student ID: {}" , stepId , studentId );
        return studentModuleRepository.findByStudentIdAndStepId ( studentId , stepId )
                .stream ( )
                .sorted ( (a , b) -> b.getStartTime ( ).compareTo ( a.getStartTime ( ) ) )
                .findFirst ( );
    }

    @Override
    @Transactional
    public StudentStep startStep(Long studentId , Long stepId) {
        logger.info ( "Starting step {} for student ID: {}" , stepId , studentId );

        try {
            Optional <Student> studentOpt = studentRepository.findById ( studentId );
            if (studentOpt.isEmpty ( )) {
                logger.error ( "Student ID: {} not found" , studentId );
                return null;
            }

            Optional <Steps> stepOpt = stepsRepository.findById ( stepId );
            if (stepOpt.isEmpty ( )) {
                logger.error ( "Step ID: {} not found" , stepId );
                return null;
            }

            Optional <StudentStep> existingStudentStepOpt = getStudentStep ( studentId , stepId );

            StudentStep studentStep;
            if (existingStudentStepOpt.isPresent ( )) {
                studentStep = existingStudentStepOpt.get ( );
                if (studentStep.getStartTime ( ) == null) {
                    logger.info ( "Updating existing step for student ID: {}" , studentId );
                    studentStep.setStartTime ( LocalDateTime.now ( ) );
                    studentStep.setEndTime ( LocalDateTime.now ( ).plusMinutes ( studentStep.getStep ( ).getDurationInMinutes ( ) ) );
                }
            } else {
                logger.info ( "Creating new step for student ID: {}" , studentId );
                studentStep = new StudentStep ( );
                studentStep.setStudent ( studentOpt.get ( ) );
                studentStep.setStep ( stepOpt.get ( ) );
                studentStep.setStartTime ( LocalDateTime.now ( ) );
                studentStep.setEndTime ( LocalDateTime.now ( ).plusMinutes ( studentStep.getStep ( ).getDurationInMinutes ( ) ) );
                studentStep.setCompleted ( false );
                studentStep.setParcours ( stepOpt.get ( ).getParcours ( ) );
            }

            StudentStep savedStep = studentModuleRepository.save ( studentStep );
            logger.info ( "Successfully started step {} for student ID: {}" , stepId , studentId );
            return savedStep;

        } catch (Exception e) {
            logger.error ( "An error occurred while starting step {} for student ID: {}" , stepId , studentId , e );
            return null;
        }
    }

    @Override
    @Transactional
    public StudentStep completeStep(Long studentId , Long stepId , String taskUrl) {
        logger.info ( "Completing step {} for student ID: {}" , stepId , studentId );
        Optional <StudentStep> optionalStudentStep = getStudentStep ( studentId , stepId );
        if (optionalStudentStep.isPresent ( )) {
            StudentStep studentStep = optionalStudentStep.get ( );
            studentStep.setEndTime ( LocalDateTime.now ( ) );
            studentStep.setCompleted ( true );
            studentStep.setTaskUrl ( taskUrl );

            StudentStep savedStep = studentModuleRepository.save ( studentStep );
            logger.info ( "Completed step {} for student ID: {}" , stepId , studentId );
            return savedStep;
        }
        logger.error ( "Failed to complete step {} for student ID: {}" , stepId , studentId );
        return null;
    }

    @Override
    public List <StepWithStatusDTO> getStepsWithCompletionStatus(Long studentId , Long parcoursId) {
        logger.info ( "Retrieving steps with completion status for student ID: {} and parcours ID: {}" , studentId , parcoursId );
        List <Steps> steps = stepsRepository.findByParcoursIdOrderById ( parcoursId );
        return steps.stream ( ).map ( step -> {
            Optional <StudentStep> studentStepOpt = getStudentStep ( studentId , step.getId ( ) );
            return moduleWithStatusMapper.convertToDTO ( studentStepOpt.orElseGet ( () -> {
                StudentStep newStep = new StudentStep ( );
                newStep.setStep ( step );
                newStep.setStudent ( studentRepository.findById ( studentId ).orElse ( null ) );
                return newStep;
            } ) );
        } ).toList ( );
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void checkAndCompleteSteps() {
        LocalDateTime now = LocalDateTime.now ( );
        List <StudentStep> stepsToComplete = studentModuleRepository.findByCompletedFalseAndEndTimeBefore ( now );
        for (StudentStep step : stepsToComplete) {
            step.setCompleted ( true );
            studentModuleRepository.save ( step );
        }
        logger.info ( "Scheduled task completed: Steps marked as complete if their end time has passed." );
    }

    @Override
    public List <Object[]> findStepCounts() {
        return studentModuleRepository.findStepCounts ( );
    }

    @Override
    public List <StudentStepDTO> getTheSteps(Long stepId) {
        List <StudentStep> studentStep = studentModuleRepository.findByStepId ( stepId );

        return studentStep.stream ( ).map ( studentStepMapper :: convertToDTO ).toList ( );
    }

    @Transactional
    @Override
    public void deleteStudentStep(Long studentId , Long stepId , Long parcoursId) {
        studentModuleRepository.deleteByStepsIdAfterSorting ( parcoursId , studentId , stepId );
        studentModuleRepository.deleteByStepIdStudentId ( studentId , stepId );
    }

    @Override
    public List <Object[]> findCompletedStepsByParcoursId(Long parcoursId , Long studentId) {
        return studentModuleRepository.findCompletedStepsByParcoursId ( parcoursId , studentId );
    }

}