package com.example.labor_management_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class SalaryDTO {
    private int salaryID;
    private int employeeID;
    private int month;
    private float salaryPerMonth;

}
