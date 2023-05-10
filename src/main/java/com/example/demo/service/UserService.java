package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.ResponseBean;
import com.example.demo.model.User;
import com.example.demo.model.UserTypes;
import com.example.demo.payload.Userpayload;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTypesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;



	@Autowired
	private UserTypesRepository userTypesRepository;

	public ResponseEntity<?> saveUser(Userpayload userpayload) {
		ResponseBean responseBean = new ResponseBean();
		try {

			User user = User.builder().id(userpayload.getId()).name(userpayload.getName()).age(userpayload.getAge())
					.username(userpayload.getUsername()).dateOfBirth(userpayload.getDateOfBirth())
					.password(userpayload.getPassword()).userTypeId(userpayload.getUserTypeId()).build();
			Optional<UserTypes> existingUserType = userTypesRepository.findById(userpayload.getUserTypeId());
			if (userpayload.getId() != null && userpayload.getUserTypeId() != null && userpayload.getAge() > 18 
					&& userpayload.getUserTypeId() > 0 
					) {
				Optional<User> existingUserRecord = userRepository.findById(userpayload.getId());
				if ( existingUserType.isPresent() && existingUserRecord.isPresent()) {
					user.setUpdatedOn(LocalDateTime.now());
					user.setIsActive(true);
					user = userRepository.save(user);
					Long savedId = user.getId();
					System.out.println("given id  " + savedId);
					responseBean.setStatus(HttpStatus.CREATED);
					responseBean.setReturnCode(1);
					responseBean.setMessage("Successfully updated the record");

				}
			}  else {
				if (existingUserType.isPresent() && userpayload.getId() == null && userpayload.getAge() > 18) {
					user.setCreatedOn(LocalDateTime.now());
					user.setIsActive(true);
					user = userRepository.save(user);
					responseBean.setStatus(HttpStatus.CREATED);
					responseBean.setReturnCode(1);
					responseBean.setMessage("Successfully inserted the record");
				}
				else
				{
			responseBean.setStatus(HttpStatus.BAD_REQUEST);
			responseBean.setMessage("userType not present or user age is not grater then  18 ");
				}
			}
		} catch (Exception e) {
			log.error("Exception occured : {}", e);
			responseBean.setReturnCode(0);
			responseBean.setStatus(HttpStatus.EXPECTATION_FAILED);
			responseBean.setMessage("Failed to add record");
		}
		return new ResponseEntity<>(responseBean, responseBean.getStatus());
	}

}
