package com.demo.employee.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.demo.employee.dto.EmployeeReqDto;
import com.demo.employee.dto.EmployeeResDto;
import com.demo.employee.entity.Employee;
import com.demo.employee.exception.EmployeeExistsException;
import com.demo.employee.exception.EmployeeNotFoundException;
import com.demo.employee.repository.EmployeeRepository;
import com.demo.employee.util.Constants;

/**
 * 
 * @author janbee
 *
 */
@Service
public class EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	EmployeeRepository employeeRepository;

	/**
	 * 
	 * @param empReqDto
	 * @return Employee object
	 * @throws EmployeeExistsException
	 */
	public Employee create(EmployeeReqDto empReqDto) throws EmployeeExistsException {
		LOGGER.info("EmployeeService :: create() :: start");
		Employee employee = employeeRepository.findByMailId(empReqDto.getMailId());
		if(employee!=null) {
			throw new EmployeeExistsException();
		}
		Employee emp = new Employee();
		emp.setFirstName(empReqDto.getFirstName());
		emp.setLastName(empReqDto.getLastName());
		emp.setAddress(empReqDto.getAddress());
		emp.setMailId(empReqDto.getMailId());
		emp.setSalary(empReqDto.getSalary());
		int exp = empReqDto.getYearsOfExperience();
		emp.setYearsOfExperience(exp);
		if(exp<=5) {
			emp.setDesignation("junior");
		} else if(exp>5 && exp<=10) {
			emp.setDesignation("mid junior");
		} else {
			emp.setDesignation("senior");
		}
		Employee newEmp = employeeRepository.save(emp);
		LOGGER.info("EmployeeService :: create() :: end");
		return newEmp;
	}

	/**
	 * 
	 * @return list of employees
	 */
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return Employee object
	 * @throws EmployeeNotFoundException
	 */
	public Employee getById(Long employeeId) throws EmployeeNotFoundException {
		LOGGER.info("EmployeeService :: getById() :: start");
		Optional<Employee> emp = employeeRepository.findById(employeeId);
		if(emp.isPresent()) {
			return emp.get();
		}
		LOGGER.info("EmployeeService :: getById() :: end");
		return null;
	}

	/**
	 * 
	 * @param employeeId
	 * @param employee
	 * @return String
	 * @throws EmployeeNotFoundException
	 */
	public String update(Long employeeId,Employee employee) throws EmployeeNotFoundException {
		LOGGER.info("EmployeeService :: update() :: start");
		Employee empObj = getById(employeeId);
		if(Objects.isNull(empObj)) {
			throw new EmployeeNotFoundException();
		}
		employeeRepository.save(employee);
		LOGGER.info("EmployeeService :: update() :: end");
		return Constants.EMPLOYEE_UPDATED;
	}

	/**
	 * 
	 * @param employeeId
	 */
	public void delete(Long employeeId) {
		employeeRepository.deleteById(employeeId);
	}

	/**
	 * 
	 * @param yrsOfExp
	 * @return list of employees grouping by experience
	 */
	public List<EmployeeResDto> getEmployeeDesignationWithCount(int yrsOfExp) {
		LOGGER.info("EmployeeService :: getEmployeeDesignationWithCount() :: start");
		List<EmployeeResDto> empResDtoList =  employeeRepository.getEmployeeDesignationWithCount(yrsOfExp);
		LOGGER.info("EmployeeService :: getEmployeeDesignationWithCount() :: end");
		return empResDtoList;
	}

	/**
	 * 
	 * @param sortFeild
	 * @param sortDirection
	 * @param pageNum
	 * @param pageSize
	 * @return list of employees 
	 * @throws EmployeeNotFoundException
	 */
	public List<Employee> getAllEmployeesSorted(String sortFeild,Direction sortDirection,int pageNum,int pageSize) throws EmployeeNotFoundException {
		LOGGER.info("EmployeeService :: getAllEmployeesSorted() :: start");
		Pageable pageable = PageRequest.of(pageNum,pageSize,sortDirection, sortFeild);
		Page<Employee> page = employeeRepository.findAll(pageable);
		if(!page.isEmpty() && !page.hasContent()) {
			throw new EmployeeNotFoundException("Employee not found");
		}
		LOGGER.info("EmployeeService :: getAllEmployeesSorted() :: end");
		return page.getContent();
	}
}
