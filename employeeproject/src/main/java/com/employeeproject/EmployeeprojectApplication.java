package com.employeeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmployeeprojectApplication {
 

	public static void main(String[] args) {
		SpringApplication.run(EmployeeprojectApplication.class, args);
	}

}
