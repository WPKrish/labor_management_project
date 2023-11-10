package com.example.labor_management_project.service;

import com.example.labor_management_project.dto.DailyJobDTO;
import com.example.labor_management_project.dto.NamePasswordDTO;
import com.example.labor_management_project.dto.UserDTO;
import com.example.labor_management_project.exception.UserNotFoundException;
import com.example.labor_management_project.model.Attendance;
import com.example.labor_management_project.model.User;
import com.example.labor_management_project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    JobRoleRepository jobRoleRepository;

    @Autowired
    SalaryRateRepository salaryRateRepository;

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // register user (used)
    public String createUser(UserDTO userDTO) {

            User tempUser = new User();
            tempUser.setName(userDTO.getName());
            //tempUser.setPassword(userDTO.getPassword()); //this is used to set password without security
            tempUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword())); //this is used to set password with security
//            tempUser.setPassword(this.passwordEncoder.encode("123")); //this is used to set password with security and without user working
            tempUser.setPhoneNo(userDTO.getPhoneNo());
            tempUser.setEmergencyName(userDTO.getEmergencyName());
            tempUser.setEmergencyPhoneNo(userDTO.getEmergencyPhoneNo());
            tempUser.setBirthDay(userDTO.getBirthDay());
            tempUser.setAddress(userDTO.getAddress());
//            tempUser.setSiteName(userDTO.getSiteName());
        tempUser.setSite(siteRepository.getReferenceById(userDTO.getSiteID()));
            tempUser.setBloodGroup(userDTO.getBloodGroup());
            tempUser.setJobRole(jobRoleRepository.getReferenceById(userDTO.getRoleID()));
            tempUser.setSalaryRate(salaryRateRepository.getReferenceById(userDTO.getRateID()));
            userRepository.save(tempUser);
            return "Success";


    }

    // update user
    public String updateUser(UserDTO userDTO) {

        int updatableEmpID = userDTO.getEmployeeID();

        // Check if the User exists
        Optional<User> checkEmployee = userRepository.findById(updatableEmpID);
        if (checkEmployee.isEmpty()) {
            throw new UserNotFoundException("Requested User not exist");
        }

        // Now you can safely retrieve the attendance
        User availableUser = checkEmployee.get();
        int availableUserEmpNo = availableUser.getEmployeeID();

        availableUser.setEmployeeID(availableUserEmpNo);
        availableUser.setName(userDTO.getName());
        //availableUser.setPassword(userDTO.getPassword());
        availableUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword())); //this is used to set password with security
        availableUser.setPhoneNo(userDTO.getPhoneNo());
        availableUser.setEmergencyName(userDTO.getEmergencyName());
        availableUser.setEmergencyPhoneNo(userDTO.getEmergencyPhoneNo());
        availableUser.setBirthDay(userDTO.getBirthDay());
        availableUser.setAddress(userDTO.getAddress());
//        availableUser.setSiteName(userDTO.getSiteName());
        availableUser.setSite(siteRepository.getReferenceById(userDTO.getSiteID()));
        availableUser.setBloodGroup(userDTO.getBloodGroup());
        availableUser.setJobRole(jobRoleRepository.getReferenceById(userDTO.getRoleID()));
        availableUser.setSalaryRate(salaryRateRepository.getReferenceById(userDTO.getRateID()));
        userRepository.save(availableUser);
        return "Success";
    }

    // update user
    public String updateUserPassword(UserDTO userDTO) {

        int updatableEmpID = userDTO.getEmployeeID();

        // Check if the User exists
        Optional<User> checkEmployee = userRepository.findById(updatableEmpID);
        if (checkEmployee.isEmpty()) {
            throw new UserNotFoundException("Requested User not exist");
        }

        String oldPassword = userDTO.getOldPassword();
        String currentPassword = checkEmployee.get().getPassword();
        if(passwordEncoder.matches(oldPassword, currentPassword)){
            // Now you can safely retrieve the attendance
            User availableUser = checkEmployee.get();
            int availableUserEmpNo = availableUser.getEmployeeID();

            availableUser.setEmployeeID(availableUserEmpNo);
            availableUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword())); //this is used to set password with security
            userRepository.save(availableUser);
            return "Successfully Changed the password";
        }
        return "Old password is wrong";

    }

    // delete user
    public ResponseEntity<String> deleteUser(int employeeID) {
        User user = userRepository.findById(employeeID).orElse(null);

        if (user != null) {
            user.setJobRole(null);
            user.setSalaryRate(null);
            user.setSite(null);
            userRepository.delete(user);
            return new ResponseEntity<>("User Deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // delete user
    public User getUser(int employeeID) {
        // More Business Logic
        if(userRepository.findById(employeeID).isEmpty())
            throw new UserNotFoundException("Requested User not exist");

        return userRepository.findById(employeeID).get();
    }


    public List<User> getAllUers() {
        // More Business Logic
        List<User> users = userRepository.findAll();

         return users;
    }

    // Get Username only
    public String getUserName(int employeeID) {
        // More Business Logic
        if(userRepository.findById(employeeID).isEmpty())
            throw new UserNotFoundException("Requested User not exist");

        return userRepository.findById(employeeID).get().getName();
    }

    // Find all active users by constructed query
    public List<User> getAllActiveUsers() {
        // More Business Logic
        List<User> users = userRepository.findAllUsers();

        return users;
    }

    // Find all active Supervisors by constructed query (used)
    public List<User> getAllSupervisors() {
        // More Business Logic
        List<User> users = userRepository.findAllSupervisors();

        return users;
    }

    // Find all active Labors by constructed query (used)
    public List<User> getAllLabors() {
        // More Business Logic
        List<User> users = userRepository.findAllLabors();
        return users;
    }

    // Find all active usernames by constructed query
    public List<?> getAllUserNames() {
        // More Business Logic
        List<?> userNames = userRepository.findAllUserNames();

        return userNames;
    }

    // Find Name and Password by constructed query
    public List<NamePasswordDTO> getAllNameWithPassword(){
        List<NamePasswordDTO> ob1 = userRepository.findNameWithPassword();
        return ob1;
    }

    // Find Name and Password by constructed query
    public List<DailyJobDTO> getDailyJob(){
        List<DailyJobDTO> ob2 =userRepository .findDailyJob();
        return ob2;
    }




}
