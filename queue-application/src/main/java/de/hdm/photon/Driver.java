package de.hdm.photon;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
    new SpringApplicationBuilder()
        .sources(Driver.class)
        .main(Driver.class)
        .web(WebApplicationType.NONE)
        .build()
        .run(args);
  }
}