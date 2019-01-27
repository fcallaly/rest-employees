package com.frankc.training.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when attempt to create department with duplicate name.
 * 
 * Sets HTTP status code to 400 with sensible message.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The given department name already exists")
public class DuplicateDepartmentException extends RuntimeException {
    private static final long serialVersionUID = -8718547147105539344L;
}
