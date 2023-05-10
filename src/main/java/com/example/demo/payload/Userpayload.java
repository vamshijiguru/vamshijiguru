package com.example.demo.payload;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Userpayload {

	private Long id;
	private String name;
	private Integer age;
	private String username;
	private LocalDate dateOfBirth;
	private String password;
	private Long userTypeId;
	private List<UserTypesDetails>userTypes;
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserTypesDetails{
		private Long id;
		private String userTypeName;
	}
}
