package com.frankc.training.employee.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.frankc.training.employee.entities.Department;
import com.frankc.training.employee.exceptions.DuplicateDepartmentException;
import com.frankc.training.employee.exceptions.EntityNotFoundException;
import com.frankc.training.employee.repositories.DepartmentRepository;

@Service
public class DepartmentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DepartmentRepository departmentRepo;

    public List<Department> findAllDepartments() {
        return departmentRepo.findAll();
    }

    public Department findDepartment(String departmentId) {
        Optional<Department> department =
                            departmentRepo.findById(departmentId);

        if(!department.isPresent()) {
            throw new EntityNotFoundException("Invalid department id: "
                                              + departmentId);
        }

        logger.info("Found department: " + department.get());
        return department.get();
    }

    public Department saveDepartment(Department department) {
        logger.info("Saving department: " + department);

        try {
            return departmentRepo.save(department);
        } catch (DuplicateKeyException ex) {
            logger.error("Unable to save department: " + ex);
            throw new DuplicateDepartmentException();
        }
    }

    public void deleteDepartment(String id) {
        try {
            departmentRepo.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Invalid department id: " + id);
        }
    }
}
