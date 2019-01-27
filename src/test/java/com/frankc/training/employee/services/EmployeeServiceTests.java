package com.frankc.training.employee.services;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.frankc.training.employee.entities.Employee;
import com.frankc.training.employee.entities.Task;
import com.frankc.training.employee.exceptions.EntityNotFoundException;
import com.frankc.training.employee.exceptions.InvalidEntityFieldException;
import com.frankc.training.employee.repositories.DepartmentRepository;
import com.frankc.training.employee.repositories.EmployeeRepository;
import com.frankc.training.employee.util.EntityFieldValidator;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {EmployeeService.class})
public class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository mockEmployeeRepo;

    @MockBean
    private DepartmentRepository mockDepartmentRepo;

    @SpyBean
    private EntityFieldValidator entityFieldValidator;

    private static final String TEST_EMPLOYEE_ID = "abcd453";
    private static final String TEST_EMPLOYEE_FULLNAME = "Bob";

    private static final String TEST_TASK_TITLE = "Testing Task";
    private static final String TEST_TASK_DESCRIPTION = "Keep on testing.";

    private static final String TEST_DEPARTMENT_ID = "1A2B3C4D";

    @Test
    public void findAllEmployees_returnsList() {
        when(mockEmployeeRepo.findAll())
            .thenReturn(new ArrayList<Employee>());

        assertThat("findAll should return List",
                   employeeService.findAllEmployees(),
                   instanceOf(List.class));
    }

    @Test
    public void findEmployee_returnsEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        Task testTask = new Task();
        testTask.setTitle(TEST_TASK_TITLE);
        testTask.setDescription(TEST_TASK_DESCRIPTION);
        testEmployee.setCurrentTask(testTask);

        when(mockEmployeeRepo
                .findById(TEST_EMPLOYEE_ID))
             .thenReturn(Optional.of(testEmployee));

        Employee foundEmployee = employeeService.findEmployee(
                                            TEST_EMPLOYEE_ID);

        assertThat("findEmployee should return Employee",
                   foundEmployee, instanceOf(Employee.class));
        assertEquals("findEmployee should return correct data",
                     foundEmployee.getFullName(),
                     TEST_EMPLOYEE_FULLNAME);
        assertEquals("findEmployee should return correct data",
                foundEmployee.getDepartmentId(),
                TEST_DEPARTMENT_ID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findEmployee_unknownThrowsNotFound() {
        when(mockEmployeeRepo
                .findById(TEST_EMPLOYEE_ID))
             .thenReturn(Optional.ofNullable(null));

        employeeService.findEmployee(TEST_EMPLOYEE_ID);
    }

    @Test
    public void createEmployee_returnsEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        Task testTask = new Task();
        testTask.setTitle(TEST_TASK_TITLE);
        testTask.setDescription(TEST_TASK_DESCRIPTION);
        testEmployee.setCurrentTask(testTask);

        when(mockDepartmentRepo.existsById(TEST_DEPARTMENT_ID))
            .thenReturn(true);
        when(mockEmployeeRepo.save(isA(Employee.class)))
             .thenReturn(testEmployee);

        Employee savedEmployee = employeeService.saveEmployee(
                                            testEmployee);

        assertThat("saveEmployee should return Employee",
                   savedEmployee, instanceOf(Employee.class));
        assertEquals("findByEmployeePath should return correct data",
                     savedEmployee.getFullName(),
                     TEST_EMPLOYEE_FULLNAME);
        assertEquals("findEmployee should return correct data",
                     savedEmployee.getFullName(),
                     TEST_EMPLOYEE_FULLNAME);
        assertEquals("findEmployee should return correct data",
                     savedEmployee.getDepartmentId(),
                     TEST_DEPARTMENT_ID);
    }


    @Test(expected = InvalidEntityFieldException.class)
    public void createEmployee_nullFullNameThrowsInvalidField() {
        Employee testEmployee = new Employee();
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        when(mockDepartmentRepo.existsById(TEST_DEPARTMENT_ID))
             .thenReturn(true);
        employeeService.saveEmployee(testEmployee);

        verify(entityFieldValidator).validateFullName(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void createEmployee_invalidDepartmentIdThrowsNotFound() {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        when(mockDepartmentRepo.existsById(TEST_DEPARTMENT_ID))
             .thenReturn(false);
        employeeService.saveEmployee(testEmployee);
    }

    @Test(expected = EntityNotFoundException.class)
    public void createEmployee_nullDepartmentdThrowsNotFound() {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);

        employeeService.saveEmployee(testEmployee);
    }

    @Test
    public void updateEmployee_returnsEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setId(TEST_EMPLOYEE_ID);
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        Task testTask = new Task();
        testTask.setTitle(TEST_TASK_TITLE);
        testTask.setDescription(TEST_TASK_DESCRIPTION);
        testEmployee.setCurrentTask(testTask);

        when(mockEmployeeRepo.existsById(TEST_EMPLOYEE_ID))
            .thenReturn(true);
        when(mockDepartmentRepo.existsById(TEST_DEPARTMENT_ID))
            .thenReturn(true);
        when(mockEmployeeRepo.save(isA(Employee.class)))
             .thenReturn(testEmployee);

        Employee savedEmployee = employeeService.updateEmployee(
                                            testEmployee);

        assertThat("saveEmployee should return Employee",
                   savedEmployee, instanceOf(Employee.class));
        assertEquals("findByEmployeePath should return correct data",
                     savedEmployee.getFullName(),
                     TEST_EMPLOYEE_FULLNAME);
        assertEquals("findEmployee should return correct data",
                     savedEmployee.getFullName(),
                     TEST_EMPLOYEE_FULLNAME);
        assertEquals("findEmployee should return correct data",
                     savedEmployee.getDepartmentId(),
                     TEST_DEPARTMENT_ID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateEmployee_invalidEmployeeIdThrowsNotFound() {
        Employee testEmployee = new Employee();
        testEmployee.setId(TEST_EMPLOYEE_ID);
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        when(mockEmployeeRepo.existsById(TEST_DEPARTMENT_ID))
             .thenReturn(false);
        employeeService.updateEmployee(testEmployee);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateEmployee_nullEmployeeIdThrowsNotFound() {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);

        employeeService.updateEmployee(testEmployee);
    }

    @Test
    public void deleteEmployee_noErrors() {
        employeeService.deleteEmployee(TEST_EMPLOYEE_ID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deletEmployee_unknownThrowsNotFound() {
        doThrow(new IllegalArgumentException())
        .when(mockEmployeeRepo)
        .deleteById(TEST_EMPLOYEE_ID);

        employeeService.deleteEmployee(TEST_EMPLOYEE_ID);
    }
}
