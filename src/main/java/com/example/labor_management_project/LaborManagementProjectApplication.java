package com.example.labor_management_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LaborManagementProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaborManagementProjectApplication.class, args);
	}

}
