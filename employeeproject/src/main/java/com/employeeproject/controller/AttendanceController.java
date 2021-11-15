package com.employeeproject.controller;

import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeeproject.common.CommonResponse;
import com.employeeproject.service.AttendanceDetailsService;

@RestController
@CrossOrigin(origins = "http://localhost")
public class AttendanceController {

	@Autowired
	AttendanceDetailsService attendanceDetailsService;

	/**
	 * @author ashish
	 * @apiNote to save attendance= true of employee
	 * @param empId,todayDate
	 */
	@PostMapping("/addAttendanceById")
	public ResponseEntity<CommonResponse> addAttendanceByAttendanceId(@RequestParam("empId") long empId) {
		return new ResponseEntity<>(attendanceDetailsService.addAttendanceById(empId), HttpStatus.CREATED);
	}

	/**
	 * @author ashish
	 * @apiNote get attendance of employee month filter used for generating salary slip
	 * @param empId,todayDate
	 */
	@GetMapping("/getAttendanceByEmpIdAndMonth")
	public ResponseEntity<CommonResponse> getAttendanceByEmpIdAndMonth(@RequestParam("empId") long empId,
			@RequestParam("month") Month month, @RequestParam("year") int year) {
		return new ResponseEntity<>(attendanceDetailsService.getAttendanceByEmpIdAndMonth(empId, month, year),
				HttpStatus.CREATED);
	}

	/**
	 * @author Aniket
	 * @apiNote get Employees Current Date Attendance
	 */
	@GetMapping("/getEmployeesCurrentDateAttendance")
	public ResponseEntity<CommonResponse> getEmployeesCurrentDateAttendance() {
		return new ResponseEntity<>(attendanceDetailsService.getEmployeesCurrentDateAttendance(), HttpStatus.OK);

	}

	/**
	 * @author Aniket
	 * @apiNote get Employees Current Date Attendance
	 */
	@GetMapping("/getEmployeeOneMonthAttendance")
	public ResponseEntity<CommonResponse> getEmployeeOneMonthAttendance(@RequestParam("empId") long empId,
			@RequestParam("month") Month month, @RequestParam("year") int year) {
		return new ResponseEntity<>(attendanceDetailsService.getEmployeeOneMonthAttendance(empId, month, year),
				HttpStatus.OK);

	}

	@GetMapping("/generatesheet")
	public ResponseEntity<CommonResponse> generatesheet(@RequestParam("empId") long empId,
			@RequestParam("month") Month month, @RequestParam("year") int year) {
		return new ResponseEntity<>(attendanceDetailsService.generateSheet(empId, month, year), HttpStatus.OK);

	}
	
	/**
	 * @author Akshay
	 * @apiNote get Employee leave
	 * @param empId,month,year
	 */
	@GetMapping("/getEmployeeLeave")
	public ResponseEntity<CommonResponse> getEmployeeLeave(@RequestParam("empId") long empId,
			@RequestParam("month") Month month, @RequestParam("year") int year) {
		return new ResponseEntity<>(attendanceDetailsService.getEmployeeLeaveByEmpIdAndMonth(empId, month, year),
				HttpStatus.OK);

	}
}
