package com.demo.employee.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.employee.dto.EmployeeReqDto;
import com.demo.employee.dto.EmployeeResDto;
import com.demo.employee.entity.Employee;
import com.demo.employee.exception.EmployeeExistsException;
import com.demo.employee.exception.EmployeeNotFoundException;
import com.demo.employee.service.EmployeeService;
import com.demo.employee.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/employees")
@Api(description = "Operations pertaining to employee service")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;


	@ApiOperation("create an employee")
	@PostMapping("/")
	public ResponseEntity<Employee> create(@RequestBody EmployeeReqDto employeeReqDto) throws EmployeeExistsException {
		Employee employee = null;
		try {
			employee = employeeService.create(employeeReqDto);
		} catch(EmployeeExistsException e) {
			throw new EmployeeExistsException();
		}
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}

	@ApiOperation("Get all employees")
	@GetMapping("/")
	public ResponseEntity<List<Employee>> getAll() {
		List<Employee> empList = employeeService.getAll();
		return new ResponseEntity<>(empList,HttpStatus.OK);
	}

	@ApiOperation("Get an employee")
	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
		Employee emp = employeeService.getById(employeeId);
		if(emp==null) {
			throw new EmployeeNotFoundException();
		}
		return new ResponseEntity<>(emp,HttpStatus.OK);
	}

	@ApiOperation("Update an employee")
	@PutMapping("/{employeeId}")
	public ResponseEntity<String> update(@PathVariable Long employeeId, @RequestBody Employee employee) throws EmployeeNotFoundException {
		String msg;
		try {
			msg = employeeService.update(employeeId, employee);
		}catch(EmployeeNotFoundException e) {
			throw new EmployeeNotFoundException();
		}
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	@ApiOperation("Delete an employee")
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<String> delete(@PathVariable Long employeeId) {
		employeeService.delete(employeeId);
		return new ResponseEntity<>(Constants.EMPLOYEE_DELETED,HttpStatus.OK);
	}

	@ApiOperation("Group by employee experience")
	@GetMapping("/groupByExperience")
	public ResponseEntity<List<EmployeeResDto>> getEmployeeDesignationWithCount(@RequestParam int yrsOfExp) {
		List<EmployeeResDto> empResDto = employeeService.getEmployeeDesignationWithCount(yrsOfExp);
		return new ResponseEntity<>(empResDto,HttpStatus.OK);
	}

	@ApiOperation("Get employees sorted list")
	@GetMapping("/sort")
	public ResponseEntity<List<Employee>> getAllEmployeesSorted(@RequestParam String sortFeild, @RequestParam Direction sortDirection, @RequestParam int pageNum,@RequestParam int pageSize) throws EmployeeNotFoundException {
		List<Employee> empList = employeeService.getAllEmployeesSorted(sortFeild, sortDirection, pageNum,pageSize);
		return new ResponseEntity<>(empList,HttpStatus.OK);
	}
}
