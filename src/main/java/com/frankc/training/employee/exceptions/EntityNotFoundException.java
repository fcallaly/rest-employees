package com.frankc.training.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when an Entity is not found.
 * 
 * Sets HTTP status code to 404 with sensible message.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5331422811112128062L;

    public EntityNotFoundException(final String message) {
        super(message);
    }
}
