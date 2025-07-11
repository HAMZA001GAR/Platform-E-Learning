package com.jobintechtracking.app.repositories;


import com.jobintechtracking.app.entities.Learning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningRepository extends JpaRepository<Learning, Long> {

    List<Learning> findBystepsId(long stepsId);
    void deleteByStepsId(Long stepsId);
    Learning findByStepsId(Long stepsId);
}
