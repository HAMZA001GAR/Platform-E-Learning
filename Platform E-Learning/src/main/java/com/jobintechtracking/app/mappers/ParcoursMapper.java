package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.ParcoursDTO;
import com.jobintechtracking.app.entities.Formations;
import com.jobintechtracking.app.entities.Parcours;
import org.springframework.stereotype.Component;

@Component
public class ParcoursMapper extends AbstractEntityDtoModelMapper <Parcours, ParcoursDTO> {

    @Override
    public ParcoursDTO convertToDTO(Parcours entity) {
        ParcoursDTO dto = new ParcoursDTO ( );
        dto.setId ( entity.getId ( ) );
        dto.setParcoursName ( entity.getParcoursName ( ) );
        dto.setParcoursDescription ( entity.getParcoursDescription ( ) );
        dto.setImageUrl ( entity.getImageUrl ( ) );
        if (entity.getFormations ( ) != null) {
            dto.setFormationId ( entity.getFormations ( ).getId ( ) );
        }
        return dto;
    }

    @Override
    public Parcours convertToEntity(ParcoursDTO dto) {
        Parcours entity = new Parcours ( );
        entity.setId ( dto.getId ( ) );
        entity.setParcoursName ( dto.getParcoursName ( ) );
        entity.setParcoursDescription ( dto.getParcoursDescription ( ) );
        entity.setImageUrl ( dto.getImageUrl ( ) );
        if (dto.getFormationId ( ) != null) {
            Formations formations = new Formations ( );
            formations.setId ( dto.getFormationId ( ) );
            entity.setFormations ( formations );
        }
        return entity;
    }
}
