package com.example.labor_management_project.controller;


import com.example.labor_management_project.dto.SalaryDTO;
import com.example.labor_management_project.model.Salary;
import com.example.labor_management_project.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    SalaryService salaryService;


    // Read Specific Attendance Details from DB
    @GetMapping("{salaryID}")
    public Salary getDetails(@PathVariable("salaryID") int salaryID){
        return salaryService.getSalary(salaryID);
    }

    // Read Attendance Details from DB
    @GetMapping()
    public List<Salary> getAllSalaryDetails(){
        return salaryService.getAllSalary();
    }

    @PostMapping
    public String createSalaryDetails (@RequestBody SalaryDTO salaryDTO){
        salaryService.createSalary(salaryDTO);
        return "Salary created successfully";
    }

    @PutMapping
    public String updateSalaryDetails (@RequestBody SalaryDTO salaryDTO){
        salaryService.updateSalary(salaryDTO);
        return "Salary Details Updated successfully";
    }


    @PutMapping("/addMonth")
    public String updateOutTimeAttendanceDetails (@RequestBody SalaryDTO salaryDTO){
        salaryService.updateSalaryOnlyMonth(salaryDTO);
        return "Salary Details Updated successfully";
    }

    @DeleteMapping("{salaryID}")
    public String deleteAttendanceDetails(@PathVariable("salaryID") int salaryID){
        salaryService.deleteSalary(salaryID);
        return "Salary Deleted successfully according to the employee";
    }



}


