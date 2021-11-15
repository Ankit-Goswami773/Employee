package com.employeeproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeproject.applications_exceptions.EmployeeCommonException;
import com.employeeproject.common.ApplicationConstant;
import com.employeeproject.common.CommonResponse;
import com.employeeproject.dto.RaiseReuestDTO;
import com.employeeproject.entity.EmployeeDetails;
import com.employeeproject.entity.RaiseRequest;
import com.employeeproject.repository.EmployeeDetailsRepository;
import com.employeeproject.repository.RaiseRequestRepository;

@Service
public class RaiseRequestImpl implements RaiseRequestServise {
	@Autowired
	private RaiseRequestRepository raiseRequestRepo;

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepo;

	@Override
	public CommonResponse addRaiseRequest(RaiseReuestDTO raiseReuestDTO) {

		if (raiseReuestDTO.getRequestMessage() == null || raiseReuestDTO.getRequestMessage().isEmpty()) {
			throw new EmployeeCommonException("Request message can not be empty");
		}
		if (raiseReuestDTO.getRequestStatus() == null || raiseReuestDTO.getRequestStatus().isEmpty()) {
			raiseReuestDTO.setRequestStatus(ApplicationConstant.PENDING);
		}
		if (raiseReuestDTO.getEmpId() == 0) {
			throw new EmployeeCommonException("Employee id can not be 0");
		}
		EmployeeDetails employeeDetails = employeeDetailsRepo.findById(raiseReuestDTO.getEmpId()).orElse(null);

		if (employeeDetails == null) {
			throw new EmployeeCommonException("Employee not found");
		}
		RaiseRequest raiseRequest = RaiseRequest.builder().requestMessage(raiseReuestDTO.getRequestMessage())
				.requestStatus(raiseReuestDTO.getRequestStatus()).employeeDetails(employeeDetails).build();

		RaiseRequest savedRaiseRequest = raiseRequestRepo.save(raiseRequest);

		return new CommonResponse(200, "saved sucessfully", savedRaiseRequest);

	}

	@Override
	public CommonResponse actionOnRequestByAdmin(long raiseReqId, String requestStatus) {

		if (raiseReqId == 0) {
			throw new EmployeeCommonException("Raiser reqest id can not be 0");
		}

		if (requestStatus == null || !requestStatus.equalsIgnoreCase(ApplicationConstant.APPROVE)
				&& !requestStatus.equalsIgnoreCase(ApplicationConstant.REJECT)) {
			throw new EmployeeCommonException("Request status is not APPROVE or REJECT");
		}
		RaiseRequest raiseRequest = raiseRequestRepo.findById(raiseReqId).orElse(null);

		if (raiseRequest == null) {
			throw new EmployeeCommonException("Raise Request not found");
		}
		raiseRequest.setRequestStatus(requestStatus);

		RaiseRequest savedRaiseRequest = raiseRequestRepo.save(raiseRequest);

		return new CommonResponse(200, "saved sucessfully", savedRaiseRequest);
	}

	@Override
	public CommonResponse getAllRequestFromEmp(long empId, String requestStatus) {
		if (empId == 0) {
			throw new EmployeeCommonException("Employee id can not be 0");
		}
		if (requestStatus == null) {
			requestStatus = "";
		}
		EmployeeDetails employeeDetails = employeeDetailsRepo.findById(empId).orElse(null);

		if (employeeDetails == null) {
			throw new EmployeeCommonException("Employee not found");
		}

		List<RaiseRequest> raiseRequestList = raiseRequestRepo.getAllRequestFromEmp(requestStatus, empId);

		return new CommonResponse(200, "fetch sucessfully", raiseRequestList);
	}

	@Override
	public CommonResponse getAllRequestFromAdmin(String requestStatus) {
		if (requestStatus == null) {
			requestStatus = "";
		}
		List<RaiseRequest> findByStatus = raiseRequestRepo.findByStatus(requestStatus);
		return new CommonResponse(200, "fetch sucessfully", findByStatus);
	}
}
