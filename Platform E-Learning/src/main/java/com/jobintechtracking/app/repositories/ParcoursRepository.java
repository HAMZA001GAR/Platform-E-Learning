package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.DTO.ParcoursStepCount;
import com.jobintechtracking.app.entities.Parcours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcoursRepository extends JpaRepository<Parcours, Long> {

    List<Parcours> findByFormationsId(Long formationId);

    @Query("SELECT p.id FROM Parcours p WHERE p.formations.id = :formationId")
    List<Long> findParcoursIdsByFormationId(@Param("formationId") Long formationId);

    @Query("SELECT p.id AS id, p.parcoursName AS name, p.parcoursDescription AS description, p.imageUrl as imageUrl , COUNT(s.id) AS stepCount " +
            "FROM Parcours p LEFT JOIN Steps s ON p.id = s.parcours.id " +
            "WHERE p.formations.id = :formationId " +
            "GROUP BY p.id, p.parcoursName ,p.parcoursDescription, p.imageUrl")
    List<ParcoursStepCount> findParcoursWithStepCountByFormationsId(@Param("formationId") Long formationId);
}
