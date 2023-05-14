package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ResponseBean;
import com.example.demo.payload.EmployeePayload;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/api/registrations")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	@PostMapping("/saveemp")
	public ResponseEntity<?> saveEmployee(@RequestBody EmployeePayload employeePayload,BindingResult result) {
		
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>("Invalid Payload",HttpStatus.BAD_REQUEST);
		}
			return employeeService.saveEmployee(employeePayload);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);

			
		}
		
	}
	

}
