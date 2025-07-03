package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.ParcoursDTO;
import com.jobintechtracking.app.DTO.ParcoursStepCount;
import com.jobintechtracking.app.entities.Parcours;
import com.jobintechtracking.app.mappers.ParcoursMapper;
import com.jobintechtracking.app.repositories.ParcoursRepository;
import com.jobintechtracking.app.services.facade.ImageService;
import com.jobintechtracking.app.services.facade.ParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParcoursServiceImpl implements ParcoursService {

    private final ParcoursRepository parcoursRepository;
    private final ParcoursMapper parcoursMapper;

    public ParcoursServiceImpl(ParcoursRepository parcoursRepository , ParcoursMapper parcoursMapper) {
        this.parcoursRepository = parcoursRepository;
        this.parcoursMapper = parcoursMapper;
    }

    @Autowired
    private ImageService imageService;

    @Override
    @Transactional
    public ParcoursDTO save(ParcoursDTO parcoursDTO , MultipartFile imageFile) throws IOException {
        Parcours parcours = parcoursMapper.convertToEntity ( parcoursDTO );

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "uploads/";
            Files.createDirectories ( Paths.get ( uploadDir ) );
            Path path = Paths.get(uploadDir, fileName);
            Files.write(path, imageFile.getBytes());

            parcours.setImageUrl("/uploads/" + fileName);
        } else {
            parcours.setImageUrl(parcoursDTO.getImageUrl()); // Keep old URL if no new image
        }
        Parcours savedParcours = parcoursRepository.save ( parcours );
        return parcoursMapper.convertToDTO ( savedParcours );
    }

    @Override
    public ParcoursDTO findById(Long id) {
        return parcoursRepository.findById ( id )
                .map ( parcoursMapper :: convertToDTO )
                .orElse ( null );
    }

    @Override
    public List <ParcoursDTO> findAll() {
        return parcoursRepository.findAll ( ).stream ( )
                .map ( parcoursMapper :: convertToDTO )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    @Transactional
    public ParcoursDTO update(ParcoursDTO parcoursDTO) {
        Parcours parcours = parcoursMapper.convertToEntity ( parcoursDTO );
        Parcours updatedParcours = parcoursRepository.save ( parcours );
        return parcoursMapper.convertToDTO ( updatedParcours );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        parcoursRepository.deleteById ( id );
    }

    @Override
    public List <ParcoursDTO> findAndCountByFormationsId(Long formationsId) {

        List <ParcoursStepCount> parcoursStepCount = parcoursRepository.findParcoursWithStepCountByFormationsId ( formationsId );
        return parcoursStepCount.stream ( ).map ( p -> {
            ParcoursDTO dto = new ParcoursDTO ( );
            dto.setId ( p.getId ( ) );
            dto.setParcoursName ( p.getName ( ) );
            dto.setParcoursDescription ( p.getDescription ( ) );
            dto.setStepsCount ( p.getStepCount ( ) );
            dto.setImageUrl ( p.getImageUrl () );

            return dto;
        } ).collect ( Collectors.toList ( ) );

    }
}
