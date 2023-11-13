//package com.example.labor_management_project.controller;
//
//import com.example.labor_management_project.dto.LoginDTO;
//import com.example.labor_management_project.resopnse.LoginResponse;
//import com.example.labor_management_project.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/login")
//public class LoginController {
//
//    @Autowired
//    LoginService loginService;
//
//    @PostMapping
//    public ResponseEntity<LoginResponse.LoginGenResponse> login (@RequestBody LoginDTO loginDTO){
////        return loginService.loginUserCondition(loginDTO);
//        return ResponseEntity.ok().body(loginService.loginUserCondition(loginDTO));
//    }
//}
