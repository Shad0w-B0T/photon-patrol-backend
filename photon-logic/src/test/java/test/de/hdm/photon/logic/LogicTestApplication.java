package test.de.hdm.photon.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Entry point for the Spring Boot application for testing the business logic layer.
 *
 * <p>This class sets up the Spring Boot application context by scanning for components,
 * entities, and repositories within the specified package. It serves as the main class
 * to bootstrap the application.</p>
 */
@SpringBootApplication
@ComponentScan("de.hdm.photon")
@EntityScan("de.hdm.photon")
@EnableJpaRepositories(basePackages="de.hdm.photon")
public class LogicTestApplication {

  /**
   * Main method to run the Spring Boot application.
   *
   * @param args Command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(LogicTestApplication.class, args);
  }
}

