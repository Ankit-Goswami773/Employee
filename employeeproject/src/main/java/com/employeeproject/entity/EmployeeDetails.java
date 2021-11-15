package com.employeeproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_details", uniqueConstraints = { @UniqueConstraint(columnNames = { "emailId" }) })
public class EmployeeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String designation;
	private String department;
	private double basicSalary;
	private String status;

	@PrePersist
	public void statusCheckIfNull() {
		if( status == null ) status = "ACTIVE";
	}
}