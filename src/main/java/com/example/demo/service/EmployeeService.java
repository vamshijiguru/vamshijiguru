package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.model.ResponseBean;
import com.example.demo.payload.EmployeePayload;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

	public ResponseEntity<?> saveEmployee(EmployeePayload employeePayload) {
	ResponseBean responseBean = new ResponseBean();
	try {
		
		Employee employee = Employee.builder().dateOfJoining(employeePayload.getDate_of_joining())
				.id(employeePayload.getId()).build();
		Department department = Department.builder().deptName(employeePayload.getDept_name())
				.id(employeePayload.getDept_id()).build();
		
		employee.setDateOfJoining(employeePayload.getDate_of_joining());
		
	} catch (Exception e) {
		// TODO: handle exception
	}
		
		return null;
	}

}
