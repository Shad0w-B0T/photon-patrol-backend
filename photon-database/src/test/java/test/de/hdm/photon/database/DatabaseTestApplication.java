package test.de.hdm.photon.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("de.hdm.photon.database")
@EntityScan("de.hdm.photon.database")
public class DatabaseTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(DatabaseTestApplication.class, args);
  }
}
