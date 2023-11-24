package com.example.labor_management_project.controller;

import com.example.labor_management_project.model.SalaryRate;
import com.example.labor_management_project.service.SalaryRateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary_rate")
public class SalaryRateController {

    SalaryRateService salaryRateService;
    public SalaryRateController(SalaryRateService salaryRateService) {
        this.salaryRateService = salaryRateService;
    }

    // Read Specific Salary Rate Details from DB
    @GetMapping("{rateID}")
    public SalaryRate getSalaryRate(@PathVariable("rateID") int rateID){
        return salaryRateService.getSalaryRate(rateID);
    }

    // Read Specific Salary Rate Details from DB
    @GetMapping()
    public List<SalaryRate> getAllSalaryRates(){
        return salaryRateService.getAllSalaryRates();
    }

    @PostMapping
    public String createSalaryRateDetails (@RequestBody SalaryRate salaryRate){
        salaryRateService.createSalaryRate(salaryRate);
        return "Salary Rate created successfully";
    }

    @PutMapping
    public String updateSalaryRateDetails (@RequestBody SalaryRate salaryRate){
        salaryRateService.updateSalaryRate(salaryRate);
        return "Salary Rate Updated successfully";
    }

    @DeleteMapping("{rateID}")
    public String deleteSalaryRate(@PathVariable("rateID") int rateID){
        salaryRateService.deleteSalaryRate(rateID);
        return "Salary Rate Deleted successfully";
    }



}

