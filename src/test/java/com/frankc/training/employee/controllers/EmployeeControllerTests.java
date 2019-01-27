package com.frankc.training.employee.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frankc.training.employee.entities.Employee;
import com.frankc.training.employee.entities.Task;
import com.frankc.training.employee.exceptions.EntityNotFoundException;
import com.frankc.training.employee.services.EmployeeService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {EmployeeController.class})
@WebMvcTest
public class EmployeeControllerTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService mockEmployeeService;

    private static final String TEST_EMPLOYEE_ID = "abcd453";
    private static final String TEST_EMPLOYEE_FULLNAME = "Bob";

    private static final String TEST_TASK_TITLE = "Testing Task";
    private static final String TEST_TASK_DESCRIPTION = "Keep on testing.";

    private static final String TEST_DEPARTMENT_ID = "1A2B3C4D";

    @Test
    public void findAllEmployees_returnsList() throws Exception {
        when(mockEmployeeService.findAllEmployees())
            .thenReturn(new ArrayList<Employee>());

        MvcResult result = this.mockMvc
                .perform(get(EmployeeController.BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isNumber())
                .andReturn();

        logger.info("Result from findAllEmployees: "
                    + result.getResponse().getContentAsString());
    }

    @Test
    public void findEmployee_returnsOk() throws Exception {
        Employee testEmployee = new Employee();
        testEmployee.setId(TEST_EMPLOYEE_ID);
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        Task testTask = new Task();
        testTask.setTitle(TEST_TASK_TITLE);
        testTask.setDescription(TEST_TASK_DESCRIPTION);
        testEmployee.setCurrentTask(testTask);

        when(mockEmployeeService
                .findEmployee(testEmployee.getId()))
            .thenReturn(testEmployee);

        this.mockMvc.perform(get(EmployeeController.BASE_PATH
                                 + testEmployee.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id")
                               .value(testEmployee.getId()))
                    .andExpect(jsonPath("$.fullName")
                               .value(testEmployee.getFullName()))
                    .andExpect(jsonPath("$.departmentId")
                               .value(testEmployee.getDepartmentId()))
                    .andExpect(jsonPath("$.currentTask.title")
                               .value(testTask.getTitle()))
                    .andExpect(jsonPath("$.currentTask.description")
                               .value(testTask.getDescription()));
    }

    @Test
    public void findEmployee_unknownReturnsNotFound() throws Exception {
        when(mockEmployeeService.findEmployee(TEST_EMPLOYEE_ID))
            .thenThrow(new EntityNotFoundException("Invalid employee id: "
                                                   + TEST_EMPLOYEE_ID));

        this.mockMvc.perform(get(EmployeeController.BASE_PATH
                                 + TEST_EMPLOYEE_ID))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void createEmployee_returnsCreated() throws Exception {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        Task testTask = new Task();
        testTask.setTitle(TEST_TASK_TITLE);
        testTask.setDescription(TEST_TASK_DESCRIPTION);
        testEmployee.setCurrentTask(testTask);

        when(mockEmployeeService.saveEmployee(any()))
            .thenReturn(testEmployee);

        this.mockMvc.perform(
                post(EmployeeController.BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName")
                        .value(testEmployee.getFullName()))
                .andExpect(jsonPath("$.departmentId")
                        .value(testEmployee.getDepartmentId()))
                .andExpect(jsonPath("$.currentTask.title")
                        .value(testTask.getTitle()))
                .andExpect(jsonPath("$.currentTask.description")
                        .value(testTask.getDescription()));
    }

    @Test
    public void createEmployee_withUnknownDepartment_returnsNotFound() throws Exception {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        when(mockEmployeeService.saveEmployee(any()))
            .thenThrow(new EntityNotFoundException("Invalid department id: "
                                          + testEmployee.getDepartmentId()));

        this.mockMvc.perform(
                post(EmployeeController.BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmployee_returnsOk() throws Exception {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        Task testTask = new Task();
        testTask.setTitle(TEST_TASK_TITLE);
        testTask.setDescription(TEST_TASK_DESCRIPTION);
        testEmployee.setCurrentTask(testTask);

        when(mockEmployeeService.updateEmployee(any()))
            .thenReturn(testEmployee);

        this.mockMvc.perform(
                put(EmployeeController.BASE_PATH + TEST_EMPLOYEE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName")
                        .value(testEmployee.getFullName()))
                .andExpect(jsonPath("$.departmentId")
                        .value(testEmployee.getDepartmentId()))
                .andExpect(jsonPath("$.currentTask.title")
                        .value(testTask.getTitle()))
                .andExpect(jsonPath("$.currentTask.description")
                        .value(testTask.getDescription()));
    }

    @Test
    public void updateEmployee_withUnknownEmployee_returnsNotFound() throws Exception {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        when(mockEmployeeService.updateEmployee(any()))
            .thenThrow(new EntityNotFoundException("Invalid employee id: "
                                                   + testEmployee.getId()));

        this.mockMvc.perform(
                put(EmployeeController.BASE_PATH + TEST_EMPLOYEE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmployee_withInvalidDepartment_returnsNotFound() throws Exception {
        Employee testEmployee = new Employee();
        testEmployee.setFullName(TEST_EMPLOYEE_FULLNAME);
        testEmployee.setDepartmentId(TEST_DEPARTMENT_ID);

        when(mockEmployeeService.updateEmployee(any()))
            .thenThrow(new EntityNotFoundException("Invalid department id: "
                                                   + testEmployee.getDepartmentId()));

        this.mockMvc.perform(
                put(EmployeeController.BASE_PATH + TEST_EMPLOYEE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteEmployee_returnsOk() throws Exception {
        this.mockMvc.perform(delete(EmployeeController.BASE_PATH
                                    + TEST_EMPLOYEE_ID))
                    .andExpect(status().isNoContent());
    }

    @Test
    public void deleteEmployee_unknownReturnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Invalid employee id: "
                                            + TEST_EMPLOYEE_ID))
             .when(mockEmployeeService)
             .deleteEmployee(TEST_EMPLOYEE_ID);

        this.mockMvc.perform(delete(EmployeeController.BASE_PATH
                                    + TEST_EMPLOYEE_ID))
                    .andExpect(status().isNotFound());
    }
}
