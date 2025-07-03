package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.LearningDTO;
import com.jobintechtracking.app.services.facade.LearningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learnings")
public class LearningController {

    private final LearningService learningService;

    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }

    @PostMapping("/save")
    public ResponseEntity<LearningDTO> saveLearning(@RequestBody LearningDTO learningDTO) {
        LearningDTO savedLearning = learningService.save(learningDTO);
        return ResponseEntity.ok(savedLearning);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningDTO> getLearningById(@PathVariable Long id) {
        LearningDTO learningDTO = learningService.findById(id);
        if (learningDTO != null) {
            return ResponseEntity.ok(learningDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = "stepsId")
    public ResponseEntity<List<LearningDTO>> getLearningsByStepsId(@RequestParam("stepsId") Long stepsId) {
        List<LearningDTO> learnings = learningService.getLearnByStepId(stepsId);
        if (!learnings.isEmpty()) {
            return ResponseEntity.ok(learnings);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<LearningDTO>> getAllLearnings() {
        List<LearningDTO> learnings = learningService.findAll();
        return ResponseEntity.ok(learnings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningDTO> updateLearning(@PathVariable Long id, @RequestBody LearningDTO learningDTO) {
        learningDTO.setId(id); // Ensure ID consistency
        LearningDTO updatedLearning = learningService.updateLearning(learningDTO);
        return ResponseEntity.ok(updatedLearning);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearning(@PathVariable Long id) {
        learningService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/steps/{stepsId}")
    public ResponseEntity<String> deleteLearningByStepId(@PathVariable Long stepsId) {
        learningService.deleteByStepsId(stepsId);
        return ResponseEntity.ok("Learning deleted");
    }
}