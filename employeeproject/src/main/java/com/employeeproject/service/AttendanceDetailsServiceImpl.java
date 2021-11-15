package com.employeeproject.service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeproject.applications_exceptions.EmployeeCommonException;
import com.employeeproject.common.CommonResponse;
import com.employeeproject.entity.AttendanceDetails;
import com.employeeproject.entity.EmpOneDayAttendance;
import com.employeeproject.entity.EmployeeDetails;
import com.employeeproject.entity.OneMonthAttendance;
import com.employeeproject.repository.AttendanceDetailsRepository;
import com.employeeproject.repository.EmployeeDetailsRepository;

@Service
public class AttendanceDetailsServiceImpl implements AttendanceDetailsService {

	@Autowired
	AttendanceDetailsRepository attendanceDetailsRepo;

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	ExcelService excelService;

	@Override
	public CommonResponse addAttendanceById(long empId) {
		validateEmployeeByEmpId(empId);

		LocalDate date = LocalDate.now();
		String dayOfWeek = date.getDayOfWeek().toString();
		AttendanceDetails attendanceDetails = attendanceDetailsRepo.getEmpIdAndDate(empId, date);
		if (attendanceDetails != null)
			throw new EmployeeCommonException("Employee already present");

		int saveAttendance = attendanceDetailsRepo.saveAttendance(empId, dayOfWeek, date);

		if (saveAttendance == 0) {
			throw new EmployeeCommonException("Error on save attendance");
		}
		return new CommonResponse(200, "Attendance saved sucessfully", "");
	}

	@Override
	public CommonResponse getAttendanceByEmpIdAndMonth(long empId, Month month, int year) {

		List<AttendanceDetails> list = new ArrayList<>();
		try {

			validateEmployeeByEmpId(empId);
			list = attendanceDetailsRepo
					.findAll().stream().filter(f -> f.getEmployeeDetails().getEmpId() == empId
							&& f.getDate().getMonth() == month && f.getDate().getYear() == year)
					.collect(Collectors.toList());

			if (list.isEmpty()) {
				throw new EmployeeCommonException("Employee has not any attendance");
			}

			return new CommonResponse(200, "sucessfull", list);

		} catch (EmployeeCommonException e) {

			return new CommonResponse(404, "Attendance Not Found for this month", list);
		}

	}

	public EmployeeDetails validateEmployeeByEmpId(long empId) {
		if (empId == 0) {
			throw new EmployeeCommonException("Employee id can not be 0");
		}

		Optional<EmployeeDetails> optional = employeeDetailsRepository.findById(empId);
		if (optional.isPresent()) {
			EmployeeDetails employeeDetails = optional.get();
			return employeeDetails;
		}
		
		else {
			 throw new EmployeeCommonException("Employee not found"); 
		}
	
	}
 
	@Override
	public CommonResponse getEmployeesCurrentDateAttendance() {

		LocalDate date = LocalDate.now();

		List<EmpOneDayAttendance> attendance = new ArrayList<>();

		List<EmployeeDetails> employees = employeeDetailsRepository.findAll();

		for (EmployeeDetails e : employees) {

			EmpOneDayAttendance emp = new EmpOneDayAttendance();

			emp.setEmployeeId(e.getEmpId());
			emp.setEmployeeName(e.getFirstName() + " " + e.getLastName());
			emp.setDate(date);
			emp.setDay(date.getDayOfWeek().toString());

			AttendanceDetails attend = attendanceDetailsRepo.getEmpIdAndDate(e.getEmpId(), date);

			if (attend != null) {
				emp.setStatus("PRESENT");
			} else {
				emp.setStatus("ABSENT");
			}

			attendance.add(emp);
		}

		if (attendance.isEmpty()) {
			throw new EmployeeCommonException("Attendance not found for " + date.toString());
		}

		return new CommonResponse(200, "Employees Current date Attendance list", attendance);
	}

	@Override
	public CommonResponse getEmployeeOneMonthAttendance(long empId, Month month, int year) {

		List<OneMonthAttendance> attendance = getAttendance(empId, month, year);

		if (attendance.isEmpty()) {
			throw new EmployeeCommonException("Attendance not found for this Month- " + month);
		}

		return new CommonResponse(200, month + " Month attendance", attendance);
	}

	@Override
	public CommonResponse generateSheet(long empId, Month month, int year) {

		List<OneMonthAttendance> attendance = getAttendance(empId, month, year);
		try {

			excelService.exportToExcel(attendance, empId);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}

	private List<OneMonthAttendance> getAttendance(long empId, Month month, int year) {
		int length = month.length(year % 4 == 0);
		List<AttendanceDetails> presentAttendance = attendanceDetailsRepo
				.findAll().stream().filter(f -> f.getEmployeeDetails().getEmpId() == empId
						&& f.getDate().getMonth() == month && f.getDate().getYear() == year)
				.collect(Collectors.toList());

		List<OneMonthAttendance> attendance = new ArrayList<>(length);

		List<LocalDate> dateList = presentAttendance.stream().map(AttendanceDetails::getDate)
				.collect(Collectors.toList());

		for (int m = 1; m <= length; m++) {
			OneMonthAttendance oma = new OneMonthAttendance();
			LocalDate date = LocalDate.of(year, month, m);
			oma.setAttendanceId(m);
			oma.setDate(date);
			oma.setDay(date.getDayOfWeek().toString());
			oma.setAttendanceStatus("ABSENT");
			attendance.add(oma);
		}

		attendance.stream().filter(f -> dateList.stream().anyMatch(d -> d.isEqual(f.getDate())))
				.forEach(m -> m.setAttendanceStatus("PRESENT")

				);
		return attendance;
	}

	@Override
	public CommonResponse getEmployeeLeaveByEmpIdAndMonth(long empId, Month month, int year) {

		List<OneMonthAttendance> attendance = getAttendance(empId, month, year);

		if (attendance.isEmpty()) {
			throw new EmployeeCommonException("Attendance not found for this Month- " + month);
		}

		List<OneMonthAttendance> list = attendance.stream()
				.filter(f -> !f.getDay().equals(DayOfWeek.SUNDAY.toString())
						&& !f.getDay().equals(DayOfWeek.SATURDAY.toString())
						&& f.getAttendanceStatus().equalsIgnoreCase("Absent"))
				.map(m -> m).collect(Collectors.toList());

		return new CommonResponse(200, "Employee No. of leaves", list.size());
	}

}
