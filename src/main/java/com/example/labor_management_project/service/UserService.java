package com.example.labor_management_project.service;

import com.example.labor_management_project.dto.DailyJobDTO;
import com.example.labor_management_project.dto.NamePasswordDTO;
import com.example.labor_management_project.dto.UserDTO;
import com.example.labor_management_project.exception.UserNotFoundException;
import com.example.labor_management_project.model.Attendance;
import com.example.labor_management_project.model.JobRole;
import com.example.labor_management_project.model.User;
import com.example.labor_management_project.repository.*;
import com.example.labor_management_project.resopnse.LoginResponse;
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
            tempUser.setUsername(userDTO.getUsername());
            //tempUser.setPassword(userDTO.getPassword()); //this is used to set password without security
            tempUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword())); //this is used to set password with security
//            tempUser.setPassword(this.passwordEncoder.encode("123")); //this is used to set password with security and without user working
            tempUser.setPhoneNo(userDTO.getPhoneNo());
            tempUser.setEmergencyName(userDTO.getEmergencyName());
            tempUser.setEmergencyPhoneNo(userDTO.getEmergencyPhoneNo());
            tempUser.setBirthDay(userDTO.getBirthDay());
            tempUser.setAddress(userDTO.getAddress());
        tempUser.setSite(siteRepository.getReferenceById(userDTO.getSiteID()));
            tempUser.setBloodGroup(userDTO.getBloodGroup());
            tempUser.setActiveUser(true); // newly added
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
        availableUser.setUsername(userDTO.getUsername());
        availableUser.setName(userDTO.getName());
        //availableUser.setPassword(userDTO.getPassword());
        availableUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword())); //this is used to set password with security
        availableUser.setPhoneNo(userDTO.getPhoneNo());
        availableUser.setEmergencyName(userDTO.getEmergencyName());
        availableUser.setEmergencyPhoneNo(userDTO.getEmergencyPhoneNo());
        availableUser.setBirthDay(userDTO.getBirthDay());
        availableUser.setAddress(userDTO.getAddress());
        availableUser.setActiveUser(true); // newly added
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

        if (user != null && user.isActiveUser()==true) {
//            user.setJobRole(null);
//            user.setSalaryRate(null);
//            user.setSite(null);
//            userRepository.delete(user);
              user.setActiveUser(false); // this is used to isNotActive to user instead of delete
            userRepository.save(user);
            return new ResponseEntity<>("User Deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // delete user
    public User getUser(int employeeID) {
        if(userRepository.findById(employeeID).isEmpty())
            throw new UserNotFoundException("Requested User not exist");

        return userRepository.findById(employeeID).get();
    }


    public List<User> getAllUers() {
        List<User> users = userRepository.findAll();

         return users;
    }

    // Get Username only
    public String getUserName(int employeeID) {
        if(userRepository.findById(employeeID).isEmpty())
            throw new UserNotFoundException("Requested User not exist");

        return userRepository.findById(employeeID).get().getName();
    }

    // Find all active users by constructed query
    public List<User> getAllActiveUsers() {
        List<User> users = userRepository.findAllUsers();

        return users;
    }

    // Find all active Supervisors by constructed query (used)
    public List<User> getAllSupervisors() {
        List<User> users = userRepository.findAllSupervisors();

        return users;
    }

    // Find all active Labors by constructed query (used)
    public List<User> getAllLabors() {
        List<User> users = userRepository.findAllLabors();
        return users;
    }

    // Find all active usernames by constructed query
    public List<?> getAllUserNames() {
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


    // Create Login Service
    public LoginResponse.LoginGenResponse loginUserCondition(UserDTO loginDTO) {

        String msg = "";
        String inputPassword = loginDTO.getPassword();
//        int inputEmployeeID = loginDTO.getEmployeeID();
        String inputUsername = loginDTO.getUsername();

        System.out.println("Log1");

        // Check if the User exists
        Optional<User> loginUser = userRepository.findByUsername(loginDTO.getUsername());
        System.out.println("Log"+loginUser);

        if (loginUser.isEmpty()) {
//            return new ResponseEntity<>("Requested User not exist", HttpStatus.NOT_FOUND);
            return LoginResponse.LoginGenResponse.builder()
                    .message("Requested User not exist")
                    .status(false)
                    .build();

        } else {
            Optional<JobRole> jobRole = jobRoleRepository.findById(loginUser.get().getJobRole().getRoleID());
            String role = jobRole.get().getRole();

            String encodedPassword = loginUser.get().getPassword();
            boolean isPasswordRight = passwordEncoder.matches(inputPassword, encodedPassword);

            if (isPasswordRight) {
//                Optional<User> user = userRepository.findOneByEmployeeIDAndPassword(inputEmployeeID, encodedPassword);
                Optional<User> user = userRepository.findOneByUsernameAndPassword(inputUsername, encodedPassword);

                if (user.isPresent() && role.equals("Admin")) {
//                    return new LoginResponse("Login Success as Admin", true);
//                    return new ResponseEntity<>("Login Success as Admin", HttpStatus.OK);
                    return LoginResponse.LoginGenResponse.builder()
                            .message("Login Success as Admin")
                            .status(true)
                            .user(user.get())
                            .build();
                } else if (user.isPresent() && role.equals("Supervisor")) {
//                    return new LoginResponse("Login Success as Supervisor", true);
//                    return new ResponseEntity<>("Login Success as Supervisor", HttpStatus.OK);
                    return LoginResponse.LoginGenResponse.builder()
                            .message("Login Success as Supervisor")
                            .status(true)
                            .user(user.get())
                            .build();
                } else if (user.isPresent() && role.equals("Labor")) {
//                    return new LoginResponse("Login Success as Labor", true);
//                    return new ResponseEntity<>("Login Success as Labor", HttpStatus.OK);
                    return LoginResponse.LoginGenResponse.builder()
                            .message("Login Success as Labor")
                            .status(true)
                            .user(user.get())
                            .build();
                } else {
//                    return new LoginResponse("Login Failed", false);
//                    return new ResponseEntity<>("Login Failed", HttpStatus.NOT_FOUND);
                    return LoginResponse.LoginGenResponse.builder()
                            .message("Login Failed")
                            .status(false)
                            .build();
                }
            } else {
//                return new LoginResponse("Password Not Match with employee ID", false);
//                return new ResponseEntity<>("Password Not Match with employee ID", HttpStatus.CONFLICT);
                return LoginResponse.LoginGenResponse.builder()
                        .message("Password Not Match with employee ID")
                        .status(false)
                        .build();
            }
        }


    }


}
