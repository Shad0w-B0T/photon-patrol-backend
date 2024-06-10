package de.hdm.photon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("de.hdm.photon")
@EnableJpaRepositories(basePackages="de.hdm.photon")
public class Driver {
  /**
   * The main method that starts the Spring Boot application.
   *
   * @param args command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(Driver.class, args);
  }
}