package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Department;
import com.example.demo.model.ResponseBean;
import com.example.demo.payload.Departementpayload;
import com.example.demo.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public ResponseEntity<?> saveUser(Departementpayload departementpayload) {
		ResponseBean responseBean = new ResponseBean();
		try {
			Department departement = Department.builder().deptName(departementpayload.getDeptName())
					.id(departementpayload.getId()).build();
			long count = departmentRepository.count();
			// long duplicate = departmentRepository.countById(departementpayload.getId());
			if (departementpayload.getId() != null && departementpayload.getId() > 0 && count < 5) {
				Optional<Department> existingRecord = departmentRepository.findById(departementpayload.getId());
				if (!existingRecord.isPresent()) {
					departement.setCreatedOn(LocalDateTime.now());
					departement.setActive(true);
					departement = departmentRepository.save(departement);
					responseBean.setStatus(HttpStatus.CREATED);
					responseBean.setReturnCode(1);
					responseBean.setMessage("new record inserted");
				} else {
					responseBean.setStatus(HttpStatus.BAD_REQUEST);
					responseBean.setReturnCode(1);
					responseBean.setMessage("Department is already exist");
				}
			} else {
				if (count > 5) {
					responseBean.setStatus(HttpStatus.BAD_REQUEST);
					responseBean.setReturnCode(1);
					responseBean.setMessage("Department cap is full");
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
