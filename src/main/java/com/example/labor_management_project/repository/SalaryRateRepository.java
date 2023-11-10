package com.example.labor_management_project.repository;

import com.example.labor_management_project.model.SalaryRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRateRepository extends JpaRepository<SalaryRate, Integer> {
}
