package de.hdm.photon.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import test.de.hdm.photon.TestContainers;


@SpringBootApplication
@ComponentScan("de.hdm.photon")
@EntityScan("de.hdm.photon")
@EnableJpaRepositories(basePackages="de.hdm.photon")
public class LogicTestApplication {

  public static void main(String[] args) {
 SpringApplication.run(LogicTestApplication.class, args);
  }
}

