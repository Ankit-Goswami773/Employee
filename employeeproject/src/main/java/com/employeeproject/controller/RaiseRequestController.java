package com.employeeproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeeproject.common.CommonResponse;
import com.employeeproject.dto.RaiseReuestDTO;
import com.employeeproject.service.RaiseRequestServise;

@RestController
@CrossOrigin(origins = "http://localhost")
public class RaiseRequestController {
	@Autowired
	RaiseRequestServise raiseRequestServise;

	/**
	 * @author Ashish
	 * @apiNote to save new raise request in database
	 * @date 1-NOV-2021
	 */
	@PostMapping("/addRaiseRequest")
	public ResponseEntity<CommonResponse> addRaiseRequest(@RequestBody RaiseReuestDTO raiseReuestDTO) {
		return new ResponseEntity<>(raiseRequestServise.addRaiseRequest(raiseReuestDTO), HttpStatus.OK);
	}

	/**
	 * @author Ashish
	 * @apiNote admit can approve or reject raise request
	 * @date 1-NOV-2021
	 */
	@PostMapping("/actionOnRequestByAdmin")
	public ResponseEntity<CommonResponse> actionOnRequestByAdmin(@RequestParam("raiseReqId") long raiseReqId,
			@RequestParam("requestStatus") String requestStatus) {
		return new ResponseEntity<>(raiseRequestServise.actionOnRequestByAdmin(raiseReqId, requestStatus),
				HttpStatus.OK);
	}

	/**
	 * @author Ashish
	 * @apiNote fetch all raise request list according to empId and Request
	 * @date 1-NOV-2021
	 */
	@GetMapping("/getAllRequestFromEmp")
	public ResponseEntity<CommonResponse> getAllRequestFromEmp(@RequestParam("empId") long empId,
			@RequestParam("requestStatus") String requestStatus) {

		return new ResponseEntity<>(raiseRequestServise.getAllRequestFromEmp(empId, requestStatus), HttpStatus.OK);
	}

	@GetMapping("/getAllRequestFromAdmin")
	public ResponseEntity<CommonResponse> getAllRequestFromAdmin(@RequestParam("requestStatus") String requestStatus) {

		return new ResponseEntity<>(raiseRequestServise.getAllRequestFromAdmin(requestStatus), HttpStatus.OK);

	}

}
