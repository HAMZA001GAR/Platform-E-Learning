package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.entities.Steps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Steps, Long> {

    Page<Steps> findByParcoursId(Long formationId, Pageable pageable);
    List<Steps> findByParcoursIdOrderById(Long parcoursId);
    @Query("SELECT COUNT(s) FROM Steps s WHERE s.parcours.id = :parcoursId")
    Long countByParcoursId(@Param("parcoursId") Long parcoursId);
}
