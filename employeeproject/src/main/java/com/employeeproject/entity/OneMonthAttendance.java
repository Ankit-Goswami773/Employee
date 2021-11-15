package com.employeeproject.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OneMonthAttendance {

	
	private long attendanceId;
	private LocalDate date;
	private String day;
	private String attendanceStatus;
}