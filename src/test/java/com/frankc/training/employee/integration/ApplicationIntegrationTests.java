package com.frankc.training.employee.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;

import com.frankc.training.employee.controllers.DepartmentController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@IfProfileValue(name="spring.profiles.active", value="integration-test")
@ActiveProfiles("init-data")
public class ApplicationIntegrationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String BASE_URL = "http://localhost";

    @LocalServerPort
    int serverPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void departmentController_findAll(){
        String result = this.restTemplate.getForObject(
                BASE_URL + ":" + serverPort +
                DepartmentController.BASE_PATH,
                String.class);

        logger.info("Received departments: " + result);
    }
}
