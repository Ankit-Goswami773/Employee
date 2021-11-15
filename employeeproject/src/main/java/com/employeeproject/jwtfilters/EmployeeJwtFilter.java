
package com.employeeproject.jwtfilters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employeeproject.utility.EmployeeUtility;

@Component
public class EmployeeJwtFilter extends OncePerRequestFilter {

	@Autowired
	EmployeeUtility employeeUtility;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		boolean isValidate = false;
		String jwtToken = null;

		final String requestTokenHeader = request.getHeader("Authorization");

		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
		  String headerName = headerNames.nextElement();
		  System.out.println("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
		}
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			isValidate = employeeUtility.validateToken(jwtToken);
			if (!isValidate) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "user token is expired");
			}
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "user unauthorized");
		}
		chain.doFilter(request, response);
	}

}
