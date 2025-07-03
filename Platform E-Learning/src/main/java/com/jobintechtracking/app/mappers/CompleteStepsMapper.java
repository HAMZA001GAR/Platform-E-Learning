package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.CompleteStepsDto;

import com.jobintechtracking.app.entities.StudentStep;

public class CompleteStepsMapper extends AbstractEntityDtoModelMapper <StudentStep, CompleteStepsDto> {
    @Override
    public CompleteStepsDto convertToDTO(StudentStep entity) {
        CompleteStepsDto dto = new CompleteStepsDto ( );
        dto.setId ( entity.getId ( ) );
        dto.setStartTime ( entity.getStartTime ( ) );
        dto.setEndTime ( entity.getEndTime ( ) );
        dto.setTaskUrl ( entity.getTaskUrl ( ) );
        dto.setStepId ( entity.getStep ( ).getId ( ) );
        dto.setStudentId ( entity.getStudent ( ).getId ( ) );
        return dto;
    }

    @Override
    public StudentStep convertToEntity(CompleteStepsDto dto) {
        StudentStep entity = new StudentStep ( );
        return entity;
    }
}
