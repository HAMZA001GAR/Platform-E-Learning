package com.jobintechtracking.app.services.impl;

import com.jobintechtracking.app.DTO.StudentDTO;
import com.jobintechtracking.app.entities.Student;
import com.jobintechtracking.app.mappers.StudentMapper;
import com.jobintechtracking.app.repositories.ParcoursRepository;
import com.jobintechtracking.app.repositories.StudentRepository;
import com.jobintechtracking.app.services.facade.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ParcoursRepository parcoursRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository , ParcoursRepository parcoursRepository , StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.parcoursRepository = parcoursRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List <StudentDTO> getAllStudents() {
        return studentRepository.findAll ( ).stream ( )
                .map ( studentMapper :: convertToDTO )
                .collect ( Collectors.toList ( ) );
    }

    @Override
    public Optional <StudentDTO> getStudentById(Long id) {
        return studentRepository.findById ( id )
                .map ( studentMapper :: convertToDTO );
    }

    @Override
    @Transactional
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = studentMapper.convertToEntity ( studentDTO );
        student.setParcours ( parcoursRepository.findById ( studentDTO.getParcoursId ( ) ).orElse ( null ) );
        Student savedStudent = studentRepository.save ( student );
        return studentMapper.convertToDTO ( savedStudent );
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student student = studentMapper.convertToEntity ( studentDTO );
        student.setParcours ( parcoursRepository.findById ( studentDTO.getParcoursId ( ) ).orElse ( null ) );
        Student updatedStudent = studentRepository.save ( student );
        return studentMapper.convertToDTO ( updatedStudent );
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById ( id );
    }


    @Override
    @Transactional
    public List <Student> getStudentsByFormationsId(Long formationsId) {
        return studentRepository.findByFormationsId ( formationsId );
    }

}

