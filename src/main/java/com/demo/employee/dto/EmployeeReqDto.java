package com.demo.employee.dto;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeReqDto {

	private String firstName;
	private String lastName;
	private String mailId;
	private int yearsOfExperience;
	private double salary;
	private String address;
}
