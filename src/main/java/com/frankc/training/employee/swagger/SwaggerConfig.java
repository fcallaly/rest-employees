package com.frankc.training.employee.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frankc.training.employee.controllers.DepartmentController;
import com.frankc.training.employee.controllers.EmployeeController;
import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for swagger2.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(apiInfo())
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.frankc.training"))
          .paths(paths())
          .build()
          .useDefaultResponseMessages(false);
    }

    private Predicate<String> paths() {
        return PathSelectors.regex("^(" + EmployeeController.BASE_PATH + "|"
                                   + DepartmentController.BASE_PATH + ").*$");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
         .title("Employee/Department REST API")
         .description("RESTful interface to create, retrieve and "
                      + "delete Employee and Department records")
         .version("0.1")
         .build();
    }
}
