package com.employeeproject.service;

import com.employeeproject.common.CommonResponse;
import com.employeeproject.dto.EmployeeDto;
import com.employeeproject.entity.EmployeeDetails;

public interface EmployeeService {

	CommonResponse saveEmpDetails(EmployeeDto employeeDto);

	EmployeeDetails getEmpByEmailId(String emailId);

	CommonResponse deleteEmployee(long empId);

	CommonResponse getAllEmployee();

	CommonResponse updateEmployee(EmployeeDto employeeDto);

	CommonResponse getEmpById(long empId);

}
