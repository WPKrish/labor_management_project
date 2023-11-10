package com.example.labor_management_project.dto;

import com.example.labor_management_project.model.SalaryRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    int attID;
    int employeeID;
    //float inTime;
    LocalDateTime inTime;
    String dailyJob;
    //float outTime;
    LocalDateTime outTime;

    String name; // used to get employee name with employeeID

    // Used to get Salary
    Float workingHours;
    Double monthlyWorkingHours;
    Long monthlyWorkedDays;
    Double noOfSalaryAllocatedDays;
    int salaryPerDay;
    Double monthlySalary;

    String monthName;
    int month;
    int year;


    // (Used for Attendance update)
    public AttendanceDTO(int employeeID, int attID,  LocalDateTime inTime) {
        this.attID = attID;
        this.employeeID = employeeID;
        this.inTime = inTime;
    }

    // (Used for Attendance update)
    public AttendanceDTO(int employeeID, int attID, Float workingHours) {
        this.attID = attID;
        this.employeeID = employeeID;
        this.workingHours = workingHours;
    }

    // (Used for current date attendance)
    public AttendanceDTO(int employeeID, String name, int attID,  LocalDateTime inTime, String dailyJob) {
        this.employeeID = employeeID;
        this.name = name;
        this.attID = attID;
        this.inTime = inTime;
        this.dailyJob = dailyJob;
    }

    // (Using for daily attendance)
    public AttendanceDTO(int employeeID, String name, int attID,  LocalDateTime inTime, LocalDateTime outTime, String dailyJob) {
        this.employeeID = employeeID;
        this.name = name;
        this.attID = attID;
        this.inTime = inTime;
        this.outTime = outTime;
        this.dailyJob = dailyJob;
    }

    // (Used for current monthly Attendance)
    public AttendanceDTO(int employeeID, String name, int attID,  LocalDateTime inTime, String dailyJob, Float workingHours, LocalDateTime outTime) {
        this.name = name;
        this.attID = attID;
        this.employeeID = employeeID;
        this.inTime = inTime;
        this.dailyJob = dailyJob;
        this.workingHours = workingHours;
        this.outTime = outTime;
    }
    // (Using for monthly salary) (doing 1)
    public AttendanceDTO(int employeeID, String name, String monthName, int year, int attID,  LocalDateTime inTime, String dailyJob, Float workingHours, LocalDateTime outTime) {
        this.employeeID = employeeID;
        this.name = name;
        this.monthName = monthName;
        this.year = year;
        this.attID = attID;
        this.inTime = inTime;
        this.dailyJob = dailyJob;
        this.workingHours = workingHours;
        this.outTime = outTime;
    }

    // (Used for current month Salary and monthly salary)
    public AttendanceDTO(int employeeID, String name, String monthName, int year, Double monthlyWorkingHours, Long monthlyWorkedDays, Double noOfSalaryAllocatedDays, int salaryPerDay, Double monthlySalary) {
        this.employeeID = employeeID;
        this.name = name;
        this.monthName = monthName;
        this.year = year;
        this.monthlyWorkingHours = monthlyWorkingHours;
        this.monthlyWorkedDays = monthlyWorkedDays;
        this.noOfSalaryAllocatedDays = noOfSalaryAllocatedDays;
        this.salaryPerDay = salaryPerDay;
        this.monthlySalary = monthlySalary;
    }


}
