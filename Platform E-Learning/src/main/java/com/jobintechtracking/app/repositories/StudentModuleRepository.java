package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.entities.StudentStep;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudentModuleRepository extends JpaRepository <StudentStep, Long> {

    List <StudentStep> findByStudentId(Long studentId);

    List <StudentStep> findByStudentIdAndStepId(Long studentId , Long stepId);

    List <StudentStep> findByCompletedFalseAndEndTimeBefore(LocalDateTime now);

    void deleteByStepId(Long stepId);

    @Query("SELECT s.step.id, COUNT(s) FROM StudentStep s WHERE s.step.id IN (SELECT st.id FROM Steps st) GROUP BY s.step.id")
    List <Object[]> findStepCounts();

    List <StudentStep> findByStepId(Long stepId);

    @Modifying
    @Transactional
    @Query("DELETE FROM StudentStep ss WHERE ss.student.id = :studentId AND ss.step.id = :stepId")
    void deleteByStepIdStudentId(Long studentId , Long stepId);


    @Modifying
    @Transactional
    @Query("DELETE FROM StudentStep ss WHERE ss.parcours.id = :parcoursId AND ss.student.id = :studentId AND ss.step.id > :stepId")
    void deleteByStepsIdAfterSorting(Long parcoursId , Long studentId , Long stepId);

    @Query("SELECT s.title AS title, ss.startTime AS startTime, ss.endTime AS endTime, ss.taskUrl AS taskUrl, ss.step.id AS stepId, ss.student.id AS studentId " +
            "FROM StudentStep ss " +
            "JOIN ss.step s ON ss.step.id = s.id " +
            "WHERE ss.parcours.id = :parcoursId AND ss.student.id = :studentId AND ss.completed = true")
    List <Object[]> findCompletedStepsByParcoursId(@Param("parcoursId") Long parcoursId , @Param("studentId") Long studentId);


}
