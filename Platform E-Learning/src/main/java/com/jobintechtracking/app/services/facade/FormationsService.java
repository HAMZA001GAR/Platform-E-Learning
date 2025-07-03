package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.FormationsDTO;
import com.jobintechtracking.app.DTO.ParcoursDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FormationsService {

    void deleteFormation(Long id);

    FormationsDTO Save(FormationsDTO formationsDTO, MultipartFile file) throws IOException;
    List<FormationsDTO> findAll();

    Object updateFormation(FormationsDTO formationsDTO);
}
