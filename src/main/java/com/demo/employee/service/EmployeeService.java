package com.demo.employee.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public Employee create(EmployeeReqDto empReqDto) throws EmployeeExistsException {
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
		return newEmp;
	}

	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	public Employee getById(Long employeeId) throws EmployeeNotFoundException {
		Optional<Employee> emp = employeeRepository.findById(employeeId);
		if(emp.isPresent()) {
			return emp.get();
		}
		return null;
	}

	public String update(Long employeeId,Employee employee) throws EmployeeNotFoundException {
		Employee empObj = getById(employeeId);
		if(Objects.isNull(empObj)) {
			throw new EmployeeNotFoundException();
		}
		employeeRepository.save(employee);
		return Constants.EMPLOYEE_UPDATED;
	}

	public void delete(Long employeeId) {
		employeeRepository.deleteById(employeeId);
	}

	public List<EmployeeResDto> getEmployeeDesignationWithCount(int yrsOfExp) {
		List<EmployeeResDto> empResDtoList =  employeeRepository.getEmployeeDesignationWithCount(yrsOfExp);
		return empResDtoList;
	}

	public List<Employee> getAllEmployeesSorted(String sortFeild,Direction sortDirection,int pageNum,int pageSize) throws EmployeeNotFoundException {
		Pageable pageable = PageRequest.of(pageNum,pageSize,sortDirection, sortFeild);
		Page<Employee> page = employeeRepository.findAll(pageable);
		if(!page.isEmpty() && !page.hasContent()) {
			throw new EmployeeNotFoundException("Employee not found");
		}
		return page.getContent();
	}
}
