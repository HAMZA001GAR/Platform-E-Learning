package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.entities.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert, Long> {
}
