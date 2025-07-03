package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.StepsDTO;
import com.jobintechtracking.app.entities.Steps;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper extends AbstractEntityDtoModelMapper <Steps, StepsDTO> {

    @Override
    public StepsDTO convertToDTO(Steps entity) {
        StepsDTO dto = new StepsDTO ( );
        dto.setId ( entity.getId ( ) );
        dto.setTitle ( entity.getTitle ( ) );
        dto.setDescription ( entity.getDescription ( ) );
        dto.setDurationInMinutes ( entity.getDurationInMinutes ( ) );
        dto.setParcoursId ( entity.getParcours ( ) != null ? entity.getParcours ( ).getId ( ) : null );
        dto.setStepProcess ( entity.getStepProcess ( ) );
        dto.setImageUrl ( entity.getImageUrl ( ) );
        return dto;
    }

    @Override
    public Steps convertToEntity(StepsDTO dto) {
        Steps entity = new Steps ( );
        entity.setId ( dto.getId ( ) );
        entity.setTitle ( dto.getTitle ( ) );
        entity.setDescription ( dto.getDescription ( ) );
        entity.setDurationInMinutes ( dto.getDurationInMinutes ( ) );
        entity.setStepProcess ( dto.getStepProcess ( ) );
        entity.setImageUrl ( dto.getImageUrl ( ) );
        return entity;
    }
}
