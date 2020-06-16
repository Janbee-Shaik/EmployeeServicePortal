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
	
	public EmployeeReqDto(String firstName,String lastName,String mailId,int yearsOfExperience,double salary
			,String address) {
		this.address = address;
		this.firstName =firstName;
		this.lastName = lastName;
		this.mailId = mailId;
		this.salary = salary;
		this.yearsOfExperience = yearsOfExperience;
	}
}
