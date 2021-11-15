package com.employeeproject.applications_exceptions;

public class EmployeeCommonException extends RuntimeException{

	private static final long serialVersionUID = -5226208827344858886L;

	public EmployeeCommonException(String message){
		super(message);
	}
}
