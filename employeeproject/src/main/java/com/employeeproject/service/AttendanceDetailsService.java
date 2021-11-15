package com.employeeproject.service;

import java.time.Month;

import com.employeeproject.common.CommonResponse;

public interface AttendanceDetailsService {


	CommonResponse addAttendanceById(long empId);

	CommonResponse getAttendanceByEmpIdAndMonth(long empId, Month month, int year);

	 CommonResponse getEmployeesCurrentDateAttendance();

	CommonResponse getEmployeeOneMonthAttendance(long empId, Month month, int year);

	CommonResponse generateSheet(long empId, Month month, int year); 

	CommonResponse getEmployeeLeaveByEmpIdAndMonth(long empId, Month month, int year); 


}
