package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.FormationsDTO;
import com.jobintechtracking.app.DTO.ParcoursDTO;
import com.jobintechtracking.app.services.facade.ParcoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/parcours")
public class ParcoursController {

    private final ParcoursService parcoursService;

    public ParcoursController(ParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    @GetMapping
    public ResponseEntity <List <ParcoursDTO>> getAllParcours() {
        List <ParcoursDTO> parcoursDTOList = parcoursService.findAll ( );
        if (parcoursDTOList != null) {
            return ResponseEntity.ok ( parcoursDTOList );
        } else {
            return ResponseEntity.notFound ( ).build ( );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity <ParcoursDTO> updateParcours(@PathVariable Long id , @RequestBody ParcoursDTO parcoursDTO) {
        ParcoursDTO updatedParcours = parcoursService.update ( parcoursDTO );
        return ResponseEntity.ok ( updatedParcours );
    }
    @PostMapping("/save")
    public ResponseEntity <ParcoursDTO> saveParcours(@RequestPart("parcoursDTO") ParcoursDTO parcoursDTO ,
                                                     @RequestPart("file") MultipartFile file) {
        try {
            ParcoursDTO savedParcours = parcoursService.save ( parcoursDTO , file );
            return ResponseEntity.ok ( savedParcours );
        } catch (IOException e) {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( null );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteParcours(@PathVariable Long id) {
        parcoursService.deleteById ( id );
        return ResponseEntity.noContent ().build ();
    }

    @GetMapping("/{id}")
    public ResponseEntity <ParcoursDTO> getParcoursById(@PathVariable Long id) {
        ParcoursDTO parcoursDTO = parcoursService.findById ( id );
        if (parcoursDTO != null) {
            return ResponseEntity.ok ( parcoursDTO );
        } else {
            return ResponseEntity.notFound ( ).build ( );
        }
    }

    @GetMapping("/formations/{formationsId}")
    public ResponseEntity <List <ParcoursDTO>> getParcoursByFormations(@PathVariable Long formationsId) {
        List <ParcoursDTO> parcoursDTO = parcoursService.findAndCountByFormationsId ( formationsId );
        if (parcoursDTO != null && !parcoursDTO.isEmpty ( )) {
            return ResponseEntity.ok ( parcoursDTO );
        } else {
            return ResponseEntity.notFound ( ).build ( );
        }
    }
}
