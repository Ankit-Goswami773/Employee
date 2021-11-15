package com.employeeproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RaiseReuestDTO {
	private long raiseReqId;
	private String requestMessage;
	private String requestStatus;
	private long empId;
}
