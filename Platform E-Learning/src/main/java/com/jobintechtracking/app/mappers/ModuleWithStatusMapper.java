package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.StepWithStatusDTO;
import com.jobintechtracking.app.entities.StudentStep;
import org.springframework.stereotype.Component;

@Component
public class ModuleWithStatusMapper extends AbstractEntityDtoModelMapper <StudentStep, StepWithStatusDTO> {

    @Override
    public StepWithStatusDTO convertToDTO(StudentStep entity) {
        StepWithStatusDTO dto = new StepWithStatusDTO ( );
        dto.setId ( entity.getStep ( ).getId ( ) );
        dto.setTitle ( entity.getStep ( ).getTitle ( ) );
        dto.setDurationInMinutes ( entity.getStep ( ).getDurationInMinutes ( ) );
        dto.setCompleted ( entity.isCompleted ( ) );
        dto.setStartTime ( entity.getStartTime ( ) );
        dto.setEndTime ( entity.getEndTime ( ) );
        dto.setStatus ( entity.isCompleted ( ) );
        dto.setImageUrl ( (entity.getStep ( ).getImageUrl ( )) );
        return dto;
    }

    @Override
    public StudentStep convertToEntity(StepWithStatusDTO dto) {
        StudentStep entity = new StudentStep ( );
        return entity;
    }


}
