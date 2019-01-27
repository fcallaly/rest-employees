package com.frankc.training.employee.util;

import org.springframework.stereotype.Component;

@Component
public class EntityFieldValidator {

    public boolean validateFullName(final String employeeName) {
        if (employeeName == null || employeeName.isEmpty()) {
            return false;
        }
        return true;
    }
}
