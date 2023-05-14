package com.example.demo.payload;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class EmployeePayload {

	private Long id;
	private LocalDate date_of_joining;
	private Long user_id;
	private Long dept_id ;
	private String dept_name ;
}
