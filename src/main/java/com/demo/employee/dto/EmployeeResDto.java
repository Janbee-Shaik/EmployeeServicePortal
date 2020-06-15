package com.demo.employee.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeResDto {

	public EmployeeResDto(int yrsOfExp,String designation, long count) {
		this.yrsOfExp = yrsOfExp;
		this.designation = designation;
		this.count= count;
	}


	private int yrsOfExp;
	private long count;
	private String designation;
}
