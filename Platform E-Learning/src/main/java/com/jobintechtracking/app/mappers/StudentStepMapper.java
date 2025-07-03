package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.StudentStepDTO;
import com.jobintechtracking.app.entities.StudentStep;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StudentStepMapper extends AbstractEntityDtoModelMapper <StudentStep, StudentStepDTO> {

    @Override
    public StudentStepDTO convertToDTO(StudentStep entity) {
        StudentStepDTO dto = new StudentStepDTO ( );

        dto.setId ( entity.getId ( ) );
        dto.setFirstName ( entity.getStudent ( ).getFirstName ( ) );
        dto.setLastName ( entity.getStudent ( ).getLastName ( ) );
        dto.setTaskUrl ( entity.getTaskUrl ( ) );
        dto.setStartDate ( entity.getStartTime ( ) );
        dto.setEndDate ( entity.getEndTime ( ) );
        dto.setStepStatus ( entity.isCompleted ( ) );
        dto.setStudentId ( entity.getStudent ( ).getId ( ) );
        dto.setStepId ( entity.getStep ( ).getId ( ) );
        dto.setParcoursId ( entity.getParcours ( ).getId ( ) );

        return dto;
    }

    @Override
    public StudentStep convertToEntity(StudentStepDTO dto) {
        return null;
    }
}
