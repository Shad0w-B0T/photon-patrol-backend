package test.de.hdm.photon;

import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;


/**
 * {@code TestContainers} manages the lifecycle of Docker containers used for integration testing.
 * <p>
 * It provides methods to start containers for InfluxDB, PostgreSQL, and RabbitMQ.
 * Containers can be controlled using environment variables.
 * </p>
 */
public final class TestContainers {

  /**
   * Starts all required Docker containers for testing.
   * <p>
   * This method will start InfluxDB, PostgreSQL, and RabbitMQ containers if the
   * {@code PHOTON_TEST_CONTAINERS} environment variable is not set to {@code false}.
   * </p>
   */
  public static void startContainers() {
    startInfluxContainer();
    startPostgresContainer();
    startRabbitContainer();
  }

  /**
   * Starts the InfluxDB container if {@code PHOTON_TEST_CONTAINERS} is not set to {@code false}.
   * <p>
   * Configures the container with default settings and binds port 8086.
   * </p>
   */
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

  /**
   * Starts the PostgreSQL container if {@code PHOTON_TEST_CONTAINERS} is not set to {@code false}.
   * <p>
   * Configures the container with default settings and binds port 5432.
   * </p>
   */
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

  /**
   * Starts the RabbitMQ container if {@code PHOTON_TEST_CONTAINERS} is not set to {@code false}.
   * <p>
   * Configures the container with default settings and environment variables.
   * </p>
   */
  public static void startRabbitContainer() {
    if (ignoreContainers()) {
      return;
    }

    try (final RabbitMQContainer rabbitMQContainer =
             new RabbitMQContainer(DockerImageName.parse("rabbitmq:management-alpine"))) {
      rabbitMQContainer
          .withEnv("RABBITMQ_DEFAULT_USER", "admin")
          .withEnv("RABBITMQ_DEFAULT_PASS", "instar")
          .start();
    }
  }

  /**
   * Determines whether to ignore starting containers based on the {@code PHOTON_TEST_CONTAINERS} environment variable.
   * <p>
   * If the environment variable is not set or set to {@code true}, containers will be started.
   * If set to {@code false}, containers will not be started.
   * </p>
   *
   * @return {@code true} if containers should be ignored, {@code false} otherwise.
   */
  private static boolean ignoreContainers() {
    System.out.println(System.getenv("PHOTON_TEST_CONTAINERS"));
    return Optional
        .ofNullable(System.getenv("PHOTON_TEST_CONTAINERS"))
        .map(Boolean::parseBoolean)
        .orElse(false);

  }

}
