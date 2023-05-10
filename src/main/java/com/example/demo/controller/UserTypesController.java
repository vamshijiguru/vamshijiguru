package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.userTypePaylode;
import com.example.demo.service.UserTypesService;


@RestController
@RequestMapping("/api/registrations")
public class UserTypesController {
	
	@Autowired
	private UserTypesService userTypesService;
	
	@PostMapping("/addUserType")
	public ResponseEntity<?> saveuserTypes(@RequestBody userTypePaylode userTypePaylode,BindingResult result){
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>("Invalid Payload",HttpStatus.BAD_REQUEST);
			}
			return userTypesService.saveUserTypes(userTypePaylode);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
		
		
	}

}
