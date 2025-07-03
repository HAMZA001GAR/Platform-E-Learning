package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.FormationsDTO;
import com.jobintechtracking.app.entities.Formations;
import com.jobintechtracking.app.mappers.FormationsMapper;
import com.jobintechtracking.app.repositories.FormationsRepository;
import com.jobintechtracking.app.services.facade.FormationsService;
import com.jobintechtracking.app.services.facade.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormationsServiceImpl implements FormationsService {

    private final FormationsRepository formationsRepository;
    private final FormationsMapper formationsMapper;

    public FormationsServiceImpl(FormationsRepository formationsRepository,FormationsMapper formationsMapper) {
        this.formationsRepository=formationsRepository;
        this.formationsMapper = formationsMapper;
    }
    @Autowired
    private ImageService imageService;

    @Override
    public FormationsDTO Save(FormationsDTO formationsDTO, MultipartFile file) throws IOException {
        Formations formations = formationsMapper.convertToEntity(formationsDTO);
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID ( ) + "_" + file.getOriginalFilename ();
            String uploadDir = "uploads/";
            Files.createDirectories ( Paths.get ( uploadDir ));
            Path path = Paths.get ( uploadDir , fileName );
            Files.write ( path, file.getBytes () );

            formations.setImageUrl("/uploads/" + fileName);
        }
        else {
            formations.setImageUrl(formationsDTO.getImageUrl()); // Keep old URL if no new image
        }
        Formations saveFormations = formationsRepository.save(formations);
        return formationsMapper.convertToDTO(saveFormations);
    }

    @Override
    public List<FormationsDTO> findAll() {
        return formationsRepository.findAll().stream().map(formationsMapper ::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Object updateFormation(FormationsDTO formationsDTO) {
        Formations existingFormation = formationsRepository.findById(formationsDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Formation not found with id: " + formationsDTO.getId()));
        existingFormation.setFormationName(formationsDTO.getFormationsName());
        existingFormation.setFormationDescription(formationsDTO.getFormationsDescription());
        Formations updatedFormation = formationsRepository.save(existingFormation);
        return formationsMapper.convertToDTO(updatedFormation);
    }
    @Override
    public void deleteFormation(Long id) {
        Formations existingFormation = formationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation not found with id: " + id));
        formationsRepository.delete(existingFormation);
    }
}
