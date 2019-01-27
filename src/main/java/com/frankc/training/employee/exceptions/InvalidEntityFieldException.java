package com.frankc.training.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when an Entity is not found.
 * 
 * Sets HTTP status code to 404 with sensible message.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidEntityFieldException extends RuntimeException {
    private static final long serialVersionUID = -2388921859855974274L;

    public InvalidEntityFieldException(final String message) {
        super(message);
    }
}
