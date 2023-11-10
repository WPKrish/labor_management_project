package com.example.labor_management_project.service;

import com.example.labor_management_project.exception.UserNotFoundException;
import com.example.labor_management_project.model.JobRole;
import com.example.labor_management_project.repository.JobRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobRoleService {

    @Autowired
    JobRoleRepository jobRoleRepository;


    public String createUserRole(JobRole jobRole) {
        jobRoleRepository.save(jobRole);
        return "Success";
    }


    public String updateUserRole(JobRole jobRole) {
        // More Business Logic
        jobRoleRepository.save(jobRole);
        return "Success";
    }


    public String deleteUserRole(int roleID) {
        // More Business Logic
        jobRoleRepository.deleteById(roleID);
        return "Success";
    }


    public JobRole getUserRole(int roleID) {
        // More Business Logic
        if(jobRoleRepository.findById(roleID).isEmpty())
            throw new UserNotFoundException("Requested Job Role not exist");

        return jobRoleRepository.findById(roleID).get();
    }


    public List<JobRole> getAllUerRoles() {
        // More Business Logic
        return jobRoleRepository.findAll();
    }
}

