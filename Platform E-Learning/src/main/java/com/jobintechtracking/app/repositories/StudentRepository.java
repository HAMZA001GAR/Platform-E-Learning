package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student , Long> {

    List<Student> findByFormationsId(Long formationsId);
}
