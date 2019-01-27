package com.frankc.training.employee.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frankc.training.employee.entities.Department;
import com.frankc.training.employee.services.DepartmentService;

/**
 * Implements interface from RESTful HTTP/JSON to Department Repository.
 *
 * @author Frank Callaly
 */
@RestController
@RequestMapping(DepartmentController.BASE_PATH)
public class DepartmentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String BASE_PATH = "/departments/";

    @Autowired
    private DepartmentService departmentService;

    /**
     * Get a collection of all Departments in the repository.
     *
     * @return HttpEntity containing the full List of Departments
     */
    @GetMapping
    public HttpEntity<List<Department>> findAllDepartments() {
        return new ResponseEntity<List<Department>>(
                                    departmentService.findAllDepartments(),
                                    HttpStatus.OK);
    }

    /**
     * Get a single particular Department from the repository.
     *
     * @param id of Department to return, taken from path
     * @return HttpEntity containing the Department corresponding to the given id
     */
    @GetMapping("{id}")
    public HttpEntity<Department> getDepartment(@PathVariable("id") final String id) {
        logger.info("getDepartment: " + id);

        return new ResponseEntity<Department>(departmentService.findDepartment(id),
                                              HttpStatus.OK);
    }

    /**
     * Create a new Department and add to the repository.
     *
     * @param newDepartment Department corresponding to data sent in request body
     * @return HttpEntity containing Department that has been added
     */
    @PostMapping
    public HttpEntity<Department> createDepartment(@RequestBody final Department newDepartment) {
        logger.info("createDepartment: " + newDepartment);
        return new ResponseEntity<Department>(departmentService.saveDepartment(newDepartment),
                                              HttpStatus.CREATED);
    }

    /**
     * Delete a Department from the repository.
     *
     * @param id of the Department to delete
     * @return HttpEntity containing NO_CONTENT
     */
    @DeleteMapping("{id}")
    public HttpEntity<Department> deleteDepartment(@PathVariable("id") final String id) {
        logger.info("deleteDepartment: " + id);

        departmentService.deleteDepartment(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
