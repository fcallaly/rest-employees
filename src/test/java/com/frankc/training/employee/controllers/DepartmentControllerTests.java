package com.frankc.training.employee.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.frankc.training.employee.entities.Department;
import com.frankc.training.employee.exceptions.EntityNotFoundException;
import com.frankc.training.employee.services.DepartmentService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DepartmentController.class})
@WebMvcTest
public class DepartmentControllerTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService mockDepartmentService;

    private static final String TEST_DEPARTMENT_ID = "1A2B3C4D";
    private static final String TEST_DEPARTMENT_NAME = "The Testers";

    @Test
    public void findAllDepartments_returnsList() throws Exception {
        when(mockDepartmentService.findAllDepartments())
            .thenReturn(new ArrayList<Department>());

        MvcResult result = this.mockMvc
                .perform(get(DepartmentController.BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isNumber())
                .andReturn();

        logger.info("Result from findAllDepartments: "
                    + result.getResponse().getContentAsString());
    }

    @Test
    public void findDepartment_returnsOk() throws Exception {
        Department testDepartment = new Department();
        testDepartment.setId(TEST_DEPARTMENT_ID);
        testDepartment.setName(TEST_DEPARTMENT_NAME);

        when(mockDepartmentService
                .findDepartment(testDepartment.getId()))
            .thenReturn(testDepartment);

        this.mockMvc.perform(get(DepartmentController.BASE_PATH
                                 + testDepartment.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id")
                               .value(testDepartment.getId()))
                    .andExpect(jsonPath("$.name")
                               .value(testDepartment.getName()));
    }

    @Test
    public void findDepartment_unknownReturnsNotFound() throws Exception {
        when(mockDepartmentService.findDepartment(TEST_DEPARTMENT_ID))
            .thenThrow(new EntityNotFoundException("Invalid department id:"
                                                   + TEST_DEPARTMENT_ID));

        this.mockMvc.perform(get(DepartmentController.BASE_PATH
                                 + TEST_DEPARTMENT_ID))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDepartment_returnsOk() throws Exception {
        this.mockMvc.perform(delete(DepartmentController.BASE_PATH
                                    + TEST_DEPARTMENT_ID))
                    .andExpect(status().isNoContent());
    }

    @Test
    public void deleteDepartment_unknownReturnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Invalid department id: "
                                            + TEST_DEPARTMENT_ID))
             .when(mockDepartmentService)
             .deleteDepartment(TEST_DEPARTMENT_ID);

        this.mockMvc.perform(delete(DepartmentController.BASE_PATH
                                    + TEST_DEPARTMENT_ID))
                    .andExpect(status().isNotFound());
    }
}
