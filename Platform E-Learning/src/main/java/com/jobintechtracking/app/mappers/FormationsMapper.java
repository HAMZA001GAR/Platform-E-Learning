package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.FormationsDTO;
import com.jobintechtracking.app.entities.Formations;
import org.springframework.stereotype.Component;

@Component
public class FormationsMapper extends AbstractEntityDtoModelMapper <Formations, FormationsDTO> {
    @Override
    public FormationsDTO convertToDTO(Formations entity) {
        FormationsDTO dto = new FormationsDTO ( );
        dto.setId ( entity.getId ( ) );
        dto.setFormationsName ( entity.getFormationName ( ) );
        dto.setFormationsDescription ( entity.getFormationDescription ( ) );
        dto.setImageUrl ( entity.getImageUrl ( ) );
        return dto;
    }

    @Override
    public Formations convertToEntity(FormationsDTO dto) {
        Formations entity = new Formations ( );
        entity.setId ( dto.getId ( ) );
        entity.setFormationName ( dto.getFormationsName ( ) );
        entity.setFormationDescription ( dto.getFormationsDescription ( ) );
        entity.setImageUrl ( dto.getImageUrl ( ) );
        return entity;
    }
}
