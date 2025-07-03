package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.entities.Formations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface FormationsRepository extends JpaRepository<Formations , Long> {
}
