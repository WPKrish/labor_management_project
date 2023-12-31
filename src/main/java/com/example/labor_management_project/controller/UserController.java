package com.example.labor_management_project.controller;

import com.example.labor_management_project.dto.*;
import com.example.labor_management_project.model.User;
import com.example.labor_management_project.resopnse.LoginResponse;
import com.example.labor_management_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // Read Specific User Details from DB
    @GetMapping("{employeeID}")
    public User getUserDetails(@PathVariable("employeeID") int employeeID){
        return userService.getUser(employeeID);
    }

    // Read Specific User Details from DB
    @GetMapping()
    public List<User> getAllUserDetails(){
        return userService.getAllUers();
    }

    // Get Specific Username only
    @GetMapping("{employeeID}/name")
    public String getUserName(@PathVariable("employeeID") int employeeID){
        return userService.getUserName(employeeID);
    }


    @PostMapping
    public String createUserDetails (@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return "User created successfully";

    }

    @PutMapping
    public String updateUserDetails (@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return "User Updated successfully";
    }

    @PutMapping("/password")
    public String updateUserPassword (@RequestBody UserDTO userDTO){
        return userService.updateUserPassword(userDTO);
    }


    @DeleteMapping("{employeeID}")
    public ResponseEntity<String> deleteUserDetails(@PathVariable("employeeID") int employeeID) {
        return userService.inActivateUser(employeeID);
    }


    // Using Query
    @GetMapping("/details")
    public List<User> findAllActiveUser(){
        return userService.getAllActiveUsers();
    }

    @GetMapping("/name")
    public List<?> findAllActiveUserNames(){
        return userService.getAllUserNames();
    }

    @GetMapping("/name&password")
    public List<NamePasswordDTO> findAllNameWithPassword(){return userService.getAllNameWithPassword();}

    @GetMapping("/job")
    public List<DailyJobDTO> findAttendance(){return userService.getDailyJob();}

    @GetMapping("/supervisors")
    public List<User> findAllSupervisors(){
        return userService.getAllSupervisors();
    }

    @GetMapping("/labors")
    public List<User> findAllLabors(){
        return userService.getAllLabors();
    }


    //Create Login Controller
    @PostMapping("/login")
    public ResponseEntity<LoginResponse.LoginGenResponse> login (@RequestBody UserDTO loginDTO){
        return ResponseEntity.ok().body(userService.loginUserCondition(loginDTO));
    }





}
