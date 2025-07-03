package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.LearningDTO;
import com.jobintechtracking.app.entities.Learning;
import com.jobintechtracking.app.entities.Steps;
import org.springframework.stereotype.Component;

@Component
public class LearningMapper extends AbstractEntityDtoModelMapper <Learning, LearningDTO> {

    @Override
    public LearningDTO convertToDTO(Learning entity) {
        LearningDTO dto = new LearningDTO ( );
        dto.setId ( entity.getId ( ) );
        dto.setTitle ( entity.getTitle ( ) );
        dto.setDescription ( entity.getDescription ( ) );
        dto.setUrl ( entity.getUrl ( ) );
        if (entity.getSteps ( ) != null) {
            dto.setStepsId ( entity.getSteps ( ).getId ( ) );
        }
        return dto;
    }

    @Override
    public Learning convertToEntity(LearningDTO dto) {
        Learning entity = new Learning ( );
        if (dto.getId ( ) != null) {
            entity.setId ( dto.getId ( ) );
        }
        entity.setTitle ( dto.getTitle ( ) );
        entity.setDescription ( dto.getDescription ( ) );
        entity.setUrl ( dto.getUrl ( ) );
        if (dto.getStepsId ( ) != null) {
            Steps steps = new Steps ( );
            steps.setId ( dto.getStepsId ( ) );
            entity.setSteps ( steps );
        }
        return entity;
    }
}