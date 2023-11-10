package com.example.labor_management_project.controller;

import com.example.labor_management_project.model.JobRole;
import com.example.labor_management_project.service.JobRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job_role")
public class JobRoleController {

    JobRoleService jobRoleService;
    public JobRoleController(JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    // Read Specific User Role Details from DB
    @GetMapping("{roleID}")
    public JobRole getUserDetails(@PathVariable("roleID") int roleID){
        return jobRoleService.getUserRole(roleID);
    }

    // Read All User Role Details from DB
    @GetMapping()
    public List<JobRole> getAllUserRoleDetails(){
        return jobRoleService.getAllUerRoles();
    }

    @PostMapping
    public String createUserDetails (@RequestBody JobRole jobRole){
        jobRoleService.createUserRole(jobRole);
        return "Job Role created successfully";
    }

    @PutMapping
    public String updateUserDetails (@RequestBody JobRole jobRole){
        jobRoleService.updateUserRole(jobRole);
        return "Job Role Updated successfully";
    }

    @DeleteMapping("{roleID}")
    public String deleteUserDetails(@PathVariable("roleID") int roleID){
        jobRoleService.deleteUserRole(roleID);
        return "JOb Role Deleted successfully";
    }



}

