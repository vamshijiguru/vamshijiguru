package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.model.ResponseBean;
import com.example.demo.model.User;
import com.example.demo.payload.EmployeePayload;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public DepartmentRepository departmentRepository;

	@Autowired
	public EmployeeRepository employeeRepository;

	public ResponseEntity<?> saveEmployee(EmployeePayload employeePayload) {
	ResponseBean responseBean = new ResponseBean();
	try {
		Optional<User> existUser = userRepository.findById(employeePayload.getUser_id());
		Optional<Department> existedDepartment = departmentRepository.findById(employeePayload.getDept_id());
		if(existUser.isPresent() && existedDepartment.isPresent()) {
		Employee employee = Employee.builder().dateOfJoining(employeePayload.getDate_of_joining())
				.id(employeePayload.getId()).deptId(employeePayload.getDept_id()).userId(employeePayload.getUser_id()).build();
		
		
		employee.setDateOfJoining(employeePayload.getDate_of_joining());
		employee.setDeptId(employeePayload.getDept_id());
		employee.setUserId(employeePayload.getUser_id());
		employee.setCreatedOn(LocalDateTime.now());
		responseBean.setMessage("employe registed sucessfully");
		responseBean.setReturnCode(1);
		responseBean.setStatus(HttpStatus.CREATED);
		employee = employeeRepository.save(employee);  
		}else {
			responseBean.setMessage("given user_id and dept_id not exist ");
			responseBean.setStatus(HttpStatus.BAD_REQUEST);
		}
		
	} catch (Exception e) {
		
		responseBean.setStatus(HttpStatus.NOT_ACCEPTABLE);
	}
		
		return new ResponseEntity<>(responseBean, responseBean.getStatus());
	}

}
