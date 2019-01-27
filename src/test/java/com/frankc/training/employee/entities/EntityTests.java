package com.frankc.training.employee.entities;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit Tests for Employee, Department and Task Entities.
 */
public class EntityTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String TEST_ID = "ABCDEDFG";
    private static final String TEST_FULLNAME = "John";

    private static final String TEST_DEPARTMENT_ID = "12345ABCD";
    private static final String TEST_DEPARTMENT_NAME = "Testers";

    private static final String TEST_TASK_TITLE = "Testing Task";
    private static final String TEST_TASK_DESCRIPTION = "A task for testing.";

    @Test
    public void test_Employee_defaultConstructor_settersAndGetters() {
        Employee newEmployee = new Employee();
        newEmployee.setId(TEST_ID);
        newEmployee.setFullName(TEST_FULLNAME);
        newEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        Task testTask = new Task();
        newEmployee.setCurrentTask(testTask);

        logger.info("Testing Employee Object: " + newEmployee);

        assertEquals("Invalid Employee Id returned",
                     newEmployee.getId(), TEST_ID);
        assertEquals("Invalid fullName returned",
                     newEmployee.getFullName(), TEST_FULLNAME);
        assertEquals("Invalid departmentId returned",
                     newEmployee.getDepartmentId(), TEST_DEPARTMENT_ID);
        assertEquals("Invalid task returned",
                     newEmployee.getCurrentTask(), testTask);
    }

    @Test
    public void test_Task_defaultConstructor_settersAndGetters() {
        Task testTask = new Task();
        testTask.setTitle(TEST_TASK_TITLE);
        testTask.setDescription(TEST_TASK_DESCRIPTION);

        logger.info("Testing Task Object: " + testTask);

        assertEquals("Invalid task title returned",
                     testTask.getTitle(), TEST_TASK_TITLE);
        assertEquals("Invalid task description returned",
                     testTask.getDescription(), TEST_TASK_DESCRIPTION);
    }

    @Test
    public void test_Department_defaultConstructor_settersAndGetters() {
        Department testDepartment = new Department();
        testDepartment.setId(TEST_DEPARTMENT_ID);
        testDepartment.setName(TEST_DEPARTMENT_NAME);

        logger.info("Testing Department Object: " + testDepartment);

        assertEquals("Invalid department id returned",
                     testDepartment.getId(), TEST_DEPARTMENT_ID);
        assertEquals("Invalid department name returned",
                     testDepartment.getName(), TEST_DEPARTMENT_NAME);
    }
}
