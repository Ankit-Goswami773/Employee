package com.employeeproject.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDetails {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long attendanceId;
	private LocalDate date;
	private String day;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "emp_id")
	private EmployeeDetails employeeDetails;

}