package com.demo.employee.repository;


import java.util.List;


import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.employee.dto.EmployeeResDto;
import com.demo.employee.entity.Employee;


/**
 * 
 * @author janbee
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	public Employee findByMailId(String mailId);

	@Query("select new com.demo.employee.dto.EmployeeResDto(e.yearsOfExperience,e.designation, count(*)) from Employee e where e.yearsOfExperience=:yrsOfExp group by e.yearsOfExperience")
	public List<EmployeeResDto> getEmployeeDesignationWithCount(@PathParam("yrsOfExp") int yrsOfExp);


}
