package com.demo.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.employee.dto.EmployeeResDto;
import com.demo.employee.entity.Employee;
import com.demo.employee.repository.EmployeeRepository;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	@InjectMocks
	EmployeeService employeeService;

	@Mock
	EmployeeRepository employeeRepository;

	private List<Employee> employees;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Before
	public void setup() throws Exception {
		employees = new ArrayList<>();
		Employee emp1 =  new Employee(1L,"janbee","shaik","shaik@gmail.com",5,30000.0,
				"kmm","junior");
		Employee emp2 =  new Employee(2L,"abc","abcd","abc@gmail.com",10,30000.0,
				"kmm","mid junior");
		employees.add(emp1); 
		employees.add(emp2);
	}

	@Test
	public void testGetAll() {
		when(employeeRepository.findAll()).thenReturn(employees);
		List<Employee> empList = employeeService.getAll();
		assertEquals(2, empList.size());
		verify(employeeRepository,times(1)).findAll();
	}

	@Test
	public void testGetEmployeeDesignationWithCount() {
		EmployeeResDto empResDto = new EmployeeResDto(5, "junior", 1);
		List<EmployeeResDto> empResDtoList = new ArrayList<>();
		empResDtoList.add(empResDto);
		when(employeeRepository.getEmployeeDesignationWithCount(5)).thenReturn(empResDtoList);
		List<EmployeeResDto> list = employeeService.getEmployeeDesignationWithCount(5);
		assertEquals(1, list.size());
	}

	/*
	 * @Test public void testGetAllEmployeesSorted() throws
	 * EmployeeNotFoundException {
	 * when(employeeRepository.findAll()).thenReturn(employees); List<Employee>
	 * empList = employeeService.getAllEmployeesSorted("firstName", Direction.ASC,
	 * 0, 2); for(Employee emp: empList) { System.out.println(emp); }
	 * assertEquals(1, empList.size()); }
	 */

	/*
	 * @Test public void testCreate() throws EmployeeExistsException { Employee emp3
	 * = new Employee(10L,"xyz","xyz","xyz@gmail.com",11,80000.0, "kmm","senior");
	 * EmployeeReqDto empReqDto = new
	 * EmployeeReqDto("xyz","xyz","xyz@gmail.com",11,80000.0, "kmm"); Employee emp =
	 * employeeRepository.findByMailId("xyz@gmail.com");
	 * //verify(employeeRepository,times(1)).findByMailId("xyz@gmail.com");
	 * if(emp==null) { employeeService.create(empReqDto); }
	 * employeeRepository.save(emp3);
	 * //verify(employeeRepository,times(1)).save(emp3); }
	 */
}
