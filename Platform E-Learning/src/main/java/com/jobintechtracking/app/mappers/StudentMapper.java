package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.StudentDTO;
import com.jobintechtracking.app.entities.Student;
import com.jobintechtracking.app.entities.StudentStep;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StudentMapper extends AbstractEntityDtoModelMapper <Student, StudentDTO> {

    @Override
    public StudentDTO convertToDTO(Student entity) {
        StudentDTO dto = new StudentDTO ( );
        dto.setId ( entity.getId ( ) );

        dto.setParcoursId ( entity.getParcours ( ) != null ? entity.getParcours ( ).getId ( ) : null );
        dto.setStudentStepIds ( entity.getStudentSteps ( ) != null ? entity.getStudentSteps ( ).stream ( ).map ( StudentStep :: getId ).collect ( Collectors.toList ( ) ) : null );
        dto.setFirstName ( entity.getFirstName ( ) );
        dto.setLastName ( entity.getLastName ( ) );
        dto.setEmail ( entity.getEmail ( ) );
        dto.setRole ( entity.getRoles ( ).name ( ) );
        return dto;
    }

    @Override
    public Student convertToEntity(StudentDTO dto) {
        Student entity = new Student ( );
        entity.setId ( dto.getId ( ) );
        return entity;
    }
}
