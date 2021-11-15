package com.employeeproject.employeeproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.employeeproject.common.CommonResponse;
import com.employeeproject.dto.EmployeeDto;
import com.employeeproject.entity.EmployeeDetails;
import com.employeeproject.repository.EmployeeDetailsRepository;
import com.employeeproject.service.EmployeeServiceImpl;
import com.employeeproject.util.AppUtillDtoToEntity;

@SpringBootTest
 class EmployeeServiceTest {

	@Mock
	AppUtillDtoToEntity appUtillDtoToEntity;

	@Mock
	EmployeeDetailsRepository employeeDetailsRepository;

	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;

	List<EmployeeDetails> employeelist = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		employeelist.add(new EmployeeDetails(1, "Ankit", "Goswami", "ankit.goswami@5exceptions.com", "Developer", "it",
				50000, ""));
		employeelist.add(new EmployeeDetails(2, "Yash", "Goswami", "yash.goswami@5exceptions.com", "Developer", "it",
				20000, ""));
		employeelist.add(
				new EmployeeDetails(3, "Dev", "Chouhan", "dev.chouhan@5exceptions.com", "Developer", "it", 30000, ""));
	}

	@Test
	void saveEmpTest() {
		EmployeeDto employeeDto = new EmployeeDto(1, "Ankit", "Goswami", "ankit.goswami@5exceptions.com", "Developer",
				"it", 50000, "");
		EmployeeDetails employee = new EmployeeDetails(1, "Ankit", "Goswami", "ankit.goswami@5exceptions.com",
				"Developer", "it", 50000, "");

		when(appUtillDtoToEntity.dtoToEntity(employeeDto)).thenReturn(employee);

		when(employeeDetailsRepository.save(employee)).thenReturn(employee);

		CommonResponse saveEmpDetails = employeeServiceImpl.saveEmpDetails(employeeDto);
		EmployeeDetails emp = (EmployeeDetails) saveEmpDetails.getData();
		assertEquals(200, saveEmpDetails.getStatusCode());
		assertEquals("employee saved sucessfully", saveEmpDetails.getMessage());
		assertEquals(employee, emp);

	}

	@Test
	void getAllEmployeeTest() {
		when(employeeDetailsRepository.findAll()).thenReturn(employeelist);

		CommonResponse allEmployee = employeeServiceImpl.getAllEmployee();
		assertEquals(200, allEmployee.getStatusCode());
		assertEquals("Success", allEmployee.getMessage());
		assertEquals(employeelist, allEmployee.getData());
	}

	@Test
	void getEmpByemailIdTest() {
		EmployeeDetails employee = new EmployeeDetails(1, "Ankit", "Goswami", "ankit.goswami@5exceptions.com",
				"Developer", "it", 50000, "");
		when(employeeDetailsRepository.getEmployeeByEmailId(employee.getEmailId())).thenReturn(employee);
		EmployeeDetails empByEmailId = employeeServiceImpl.getEmpByEmailId(employee.getEmailId());
		assertEquals(employee.getEmpId(), empByEmailId.getEmpId());
		assertEquals(employee.getFirstName(), empByEmailId.getFirstName());
		assertEquals(employee.getEmailId(), empByEmailId.getEmailId());
	}

	@Test
	void deleteEmployeeTest() {
		EmployeeDetails employee = new EmployeeDetails(1, "ankit", "Goswami", "ankit.goswami@5exceptions.com",
				"java Developer", "it", 5000, "ACTIVE");
		when(employeeDetailsRepository.findById(employee.getEmpId())).thenReturn(Optional.of(employee));
		when(employeeDetailsRepository.save(employee)).thenReturn(employee);
		CommonResponse deleteEmployee = employeeServiceImpl.deleteEmployee(employee.getEmpId());
		EmployeeDetails emp = (EmployeeDetails) deleteEmployee.getData();
		assertEquals(200, deleteEmployee.getStatusCode());
		assertEquals("employee status update sucessfully", deleteEmployee.getMessage());
		assertEquals("INACTIVE", emp.getStatus());

	}

	@Test
	void updateEmployeeTest() {
		EmployeeDto employeeDto = new EmployeeDto(1, "Ankit", "Goswami", "ankit.goswami@5exceptions.com", "Developer",
				"it", 50000, "");
		EmployeeDetails employee = new EmployeeDetails(1, "Ankit", "Goswami", "ankit.goswami@5exceptions.com",
				"Developer", "it", 50000, "");
		
		when(appUtillDtoToEntity.dtoToEntity(employeeDto)).thenReturn(employee);
		when(employeeDetailsRepository.save(employee)).thenReturn(employee);
		employee.setDepartment("hr");
		CommonResponse response = employeeServiceImpl.updateEmployee(employeeDto);
		EmployeeDetails data = (EmployeeDetails) response.getData();
		assertEquals(200, response.getStatusCode());
		assertEquals("Employee Data Sussessfully", response.getMessage());
		assertEquals(employee.getDepartment(), data.getDepartment());

	}

	@Test
	void getEmpByIdTest() {
		EmployeeDetails employee = new EmployeeDetails(1, "ankit", "Goswami", "ankit.goswami@5exceptions.com",
				"java Developer", "it", 5000, "ACTIVE");

		when(employeeDetailsRepository.findById(employee.getEmpId())).thenReturn(Optional.of(employee));

		CommonResponse response = employeeServiceImpl.getEmpById(employee.getEmpId());
		EmployeeDetails emp = (EmployeeDetails) response.getData();
		assertEquals(200, response.getStatusCode());
		assertEquals("Employee Id to get data successfully", response.getMessage());
		assertEquals(employee, response.getData());

	}

}
