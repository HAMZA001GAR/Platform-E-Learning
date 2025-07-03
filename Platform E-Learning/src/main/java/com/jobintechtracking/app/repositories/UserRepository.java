package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <Users, Long> {
    Optional <Users> findByEmail(String email);

    @Query("SELECT ss.student.id, ss.student.firstName, ss.student.lastName, ss.parcours.id, " +
            "COUNT(ss.id) AS steps_taken, total_steps.totalSteps, " +
            "CONCAT(COUNT(ss.id), '/', total_steps.totalSteps) AS progress " +
            "FROM StudentStep ss " +
            "JOIN (SELECT st.parcours.id AS parcoursId, COUNT(st.id) AS totalSteps " +
            "      FROM Steps st " +
            "      GROUP BY st.parcours.id) AS total_steps " +
            "ON ss.parcours.id = total_steps.parcoursId " +
            "WHERE ss.parcours.id = :parcoursId " +
            "GROUP BY ss.student.id, ss.student.firstName, ss.student.lastName, ss.parcours.id, total_steps.totalSteps " +
            "ORDER BY ss.student.id")
    List <Object[]> getUsersWithStepsByParcoursId(@Param("parcoursId") Long parcoursId);


    @Query("SELECT u.id, u.firstName, u.lastName FROM Users u JOIN Student s ON u.id = s.id JOIN s.formations f JOIN Parcours p ON f.id = p.formations.id WHERE p.id = :parcoursId")
    List <Object[]> findUsersByParcoursId(@Param("parcoursId") Long parcoursId);
}




