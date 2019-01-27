package com.frankc.training.employee;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.frankc.training.employee.services.DepartmentService;

@ActiveProfiles("init-data")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestEmployeeApplicationTests {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void contextLoadsWithInitData() {
        assertTrue(departmentService.findAllDepartments().size() > 0);
    }
}
