package test.de.hdm.photon.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import test.de.hdm.photon.TestContainers;

/**
 * Main class for running the Spring Boot application in the context of database testing.
 * This class configures component scanning, entity scanning, and JPA repository scanning
 * specifically for the database layer of the application.
 */
@SpringBootApplication
@ComponentScan("de.hdm.photon.database")
@EntityScan("de.hdm.photon.database")
@EnableJpaRepositories(basePackages="de.hdm.photon.database")
public class DatabaseTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(DatabaseTestApplication.class, args);
  }
}
