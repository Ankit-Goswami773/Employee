package com.employeeproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {

	private long empId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String designation;
	private String department;
	private double basicSalary;
	private String status;
	
}
