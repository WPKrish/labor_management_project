package com.example.labor_management_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    int employeeID;
    int rateID;
    int roleID;
    int siteID;
    private String name;
    private String password;
    private String oldPassword;
    @JsonFormat(pattern="MM/dd/yyyy")
    private Date birthDay;
    private String phoneNo;
    private String address;
    private String bloodGroup;
    private String emergencyPhoneNo;
    private String emergencyName;
    private String username;

}

