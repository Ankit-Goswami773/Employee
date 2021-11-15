package com.employeeproject.service;

import com.employeeproject.common.CommonResponse;
import com.employeeproject.dto.RaiseReuestDTO;

public interface RaiseRequestServise {

	CommonResponse addRaiseRequest(RaiseReuestDTO raiseReuestDTO);
	CommonResponse actionOnRequestByAdmin(long raiseReqId, String requestStatus);


	CommonResponse getAllRequestFromEmp(long empId, String requestStatus);


	CommonResponse getAllRequestFromAdmin(String requestStatus);

}
