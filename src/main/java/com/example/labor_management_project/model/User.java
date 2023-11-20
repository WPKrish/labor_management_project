package com.example.labor_management_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id") // this is used to mapping to Attendance entity, for mapping we should know column name.
    private int employeeID;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private JobRole jobRole;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_id", referencedColumnName = "rate_id")
    private SalaryRate salaryRate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "site_id", referencedColumnName = "site_id")
    private Site site;

    private String name;
    private String password;

    private boolean activeUser;



    @JsonFormat(pattern="MM/dd/yy")
    private Date birthDay;
    private String phoneNo;
    private String address;
//    @Column(name="site_name")
//    private String siteName;
    private String bloodGroup;
    private String emergencyPhoneNo;
    private String emergencyName;

    private String username;


}
