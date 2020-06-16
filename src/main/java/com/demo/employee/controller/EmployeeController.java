package com.demo.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * 
 * @author janbee
 * @version 1.0
 * @since 15-06-2020
 */
@RestController
@RequestMapping("/employees")
@Api(description = "Operations pertaining to employee service")
public class EmployeeController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;

	/**
	 * 
	 * @param employeeReqDto
	 * @return Employee object
	 * @throws EmployeeExistsException
	 */
	@ApiOperation("create an employee")
	@PostMapping("/")
	public ResponseEntity<Employee> create(@RequestBody EmployeeReqDto employeeReqDto) throws EmployeeExistsException {
		LOGGER.debug("EmployeeController :: create() :: start" +employeeReqDto);
		Employee employee = null;
		try {
			employee = employeeService.create(employeeReqDto);
		} catch(EmployeeExistsException e) {
			LOGGER.error("EmployeeController :: create() :: EmployeeExistsException"+e);
			throw new EmployeeExistsException();
		}
		LOGGER.debug("EmployeeController :: create() :: end");
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * @return list of employees
	 */
	@ApiOperation("Get all employees")
	@GetMapping("/")
	public ResponseEntity<List<Employee>> getAll() {
		LOGGER.debug("EmployeeController :: getAll() :: start");
		List<Employee> empList = employeeService.getAll();
		LOGGER.debug("EmployeeController :: getAll() :: end");
		return new ResponseEntity<>(empList,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return Employee object
	 * @throws EmployeeNotFoundException
	 */
	@ApiOperation("Get an employee")
	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
		LOGGER.debug("EmployeeController :: getById() :: start");
		Employee emp = employeeService.getById(employeeId);
		if(emp==null) {
			throw new EmployeeNotFoundException();
		}
		LOGGER.debug("EmployeeController :: getById() :: end");
		return new ResponseEntity<>(emp,HttpStatus.OK);
	}

	/**
	 * 
	 * @param employeeId
	 * @param employee
	 * @return String
	 * @throws EmployeeNotFoundException
	 */
	@ApiOperation("Update an employee")
	@PutMapping("/{employeeId}")
	public ResponseEntity<String> update(@PathVariable Long employeeId, @RequestBody Employee employee) throws EmployeeNotFoundException {
		LOGGER.debug("EmployeeController :: update() :: start");
		String msg;
		try {
			msg = employeeService.update(employeeId, employee);
		}catch(EmployeeNotFoundException e) {
			LOGGER.error("EmployeeController :: update() :: EmployeeNotFoundException");
			throw new EmployeeNotFoundException();
		}
		LOGGER.debug("EmployeeController :: update() :: end");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	/**
	 * deletes an employee object
	 * 
	 * @param employeeId
	 * @return String
	 */
	@ApiOperation("Delete an employee")
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<String> delete(@PathVariable Long employeeId) {
		LOGGER.debug("EmployeeController :: delete() :: start");
		employeeService.delete(employeeId);
		LOGGER.debug("EmployeeController :: delete() :: end");
		return new ResponseEntity<>(Constants.EMPLOYEE_DELETED,HttpStatus.OK);
	}

	/**
	 * 
	 * @param yrsOfExp
	 * @return list of employees whose 
	 */
	@ApiOperation("Group by employee experience")
	@GetMapping("/groupByExperience")
	public ResponseEntity<List<EmployeeResDto>> getEmployeeDesignationWithCount(@RequestParam int yrsOfExp) {
		LOGGER.debug("EmployeeController :: getEmployeeDesignationWithCount() :: start" +yrsOfExp);
		List<EmployeeResDto> empResDto = employeeService.getEmployeeDesignationWithCount(yrsOfExp);
		LOGGER.debug("EmployeeController :: getEmployeeDesignationWithCount() :: end");
		return new ResponseEntity<>(empResDto,HttpStatus.OK);
	}

	/**
	 * 
	 * @param sortFeild
	 * @param sortDirection
	 * @param pageNum
	 * @param pageSize
	 * @return list of sorted employees
	 * @throws EmployeeNotFoundException
	 */
	@ApiOperation("Get employees sorted list")
	@GetMapping("/sort")
	public ResponseEntity<List<Employee>> getAllEmployeesSorted(@RequestParam String sortFeild, @RequestParam Direction sortDirection, @RequestParam int pageNum,@RequestParam int pageSize) throws EmployeeNotFoundException {
		LOGGER.debug("EmployeeController :: getAllEmployeesSorted() :: start");
		List<Employee> empList = employeeService.getAllEmployeesSorted(sortFeild, sortDirection, pageNum,pageSize);
		LOGGER.debug("EmployeeController :: getAllEmployeesSorted() :: end");
		return new ResponseEntity<>(empList,HttpStatus.OK);
	}
}
