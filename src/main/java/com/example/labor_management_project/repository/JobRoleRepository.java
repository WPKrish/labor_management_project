package com.example.labor_management_project.repository;

import com.example.labor_management_project.model.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRoleRepository extends JpaRepository<JobRole, Integer> {
}


