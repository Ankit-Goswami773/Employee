package com.employeeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employeeproject.entity.EmployeeDetails;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

	@Query("select e from EmployeeDetails  e where e.emailId =:emailId")
	EmployeeDetails getEmployeeByEmailId(String emailId);



	
	
}
