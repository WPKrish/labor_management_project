package com.example.labor_management_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {
    int employeeID;
    String name;
    int year;
    String month;
    int points;
    Long pointSum;

    Long pointIDCount;


    public PointDTO(int employeeID, String name, String month, int year, Long pointSum, Long pointIDCount) {
        this.employeeID = employeeID;
        this.name = name;
        this.month = month;
        this.year = year;
        this.pointSum = pointSum;
        this.pointIDCount = pointIDCount;
    }




}
