package test.de.hdm.photon;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.utility.DockerImageName;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;


/**
 * The {@code AbstractSpringTest} class provides a base for Spring-based test classes.
 * It contains a utility test method to verify that all {@code @Autowired} components
 * in a derived test class are properly loaded by the Spring context.
 * <p>
 * This class uses Lombok's {@code @Log4j2} annotation for logging purposes.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @ExtendWith(SpringExtension.class)
 * @ContextConfiguration(classes = MyTestConfig.class)
 * public class MyTest extends AbstractSpringTest {
 *     @Autowired
 *     private MyRepository myRepository;
 *
 *     // Additional test methods
 * }
 * }
 * </pre>
 * <p>
 * *
 *
 * @see org.springframework.beans.factory.annotation.Autowired
 * @see org.junit.jupiter.api.Test
 */
@Log4j2
public abstract class AbstractSpringTest {

  private static InfluxDBContainer<?> influxDBContainer;
  private static PostgreSQLContainer<?> postgreSQLContainer;
  private static RabbitMQContainer rabbitMQContainer;

  /**
   * The Spring {@link ConfigurableApplicationContext} instance used for interacting with the Spring container.
   *
   * <p>This field is automatically injected by Spring and provides access to the application context,
   * which can be used to retrieve beans, manage the lifecycle of the application, and perform various
   * context-related operations.</p>
   *
   * <p>Note: This field is protected, allowing access within subclasses and the package, which is useful
   * for integrating with the Spring container or accessing application context features.</p>
   */
  @Autowired
  protected ConfigurableApplicationContext springContext;

  /**
   * Initializes test containers for the InfluxDB, PostgreSQL, and RabbitMQ services.
   * <p>
   * This method sets up the containers required for integration tests. It configures each container with
   * necessary environment variables and port bindings. If the environment variable {@code PHOTON_TEST_CONTAINERS}
   * is set to {@code true}, it will skip starting the containers.
   * </p>
   */
  @BeforeAll
  @SuppressWarnings("all")
  public static void init() {
    if (ignoreContainers()) {
      influxDBContainer = null;
      postgreSQLContainer = null;

    } else {
      influxDBContainer = new InfluxDBContainer<>(DockerImageName.parse("influxdb:2"))
          .withUsername("my-user")
          .withPassword("my-password")
          .withOrganization("my-org")
          .withBucket("my-bucket")
          .withRetention("1w")
          .withAdminToken("my-super-secret-auth-token");

      influxDBContainer.setPortBindings(List.of("8086:8086"));
      influxDBContainer.start();

      postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
          .withDatabaseName("postgres")
          .withUsername("postgres")
          .withPassword("test");

      postgreSQLContainer.setPortBindings(List.of("5432:5432"));
      postgreSQLContainer.start();

      rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:management-alpine"));
      rabbitMQContainer
          .withEnv("RABBITMQ_DEFAULT_USER", "admin")
          .withEnv("RABBITMQ_DEFAULT_PASS", "instar")
          .withAdminPassword("instar");

      rabbitMQContainer.setPortBindings(List.of("1883:1883", "5672:5672"));
      rabbitMQContainer.start();
    }
  }

  /**
   * Shuts down the test containers for InfluxDB, PostgreSQL, and RabbitMQ if they are present.
   * This method is annotated with {@link AfterAll} to ensure it runs after all tests have completed.
   * It safely closes each container using {@link Startable#close()} if they are not null.
   */
  @AfterAll
  public static void shutdown() {
    Optional.ofNullable(influxDBContainer).ifPresent(Startable::close);
    Optional.ofNullable(postgreSQLContainer).ifPresent(Startable::close);
    Optional.ofNullable(rabbitMQContainer).ifPresent(Startable::close);
  }

  /**
   * Determines whether to ignore starting test containers based on an environment variable.
   *
   * <p>This method checks the value of the environment variable "PHOTON_TEST_CONTAINERS". If the variable is
   * present and can be parsed as a boolean, its value is used to determine whether to ignore the containers.
   * If the environment variable is not present or cannot be parsed, the method defaults to ignoring containers.</p>
   *
   * @return {@code true} if containers should be ignored (based on the environment variable), {@code false} otherwise.
   */
  private static boolean ignoreContainers() {
    System.out.println(System.getenv("PHOTON_TEST_CONTAINERS"));
    return Optional
        .ofNullable(System.getenv("PHOTON_TEST_CONTAINERS"))
        .map(Boolean::parseBoolean)
        .orElse(false);
  }

  /**
   * Test method to verify that all {@code @Autowired} components in the derived class
   * are properly loaded by the Spring context.
   * <p>
   * This method iterates through all declared fields in the derived test class,
   * checks if they are annotated with {@code @Autowired}, and asserts that they are
   * not {@code null}, indicating successful dependency injection by Spring.
   * <p>
   * Example log output:
   * <pre>
   * {@code
   * Checking autowired components for=MyTest.
   * Checking bean=MyRepository.
   * }
   * </pre>
   */
  @Test
  @DisplayName("Test JPA-Repository load.")
  public void contextLoads() {
    log.info("Checking autowired components for={}.", this.getClass().getSimpleName());

    for (final Field declaredField : this.getClass().getDeclaredFields()) {
      if (declaredField.getAnnotation(Autowired.class) == null) {
        continue;
      }
      log.info("Checking bean={}.", declaredField.getName());
      Assertions.assertNotNull(declaredField);
    }
  }
}


