package test.de.hdm.photon.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


@SpringBootApplication
@ComponentScan("de.hdm.photon.database")
@EntityScan("de.hdm.photon.database")
@EnableJpaRepositories(basePackages="de.hdm.photon.database")
public class DatabaseTestApplication {

  @Container
  private static final InfluxDBContainer<?> INFLUX_CONTAINER;

  @Container
  private static final PostgreSQLContainer<?> SQL_CONTAINER;

  static {
    INFLUX_CONTAINER = new InfluxDBContainer<>(DockerImageName.parse("influxdb:2"))
        .withUsername("my-user")
        .withPassword("my-password")
        .withOrganization("my-org")
        .withBucket("my-bucket")
        .withRetention("1w")
        .withAdminToken("my-super-secret-auth-token");

    INFLUX_CONTAINER.start();

    SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
        .withDatabaseName("postgres")
        .withUsername("postgres")
        .withPassword("test");

    SQL_CONTAINER.start();
  }

  public static void main(String[] args) {
    SpringApplication.run(DatabaseTestApplication.class, args);
  }
}
