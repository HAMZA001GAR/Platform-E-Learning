package com.jobintechtracking.app.services.facade;

import com.jobintechtracking.app.DTO.StudentDTO;
import com.jobintechtracking.app.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<StudentDTO> getAllStudents();
    Optional<StudentDTO> getStudentById(Long id);
    StudentDTO saveStudent(StudentDTO studentDTO);
    void deleteStudent(Long id);
    StudentDTO updateStudent(StudentDTO studentDTO);
    List<Student> getStudentsByFormationsId(Long formationsId);
}
