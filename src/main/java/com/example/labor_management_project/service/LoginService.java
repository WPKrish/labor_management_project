//package com.example.labor_management_project.service;
//
//import com.example.labor_management_project.dto.LoginDTO;
//import com.example.labor_management_project.model.JobRole;
//import com.example.labor_management_project.model.User;
//import com.example.labor_management_project.repository.JobRoleRepository;
//import com.example.labor_management_project.repository.LoginRepository;
//import com.example.labor_management_project.repository.UserRepository;
//import com.example.labor_management_project.resopnse.LoginResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class LoginService {
//
//    @Autowired
//    LoginRepository loginRepository;
//
//    @Autowired
//    JobRoleRepository jobRoleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    public LoginResponse.LoginGenResponse loginUserCondition(LoginDTO loginDTO) {
//
//        String msg ="";
//        String inputPassword = loginDTO.getPassword();
//        int inputEmployeeID = loginDTO.getEmployeeID();
//
//        // Check if the User exists
//        Optional<User> loginUser = userRepository.findById(loginDTO.getEmployeeID());
//
//        if (loginUser.isEmpty()) {
////            return new ResponseEntity<>("Requested User not exist", HttpStatus.NOT_FOUND);
//            return LoginResponse.LoginGenResponse.builder()
//                    .message("Requested User not exist")
//                    .status(false)
//                    .build();
//
//        }
//
//        else{
//            Optional<JobRole> jobRole = jobRoleRepository.findById(loginUser.get().getJobRole().getRoleID());
//            String role = jobRole.get().getRole();
//
//            String encodedPassword = loginUser.get().getPassword();
//            boolean isPasswordRight = passwordEncoder.matches(inputPassword, encodedPassword);
//
//            if(isPasswordRight){
//                Optional<User> user = loginRepository.findOneByEmployeeIDAndPassword(inputEmployeeID, encodedPassword );
//
//                if(user.isPresent()  && role.equals("Admin")){
////                    return new LoginResponse("Login Success as Admin", true);
////                    return new ResponseEntity<>("Login Success as Admin", HttpStatus.OK);
//                    return LoginResponse.LoginGenResponse.builder()
//                            .message("Login Success as Admin")
//                            .status(true)
//                            .user(user.get())
//                            .build();
//                }
//                else if(user.isPresent()  && role.equals("Supervisor")){
////                    return new LoginResponse("Login Success as Supervisor", true);
////                    return new ResponseEntity<>("Login Success as Supervisor", HttpStatus.OK);
//                    return LoginResponse.LoginGenResponse.builder()
//                            .message("Login Success as Supervisor")
//                            .status(true)
//                            .user(user.get())
//                            .build();
//                }
//                else if(user.isPresent()  && role.equals("Labor")){
////                    return new LoginResponse("Login Success as Labor", true);
////                    return new ResponseEntity<>("Login Success as Labor", HttpStatus.OK);
//                    return LoginResponse.LoginGenResponse.builder()
//                            .message("Login Success as Labor")
//                            .status(true)
//                            .user(user.get())
//                            .build();
//                }
//                else{
////                    return new LoginResponse("Login Failed", false);
////                    return new ResponseEntity<>("Login Failed", HttpStatus.NOT_FOUND);
//                    return LoginResponse.LoginGenResponse.builder()
//                            .message("Login Failed")
//                            .status(false)
//                            .build();
//                }
//            }
//            else{
////                return new LoginResponse("Password Not Match with employee ID", false);
////                return new ResponseEntity<>("Password Not Match with employee ID", HttpStatus.CONFLICT);
//                return LoginResponse.LoginGenResponse.builder()
//                        .message("Password Not Match with employee ID")
//                        .status(false)
//                        .build();
//            }
//        }
//
//    }
//
//
//}
