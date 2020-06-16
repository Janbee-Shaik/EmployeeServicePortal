package com.demo.employee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "EMPLOYEE")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long empId;
	private String firstName;
	private String lastName;
	private String mailId;
	private int yearsOfExperience;
	private double salary;
	private String address;
	private String designation;

	public Employee() {
		
	}

	public Employee(Long empId, String firstName,String lastName,String mailId,int yearsOfExperience,double salary
			,String address,String designation) {
		this.address=address;
		this.designation=designation;
		this.empId=empId;
		this.firstName=firstName;
		this.lastName=lastName;
		this.mailId=mailId;
		this.salary=salary;
		this.yearsOfExperience=yearsOfExperience;

	}

}
