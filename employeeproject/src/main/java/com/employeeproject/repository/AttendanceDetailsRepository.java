package com.employeeproject.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.employeeproject.entity.AttendanceDetails;

@Repository
public interface AttendanceDetailsRepository extends JpaRepository<AttendanceDetails, Long> {

	@Modifying
	@Transactional
	@Query(value = "  Insert into attendance_details (attendance_id, date, day, emp_id) values(0, :date, :dayOfWeek,:empId)", nativeQuery = true)
	public int saveAttendance(long empId, String dayOfWeek, LocalDate date);

	@Modifying
	@Transactional
	@Query(value = " INSERT INTO attendance_details ( `attendance`, `date`,  `emp_id`) "
			+ "SELECT false, :todayDate, d.emp_id" + " FROM employee_details as d", nativeQuery = true)
	public void createDailyEntryOfEmp(String todayDate);

	@Query(value = "select * from attendance_details  where emp_id=:empId && date=:date", nativeQuery = true)
	public AttendanceDetails getEmpIdAndDate(long empId, LocalDate date);

}
