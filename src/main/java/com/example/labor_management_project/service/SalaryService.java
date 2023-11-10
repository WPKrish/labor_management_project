package com.example.labor_management_project.service;

import com.example.labor_management_project.dto.SalaryDTO;
import com.example.labor_management_project.exception.UserNotFoundException;
import com.example.labor_management_project.model.Salary;
import com.example.labor_management_project.repository.SalaryRepository;
import com.example.labor_management_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SalaryRepository salaryRepository;



    public String createSalary(SalaryDTO salaryDTO) {
        Salary tempSalary = new Salary();
        tempSalary.setMonth(salaryDTO.getMonth());
        tempSalary.setSalaryPerMonth(salaryDTO.getSalaryPerMonth());

        tempSalary.setEmployeeID(userRepository.getReferenceById(salaryDTO.getEmployeeID()));
        salaryRepository.save(tempSalary);
        return "Success";
    }



    public String updateSalary(SalaryDTO salaryDTO) {
        int updatableSalaryID = salaryDTO.getSalaryID();

        // Check if the attendance exists
        Optional<Salary> checkSalary = salaryRepository.findById(updatableSalaryID);
        if (checkSalary.isEmpty()) {
            throw new UserNotFoundException("Requested Attendance not exist");
        }

        // Now you can safely retrieve the attendance
        Salary availableSalary = checkSalary.get();
        int availableSalarySNo = availableSalary.getSalaryID();

        availableSalary.setSalaryID(availableSalarySNo);
        availableSalary.setMonth(salaryDTO.getMonth());
        availableSalary.setSalaryPerMonth(salaryDTO.getSalaryPerMonth()); // these values should be taken from database. Now do like this and this should be changed

        //availableAttendance.setEmployeeID(userRepository.getReferenceById(attendanceDTO.getEmployeeID()));
        salaryRepository.save(availableSalary);
        return "Success";
    }


    public String updateSalaryOnlyMonth(SalaryDTO salaryDTO) {
        int updatableSalaryID = salaryDTO.getSalaryID();

        // Check if the attendance exists
        Optional<Salary> checkSalary = salaryRepository.findById(updatableSalaryID);
        if (checkSalary.isEmpty()) {
            throw new UserNotFoundException("Requested Attendance not exist");
        }

        // Now you can safely retrieve the attendance
        Salary availableSalary = checkSalary.get();
        int availableSalarySNo = availableSalary.getSalaryID();

        availableSalary.setSalaryID(availableSalarySNo);
        availableSalary.setMonth(salaryDTO.getMonth());

        //availableAttendance.setEmployeeID(userRepository.getReferenceById(attendanceDTO.getEmployeeID()));
        salaryRepository.save(availableSalary);
        return "Success";
    }



    public String deleteSalary(int salaryID) {
        // More Business Logic
        salaryRepository.deleteById(salaryID);
        return "Success";
    }


    public Salary getSalary(int salaryID) {
        // More Business Logic
        if(salaryRepository.findById(salaryID).isEmpty())
            throw new UserNotFoundException("Requested User not exist");

        return salaryRepository.findById(salaryID).get();
    }


    public List<Salary> getAllSalary() {
        // More Business Logic
        List<Salary> salary = salaryRepository.findAll();

        return salary;
    }

}
