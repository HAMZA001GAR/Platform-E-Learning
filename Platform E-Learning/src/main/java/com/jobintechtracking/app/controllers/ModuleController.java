package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.StepsDTO;
import com.jobintechtracking.app.DTO.StepsDoingLeranings;
import com.jobintechtracking.app.services.facade.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/steps")
public class ModuleController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<StepsDTO> getAllSteps() {
        List<StepsDTO> steps = moduleService.findAll();
        steps.sort(Comparator.comparing(StepsDTO::getId));
        return steps;
    }

    @GetMapping("/parcours/{parcoursId}")
    public Page <StepsDTO> getStepsByParcoursId(
            @PathVariable Long parcoursId ,
            @RequestParam(value = "page", defaultValue = "0") int page ,
            @RequestParam(value = "size", defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of ( page , size );
        return (Page <StepsDTO>) moduleService.getStepsByParcoursId ( parcoursId , pageable );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepsDTO> getStepById(@PathVariable Long id) {
        StepsDTO step = moduleService.findById(id);
        return ResponseEntity.ok(step);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StepsDTO> createStep (@RequestPart("stepDTO") StepsDTO stepDTO,
                                                @RequestPart("image") MultipartFile imageFile
    ) {
        try {
            StepsDTO savedStep = moduleService.saveStep(stepDTO,imageFile);
            return ResponseEntity.ok(savedStep);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long id) {
        moduleService.deleteStep(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StepsDTO> updateSteps(
            @PathVariable Long id,
            @RequestBody StepsDTO stepDTO,
            MultipartFile file) {
        StepsDTO updatedStep = moduleService.updateStep(stepDTO, file);
        return ResponseEntity.ok(updatedStep);
    }

    @PutMapping(value = "/stepDoingLearning/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity <StepsDTO> updateStepsWithDoingAndLearning(
            @PathVariable Long id ,
            @RequestPart("stepsDoingLeranings") StepsDoingLeranings stepsDoingLeranings ,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            StepsDTO updatedStep = moduleService.updateStepsWithDoingAndLearning ( stepsDoingLeranings , imageFile );
            return ResponseEntity.ok ( updatedStep );
        } catch (IOException e) {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( null );
        }
    }

    @GetMapping("/count")
    public Map<Long, Long> countStepsByFormationId(@RequestParam Long formationId) {
        return moduleService.countStepsByFormationId(formationId);
    }
}