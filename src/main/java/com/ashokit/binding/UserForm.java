package com.ashokit.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserForm {
	
	private String fname;
	
	private String lname;
	
	private String email;
	
	private Long phno;
	
	private LocalDate dob;
	
	private String gender;
	
	private Integer cityId;
	
	private Integer stateId;
	
	private Integer countryId;


}
