package com.jobintechtracking.app.controllers;

import com.jobintechtracking.app.DTO.StudentDTO;
import com.jobintechtracking.app.entities.Student;
import com.jobintechtracking.app.services.facade.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Optional<StudentDTO> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.saveStudent(studentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        studentDTO.setId(id);
        return studentService.updateStudent(studentDTO);
    }

    @GetMapping("/by-formations/{formationsId}")
    public ResponseEntity<List<Student>> getStudentsByFormationsId(@PathVariable Long formationsId) {
        List<Student> students = studentService.getStudentsByFormationsId(formationsId);
        return ResponseEntity.ok(students);
    }
}
