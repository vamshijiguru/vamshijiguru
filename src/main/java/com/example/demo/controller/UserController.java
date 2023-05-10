package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.Userpayload;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/registrations")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/saveuser")
	public ResponseEntity<?> saveUser(@RequestBody Userpayload userpayload,BindingResult result){
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>("Invalid Payload",HttpStatus.BAD_REQUEST);
			}
			return userService.saveUser(userpayload);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
}

