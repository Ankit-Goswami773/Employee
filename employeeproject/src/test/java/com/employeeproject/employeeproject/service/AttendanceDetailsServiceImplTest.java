package com.employeeproject.employeeproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.employeeproject.applications_exceptions.EmployeeCommonException;
import com.employeeproject.common.CommonResponse;
import com.employeeproject.entity.AttendanceDetails;
import com.employeeproject.entity.EmployeeDetails;
import com.employeeproject.repository.AttendanceDetailsRepository;
import com.employeeproject.repository.EmployeeDetailsRepository;
import com.employeeproject.service.AttendanceDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class AttendanceDetailsServiceImplTest {

	@InjectMocks
	AttendanceDetailsServiceImpl attendanceDetailsServiceImpl;

	@Mock
	AttendanceDetailsRepository attendanceDetailsRepo;

	@Mock
	EmployeeDetailsRepository employeeDetailsRepository;

	@Test
	void testAddAttendanceById() {
		LocalDate date = LocalDate.now();
		String dayOfWeek = date.getDayOfWeek().toString();
		EmployeeDetails details1 = new EmployeeDetails(3, "Akshay", "Kag", "akshaykag1998@gmail.com", "Developer", "CS",
				20000, "INACTIVE");
		AttendanceDetails attendanceDetails1 = new AttendanceDetails(0, date, dayOfWeek, details1);

		EmployeeDetails details2 = new EmployeeDetails(3, "Akshay", "Kag", "akshaykag1998@gmail.com", "Developer", "CS",
				20000, "INACTIVE");
		AttendanceDetails attendanceDetails2 = new AttendanceDetails(0, date, dayOfWeek, details2);

		when(employeeDetailsRepository.findById(3l)).thenReturn(Optional.of(details1));
		when(attendanceDetailsRepo.saveAttendance(3, dayOfWeek, date)).thenReturn(1);

		CommonResponse commonResponse = new CommonResponse(200, "OK", attendanceDetails1);
		CommonResponse commonResponseReturn = attendanceDetailsServiceImpl.addAttendanceById(3);

		assertEquals(200, commonResponseReturn.getStatusCode());
		assertEquals("Attendance saved sucessfully", commonResponseReturn.getMessage());

		when(attendanceDetailsRepo.getEmpIdAndDate(3, date)).thenReturn(attendanceDetails1);
		assertThrows(EmployeeCommonException.class, () -> {
			attendanceDetailsServiceImpl.addAttendanceById(3);
		});

		when(attendanceDetailsRepo.getEmpIdAndDate(3, date)).thenReturn(null);
		when(attendanceDetailsRepo.saveAttendance(3, dayOfWeek, date)).thenReturn(0);
		assertThrows(EmployeeCommonException.class, () -> {
			attendanceDetailsServiceImpl.addAttendanceById(3);
		});
	}

	@Test
	void testGetAttendanceByEmpIdAndMonth() {

		EmployeeDetails emp = new EmployeeDetails(3, "Aniket", "Soni", "aniket@gmail.com", "Developer", "CS", 15000,
				"ACTIVE");

		List<AttendanceDetails> list = new ArrayList<AttendanceDetails>();
		list.add(new AttendanceDetails(0, LocalDate.of(2021, Month.NOVEMBER, 01), "Monday", emp));
		list.add(new AttendanceDetails(0, LocalDate.of(2021, Month.NOVEMBER, 02), "Tuesday", emp));
		list.add(new AttendanceDetails(0, LocalDate.of(2021, Month.NOVEMBER, 03), "Wednesday", emp));
		list.add(new AttendanceDetails(0, LocalDate.of(2021, Month.NOVEMBER, 04), "Thursday", emp));

		when(employeeDetailsRepository.findById(3l)).thenReturn(Optional.of(emp));
		when(attendanceDetailsRepo.findAll()).thenReturn(list);

		CommonResponse response = attendanceDetailsServiceImpl.getAttendanceByEmpIdAndMonth(3, Month.NOVEMBER, 2021);

		assertEquals(200, response.getStatusCode());
		assertEquals("sucessfull", response.getMessage());
	}

	@Test
	void testGetEmployeesCurrentDateAttendance() {

		LocalDate date = LocalDate.now();
		String day = date.getDayOfWeek().toString();

		EmployeeDetails emp1 = new EmployeeDetails(1, "Aniket", "Soni", "aniket@gmail.com", "Developer", "CS", 15000,
				"ACTIVE");
		EmployeeDetails emp2 = new EmployeeDetails(2, "Ashish", "Wankhade", "ashish@gmail.com", "Developer", "CS",
				20000, "ACTIVE");
		EmployeeDetails emp3 = new EmployeeDetails(3, "AKshay", "Kag", "akshay@gmail.com", "Developer", "CS", 15000,
				"ACTIVE");

		List<EmployeeDetails> employees = new ArrayList<EmployeeDetails>();
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);

		AttendanceDetails attend = new AttendanceDetails(1, date, day, emp1);

		when(employeeDetailsRepository.findAll()).thenReturn(employees);
		when(attendanceDetailsRepo.getEmpIdAndDate(1, date)).thenReturn(attend);

		CommonResponse response = attendanceDetailsServiceImpl.getEmployeesCurrentDateAttendance();

		assertEquals(200, response.getStatusCode());
		assertEquals("Employees Current date Attendance list", response.getMessage());

		List<EmployeeDetails> attendance = new ArrayList<>();

		when(employeeDetailsRepository.findAll()).thenReturn(attendance);
		assertThrows(EmployeeCommonException.class, () -> {
			attendanceDetailsServiceImpl.getEmployeesCurrentDateAttendance();
		});

	}

	@Test
	void testGetEmployeeOneMonthAttendance() {
		EmployeeDetails emp = new EmployeeDetails(1, "Aniket", "Soni", "aniket@gmail.com", "Developer", "CS", 15000,
				"ACTIVE");

		List<AttendanceDetails> attend = new ArrayList<AttendanceDetails>();
		attend.add(new AttendanceDetails(1, LocalDate.of(2021, Month.NOVEMBER, 01), "Monday", emp));
		attend.add(new AttendanceDetails(2, LocalDate.of(2021, Month.NOVEMBER, 02), "Tuesday", emp));
		attend.add(new AttendanceDetails(3, LocalDate.of(2021, Month.NOVEMBER, 03), "Wednesday", emp));
		attend.add(new AttendanceDetails(4, LocalDate.of(2021, Month.NOVEMBER, 04), "Thursday", emp));

		when(attendanceDetailsRepo.findAll()).thenReturn(attend);

		CommonResponse response = attendanceDetailsServiceImpl.getEmployeeOneMonthAttendance(1, Month.NOVEMBER, 2021);

		assertEquals(200, response.getStatusCode());
		assertEquals("NOVEMBER Month attendance", response.getMessage());

	}

	@Test
	void testGetEmployeeLeave() {

		EmployeeDetails emp = new EmployeeDetails(1, "Aniket", "Soni", "aniket@gmail.com", "Developer", "CS", 15000,
				"ACTIVE");

		List<AttendanceDetails> attend = new ArrayList<AttendanceDetails>();
		attend.add(new AttendanceDetails(1, LocalDate.of(2021, Month.NOVEMBER, 01), "Monday", emp));
		attend.add(new AttendanceDetails(2, LocalDate.of(2021, Month.NOVEMBER, 02), "Tuesday", emp));
		attend.add(new AttendanceDetails(3, LocalDate.of(2021, Month.NOVEMBER, 03), "Wednesday", emp));
		attend.add(new AttendanceDetails(4, LocalDate.of(2021, Month.NOVEMBER, 04), "Thursday", emp));
		when(attendanceDetailsRepo.findAll()).thenReturn(attend);

		CommonResponse response = attendanceDetailsServiceImpl.getEmployeeLeaveByEmpIdAndMonth(1, Month.NOVEMBER, 2021);
		assertEquals(200, response.getStatusCode());
		assertEquals("Employee No. of leaves", response.getMessage());

	}

}
