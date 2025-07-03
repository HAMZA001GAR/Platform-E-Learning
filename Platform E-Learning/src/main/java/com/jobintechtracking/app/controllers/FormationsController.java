package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.DoingDTO;
import com.jobintechtracking.app.DTO.FormationsDTO;
import com.jobintechtracking.app.DTO.StepsDTO;
import com.jobintechtracking.app.DTO.StudentDTO;
import com.jobintechtracking.app.services.facade.FormationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationsController {
    private final FormationsService formationsService;

    public FormationsController (FormationsService formationsService) {
        this.formationsService = formationsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FormationsDTO>> getAllForamtions() {
        List<FormationsDTO> formationsDTOList = formationsService.findAll();
        return ResponseEntity.ok(formationsDTOList);
    }
    @PostMapping("/save")
    public ResponseEntity<FormationsDTO> saveFormations(
            @RequestPart("formationDTO") FormationsDTO formationsDTO,
            @RequestPart("file") MultipartFile file) {
        try {
            FormationsDTO savedFormation = formationsService.Save(formationsDTO,file);
            return ResponseEntity.ok(savedFormation);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public FormationsDTO updateFormation(@PathVariable Long id,@RequestBody FormationsDTO formationsDTO) {
        formationsDTO.setId(id);
        return (FormationsDTO) formationsService.updateFormation(formationsDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationsService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}
