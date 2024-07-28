package test.de.hdm.photon;

import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;


public final class TestContainers {

  public static void startContainers() {
    startInfluxContainer();
    startPostgresContainer();
  }

  public static void startInfluxContainer() {
    if (ignoreContainers()) {
      return;
    }

    final InfluxDBContainer<?> container = new InfluxDBContainer<>(DockerImageName.parse("influxdb:2"))
        .withUsername("my-user")
        .withPassword("my-password")
        .withOrganization("my-org")
        .withBucket("my-bucket")
        .withRetention("1w")
        .withAdminToken("my-super-secret-auth-token");

    container.setPortBindings(List.of("8086:8086"));
    container.start();
  }

  public static void startPostgresContainer() {
    if (ignoreContainers()) {
      return;
    }

    final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
        .withDatabaseName("postgres")
        .withUsername("postgres")
        .withPassword("test");

    container.setPortBindings(List.of("5432:5432"));
    container.start();
  }

  public static void startRabbitContainer() {
    if (ignoreContainers()) {
      return;
    }

    try (final PostgreSQLContainer<?> postgresContainer =
             new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))) {
      postgresContainer.withDatabaseName("postgres")
          .withUsername("postgres")
          .withPassword("test")
          .start();
    }
  }

  private static boolean ignoreContainers() {
    System.out.println(System.getenv("PHOTON_TEST_CONTAINERS"));
    return Optional
        .ofNullable(System.getenv("PHOTON_TEST_CONTAINERS"))
        .map(Boolean::parseBoolean)
        .orElse(false);

  }

}
