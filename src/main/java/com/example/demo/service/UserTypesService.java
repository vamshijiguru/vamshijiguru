package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.ResponseBean;
import com.example.demo.model.UserTypes;
import com.example.demo.payload.userTypePaylode;
import com.example.demo.repository.UserTypesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserTypesService {

	@Autowired
	private UserTypesRepository userTypesRepository;

	public ResponseEntity<?> saveUserTypes(userTypePaylode userTypePaylode) {
		ResponseBean responseBean = new ResponseBean();
		try {
			UserTypes userTypes = UserTypes.builder().userTypeName(userTypePaylode.getUserTypeName())
					.id(userTypePaylode.getId()).build();
			if (userTypePaylode.getId() != null && userTypePaylode.getId() != 1 && userTypePaylode.getId() > 0) {
				Optional<UserTypes> existingUserRoleRecord = userTypesRepository.findById(userTypePaylode.getId());
				// userTypeId 1 is Admin User
				if (existingUserRoleRecord.isPresent()) {
					userTypes = existingUserRoleRecord.get();
					userTypes.setUpdatedOn(LocalDateTime.now());
					userTypes.setUserTypeName(userTypePaylode.getUserTypeName());
					userTypes = userTypesRepository.save(userTypes);
					responseBean.setStatus(HttpStatus.OK);
					responseBean.setMessage("Successfully updated the record");
					responseBean.setData(null);
				} else {
					userTypes.setCreatedOn(LocalDateTime.now());
					userTypes.setIsActive(true);
					userTypes = userTypesRepository.save(userTypes);
					responseBean.setStatus(HttpStatus.CREATED);
					responseBean.setReturnCode(1);
					responseBean.setMessage("Record does not exist new record inserted");

				}
			} else {
				if ( userTypePaylode.getId() == null ) {
					userTypes.setCreatedOn(LocalDateTime.now());
					userTypes.setIsActive(true);
					userTypes = userTypesRepository.save(userTypes);
					responseBean.setStatus(HttpStatus.CREATED);
					responseBean.setReturnCode(1);
					responseBean.setMessage("Successfully added record");
				} else {
					responseBean.setStatus(HttpStatus.UNAUTHORIZED);
					responseBean.setReturnCode(1);
					responseBean.setMessage("access denied");
				}

			}

		}

		catch (Exception e) {
			log.error("Exception occured : {}", e);
			responseBean.setStatus(HttpStatus.EXPECTATION_FAILED);
			responseBean.setMessage("Failed to add record");

		}
		return new ResponseEntity<>(responseBean, responseBean.getStatus());

	}
}
