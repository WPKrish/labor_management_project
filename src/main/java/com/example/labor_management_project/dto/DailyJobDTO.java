package com.example.labor_management_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyJobDTO {
    private int employeeID;
    private String name;
    private int attID;
    private String dailyJob;


}
