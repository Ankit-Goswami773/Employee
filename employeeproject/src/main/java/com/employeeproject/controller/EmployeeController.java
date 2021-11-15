package com.employeeproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.employeeproject.common.CommonResponse;
import com.employeeproject.dto.EmployeeDto;
import com.employeeproject.entity.EmployeeDetails;
import com.employeeproject.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/addEmp")
	public ResponseEntity<CommonResponse> addEmp(@RequestBody EmployeeDto employeeDto) {
		return new ResponseEntity<>(employeeService.saveEmpDetails(employeeDto), HttpStatus.OK);

	}

	@GetMapping("/getEmpByEmailId")
	public ResponseEntity<EmployeeDetails> getEmployeeByEmailId(@RequestParam("emailId") String emailId) {
		return new ResponseEntity<>(employeeService.getEmpByEmailId(emailId), HttpStatus.OK);

	}

	@DeleteMapping("/deleteEmployee/{empId}")
	public ResponseEntity<CommonResponse> deleteEmployee(@PathVariable("empId") long empId) {

		return new ResponseEntity<>(employeeService.deleteEmployee(empId), HttpStatus.OK);
	}

	@PutMapping("/updateEmployee")
	public ResponseEntity<CommonResponse> updateEmployee(@RequestBody EmployeeDto employeeDto) {
		return new ResponseEntity<>(employeeService.updateEmployee(employeeDto), HttpStatus.OK);
	}
 
	@GetMapping("getEmpById/{empId}")
	public ResponseEntity<CommonResponse> getEmployeeByEmpId(@PathVariable("empId") long empId) {

		return new ResponseEntity<>(employeeService.getEmpById(empId), HttpStatus.OK);
	}

	@GetMapping("/getAllEmployee")
	public ResponseEntity<CommonResponse> getAllEmployee() {
		return new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK);
	}

}
