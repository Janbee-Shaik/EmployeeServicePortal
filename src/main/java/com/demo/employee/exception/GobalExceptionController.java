package com.demo.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GobalExceptionController {

	@ExceptionHandler(value = EmployeeNotFoundException.class)
	public ResponseEntity<Object> handleEmployeeNotFoundException() {
		return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = EmployeeExistsException.class)
	public ResponseEntity<Object> handleEmployeeExistsException() {
		return new ResponseEntity<>("Employee already exists in the system!", HttpStatus.OK);
	}

}
