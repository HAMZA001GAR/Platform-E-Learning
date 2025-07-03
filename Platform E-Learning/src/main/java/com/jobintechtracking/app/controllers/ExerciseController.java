package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.DoingDTO;
import com.jobintechtracking.app.services.facade.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doings")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/save")
    public ResponseEntity<DoingDTO> saveDoing(@RequestBody DoingDTO doingDTO) {
        DoingDTO savedDoing = exerciseService.save(doingDTO);
        return ResponseEntity.ok(savedDoing);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoingDTO> getDoingById(@PathVariable Long id) {
        DoingDTO doingDTO = exerciseService.findById(id);
        if (doingDTO != null) {
            return ResponseEntity.ok(doingDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = "stepsId")
    public ResponseEntity<List<DoingDTO>> getDoingsByStepsId(@RequestParam("stepsId") Long stepsId) {
        List<DoingDTO> doings = exerciseService.getDoingByStepId(stepsId);
        if (!doings.isEmpty()) {
            return ResponseEntity.ok(doings);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoingDTO>> getAllDoings() {
        List<DoingDTO> doings = exerciseService.findAll();
        return ResponseEntity.ok(doings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoingDTO> updateDoing(@PathVariable Long id, @RequestBody DoingDTO doingDTO) {
        doingDTO.setId(id); // Ensure ID consistency
        DoingDTO updatedDoing = exerciseService.updateDoing(doingDTO);
        return ResponseEntity.ok(updatedDoing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoing(@PathVariable Long id) {
        exerciseService.deleteDoing(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/steps/{stepsId}")
    public ResponseEntity<String> deleteDoingByStepId(@PathVariable Long stepsId) {
        exerciseService.deleteByStepsId(stepsId);
        return ResponseEntity.ok("Doing deleted");
    }
}