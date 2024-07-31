package test.de.hdm.photon.queue.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import test.de.hdm.photon.TestContainers;


/**
 * Entry point for the Spring Boot application.
 *
 * <p>This class is annotated with {@link SpringBootApplication} to mark it as the main
 * configuration class for the Spring Boot application. It enables component scanning,
 * JPA repositories, and entity scanning in the specified base packages.</p>
 *
 * <p>The {@link #main(String[])} method starts the Spring Boot application by invoking
 * {@link SpringApplication#run(Class, String...)}.</p>
 */
@SpringBootApplication
@ComponentScan("de.hdm.photon")
@EntityScan("de.hdm.photon")
@EnableJpaRepositories(basePackages="de.hdm.photon")
public class QueueTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(QueueTestApplication.class, args);
  }
}

