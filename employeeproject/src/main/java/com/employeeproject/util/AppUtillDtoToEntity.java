package com.employeeproject.util;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

import com.employeeproject.dto.EmployeeDto;
import com.employeeproject.entity.EmployeeDetails;

@Configuration
public class AppUtillDtoToEntity {

	public EmployeeDetails dtoToEntity(EmployeeDto employeeDto) {
		EmployeeDetails details = new EmployeeDetails();
		BeanUtils.copyProperties(employeeDto, details);
		return details;
	}

}
