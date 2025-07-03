package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.StepWithStatusDTO;
import com.jobintechtracking.app.DTO.StudentStepDTO;
import com.jobintechtracking.app.entities.StudentStep;
import com.jobintechtracking.app.mappers.ModuleWithStatusMapper;
import com.jobintechtracking.app.services.facade.StudentStepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student-steps")
public class StudentStepController {

    private static final Logger logger = LoggerFactory.getLogger ( StudentStepController.class );

    private final com.jobintechtracking.app.services.facade.StudentStepService studentStepService;

    private final ModuleWithStatusMapper moduleWithStatusMapper;

    public StudentStepController(StudentStepService studentStepService , ModuleWithStatusMapper moduleWithStatusMapper) {
        this.studentStepService = studentStepService;
        this.moduleWithStatusMapper = moduleWithStatusMapper;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity <List <StudentStep>> getStudentSteps(@PathVariable Long studentId) {
        logger.info ( "Fetching steps for student ID: {}" , studentId );
        List <StudentStep> studentSteps = studentStepService.getStudentSteps ( studentId );
        return ResponseEntity.ok ( studentSteps );
    }

    @PostMapping("/start")
    public ResponseEntity <StudentStep> startStep(@RequestParam Long studentId , @RequestParam Long stepId) {
        logger.info ( "Starting step {} for student ID: {}" , stepId , studentId );
        StudentStep studentStep = studentStepService.startStep ( studentId , stepId );
        if (studentStep != null) {
            return ResponseEntity.ok ( studentStep );
        } else {
            logger.error ( "Failed to start step {} for student ID: {}" , stepId , studentId );
            return ResponseEntity.badRequest ( ).body ( null );
        }
    }

    @PostMapping("/complete")
    public ResponseEntity <StudentStep> completeStep(@RequestParam Long studentId , @RequestParam Long stepId , @RequestParam String taskUrl) {
        logger.info ( "Completing step {} for student ID: {}" , stepId , studentId );
        StudentStep studentStep = studentStepService.completeStep ( studentId , stepId , taskUrl );
        if (studentStep != null) {
            return ResponseEntity.ok ( studentStep );
        } else {
            logger.error ( "Failed to complete step {} for student ID: {}" , stepId , studentId );
            return ResponseEntity.badRequest ( ).body ( null );
        }
    }

    @GetMapping("/steps-status/{studentId}/{parcoursId}")
    public ResponseEntity <List <StepWithStatusDTO>> getStepsWithCompletionStatus(@PathVariable Long studentId , @PathVariable Long parcoursId) {
        logger.info ( "Fetching steps with completion status for student ID: {} and parcours ID: {}" , studentId , parcoursId );
        List <StepWithStatusDTO> stepsWithStatus = studentStepService.getStepsWithCompletionStatus ( studentId , parcoursId );
        return ResponseEntity.ok ( stepsWithStatus );
    }

    @GetMapping("/stepInfo/{studentId}/{stepId}")
    public ResponseEntity <StepWithStatusDTO> getStepInfo(@PathVariable Long studentId , @PathVariable Long stepId) {
        Optional <StudentStep> studentStep = studentStepService.getStudentStep ( studentId , stepId );
        if (studentStep.isPresent ( )) {
            StepWithStatusDTO dto = moduleWithStatusMapper.convertToDTO ( studentStep.get ( ) );
            return ResponseEntity.ok ( dto );
        }
        return ResponseEntity.notFound ( ).build ( );
    }

    @GetMapping("/step-counts")
    public List <Object[]> getStepCounts() {
        return studentStepService.findStepCounts ( );
    }

    @GetMapping("/stepsStudents/{stepId}")
    public List <StudentStepDTO> getthestudentsinastep(@PathVariable Long stepId) {
        return studentStepService.getTheSteps ( stepId );
    }


    @DeleteMapping("/delete-student-step/{studentId}/{stepId}/{parcoursId}")
    public ResponseEntity <Void> deleteStudentStep(@PathVariable Long studentId , @PathVariable Long stepId , @PathVariable Long parcoursId) {
        studentStepService.deleteStudentStep ( studentId , stepId , parcoursId );
        return ResponseEntity.noContent ( ).build ( );
    }

    @GetMapping("progress/{parcoursId}")
    public List <Object[]> findCompletedStepsByParcoursId(@PathVariable Long parcoursId , @RequestParam Long studentId) {
        return studentStepService.findCompletedStepsByParcoursId ( parcoursId , studentId );
    }


}
