package com.employeeproject.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RaiseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long raiseReqId;
	private String requestMessage;
	private String requestStatus;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "emp_id")
	private EmployeeDetails employeeDetails;
}
