package com.frankc.training.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Profile("init-data")
@Configuration
class ApplicationConfig {

  @Bean
  public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

    Resource sourceData = new ClassPathResource("init-data.json");

    Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
    factory.setResources(new Resource[] { sourceData });
    return factory;
  }
}
