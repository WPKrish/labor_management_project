package com.example.labor_management_project.service;

import com.example.labor_management_project.exception.UserNotFoundException;
import com.example.labor_management_project.model.SalaryRate;
import com.example.labor_management_project.repository.SalaryRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryRateService{

    @Autowired
    SalaryRateRepository salaryRateRepository;


    public String createSalaryRate(SalaryRate salaryRate) {
        salaryRateRepository.save(salaryRate);
        return "Success";
    }


    public String updateSalaryRate(SalaryRate salaryRate) {
        // More Business Logic
        salaryRateRepository.save(salaryRate);
        return "Success";
    }


    public String deleteSalaryRate(int rateID) {
        // More Business Logic
        salaryRateRepository.deleteById(rateID);
        return "Success";
    }


    public SalaryRate getSalaryRate(int rateID) {
        // More Business Logic
        if(salaryRateRepository.findById(rateID).isEmpty())
            throw new UserNotFoundException("Requested Salary Rate not exist");

        return salaryRateRepository.findById(rateID).get();
    }


    public List<SalaryRate> getAllSalaryRates() {
        // More Business Logic
        return salaryRateRepository.findAll();
    }
}
