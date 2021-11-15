package com.employeeproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employeeproject.entity.RaiseRequest;

@Repository
public interface RaiseRequestRepository extends JpaRepository<RaiseRequest, Long> {


	@Query(value = "select * from raise_request as  r where r.request_status LIKE :requestStatus%  and r.emp_id=:empId", nativeQuery = true)
	List<RaiseRequest> getAllRequestFromEmp(@Param("requestStatus") String requestStatus,@Param("empId")  long empId);
	
	
	@Query(value = "select * from raise_request where request_status LIKE :requestStatus%", nativeQuery = true) 
	List<RaiseRequest> findByStatus(@Param("requestStatus") String requestStatus);


}
