package com.frankc.training.employee.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.frankc.training.employee.entities.Department;

public interface DepartmentRepository extends MongoRepository<Department, String>{

    Department findByName(String name);

}
