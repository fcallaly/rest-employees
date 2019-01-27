package com.frankc.training.employee.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.frankc.training.employee.entities.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String>{

}
