package com.employeeproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeproject.applications_exceptions.EmployeeCommonException;
import com.employeeproject.common.ApplicationConstant;
import com.employeeproject.common.CommonResponse;
import com.employeeproject.dto.EmployeeDto;
import com.employeeproject.entity.EmployeeDetails;
import com.employeeproject.repository.EmployeeDetailsRepository;
import com.employeeproject.util.AppUtillDtoToEntity;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;
	@Autowired
	private AppUtillDtoToEntity appUtillDtoToEntity;

	@Override
	public CommonResponse saveEmpDetails(EmployeeDto employeeDto) {

		EmployeeDetails employeeDetails = appUtillDtoToEntity.dtoToEntity(employeeDto);
		System.out.println("app");
		validateEmployeeDetails(employeeDetails);

		employeeDetails.setStatus("ACTIVE");
		boolean check = getEmployeeByEmailId(employeeDetails.getEmailId());
		if (check) {
			throw new EmployeeCommonException("Email Id already present in database");
		}

		EmployeeDetails savedEmployee = employeeDetailsRepository.save(employeeDetails);

		return new CommonResponse(200, "employee saved sucessfully", savedEmployee);

	}

	@Override
	public EmployeeDetails getEmpByEmailId(String emailId) {
		EmployeeDetails employeeByEmailId = employeeDetailsRepository.getEmployeeByEmailId(emailId);

		if (employeeByEmailId == null || !employeeByEmailId.getEmailId().equalsIgnoreCase(emailId))
			throw new EmployeeCommonException("please enter valid Email Id or email is not present");
		return employeeDetailsRepository.getEmployeeByEmailId(emailId);
	}

	@Override
	public CommonResponse deleteEmployee(long empId) {
		if (empId == 0) {
			throw new EmployeeCommonException("Employee id can not be 0");
		}
		EmployeeDetails employeeDetails = employeeDetailsRepository.findById(empId).orElse(null);

		if (employeeDetails == null) {
			throw new EmployeeCommonException("Employee not found");
		}

		employeeDetails.setStatus(ApplicationConstant.EMPLOYE_STATUS_INACTIVE);
		EmployeeDetails save = employeeDetailsRepository.save(employeeDetails);

		return new CommonResponse(200, "employee status update sucessfully", save);
	}

	private void validateEmployeeDetails(EmployeeDetails employeeDetails) {
		if (employeeDetails.getEmailId() == null || employeeDetails.getEmailId().isEmpty())
			throw new EmployeeCommonException("email id compulsary");
		if (employeeDetails.getFirstName() == null || employeeDetails.getFirstName().isEmpty())
			throw new EmployeeCommonException("first name can not be empty");
		if (employeeDetails.getLastName() == null || employeeDetails.getLastName().isEmpty())
			throw new EmployeeCommonException("last name can not be empty");
		if (employeeDetails.getDesignation() == null || employeeDetails.getDesignation().isEmpty())
			throw new EmployeeCommonException("Designation can not be empty");
		if (employeeDetails.getDepartment() == null || employeeDetails.getDepartment().isEmpty())
			throw new EmployeeCommonException("Department can not be empty");
		if (employeeDetails.getBasicSalary() == 0.0)
			throw new EmployeeCommonException("basic salary can not be insert 0");
	}

	public boolean getEmployeeByEmailId(String emailId) {
		boolean check = false;
		EmployeeDetails employeeByEmailId = employeeDetailsRepository.getEmployeeByEmailId(emailId);

		if (employeeByEmailId != null) {
			check = true;
		}
		return check;
	}

	@Override
	public CommonResponse getAllEmployee() {
		List<EmployeeDetails> findAll = employeeDetailsRepository.findAll();

		if (findAll.isEmpty())
			throw new EmployeeCommonException("No data");
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatusCode(200);
		commonResponse.setMessage("Success");
		commonResponse.setData(findAll);
		return commonResponse;

	}

	@Override
	public CommonResponse updateEmployee(EmployeeDto employeeDto) {

		EmployeeDetails employeeDetails = appUtillDtoToEntity.dtoToEntity(employeeDto);

		if (employeeDetails.getEmpId() == 0) {
			throw new EmployeeCommonException("Employee id not a 0");
		}
		validateEmployeeDetails(employeeDetails);
		EmployeeDetails save = employeeDetailsRepository.save(employeeDetails);
		return new CommonResponse(200, "Employee Data Sussessfully", save);
	}

	@Override
	public CommonResponse getEmpById(long empId) {
		EmployeeDetails employeeDetails = employeeDetailsRepository.findById(empId).orElse(null);
		if (employeeDetails == null) {
			throw new EmployeeCommonException("employee id not found");
		}
		return new CommonResponse(200, "Employee Id to get data successfully", employeeDetails);
	}

}
