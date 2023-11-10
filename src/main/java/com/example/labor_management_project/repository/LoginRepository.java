package com.example.labor_management_project.repository;

import com.example.labor_management_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Integer> {
    Optional<User> findOneByEmployeeIDAndPassword(int employeeID, String password);
    User findByEmployeeID(int employeeID);
}
