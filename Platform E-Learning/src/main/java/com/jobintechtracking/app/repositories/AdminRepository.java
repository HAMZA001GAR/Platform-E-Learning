package com.jobintechtracking.app.repositories;

import com.jobintechtracking.app.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
