package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.ParcoursDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ParcoursService {

    ParcoursDTO save(ParcoursDTO parcoursDTO,MultipartFile file) throws IOException;

    ParcoursDTO findById(Long id);

    List<ParcoursDTO> findAll();

    ParcoursDTO update(ParcoursDTO parcoursDTO);

    void deleteById(Long id);

    List<ParcoursDTO> findAndCountByFormationsId (Long formationsId);

//    ParcoursDTO convertToDTO(Parcours parcours);
//    Parcours convertToEntity(ParcoursDTO parcoursDTO);
}
