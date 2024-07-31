package test.de.hdm.photon.queue.logic.connection;

import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.RabbitConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import test.de.hdm.photon.AbstractSpringTest;


/**
 * Test class for validating the RabbitConnection within a Spring application.
 *
 * <p>This class performs unit tests to ensure that the {@link RabbitConnection}
 * behaves as expected under various conditions, and checks the presence of beans
 * in the Spring application context.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @SpringBootTest} - Indicates that the class should use Spring Boot's testing support.</li>
 *   <li>{@code @DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)} - Indicates that the Spring context should be reloaded before each test method.</li>
 *   <li>{@code @AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)} - Disables the default behavior of replacing the application’s database with an embedded database.</li>
 * </ul>
 */
@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public final class TestRabbitConnection extends AbstractSpringTest {

  /**
   * Tests the {@link RabbitConnection} for negative scenarios where null or empty values are passed.
   *
   * <p>This test verifies that the constructor of {@link RabbitConnection} correctly throws
   * a {@link NullPointerException} when passed null or empty values.</p>
   */
  @Test
  @DisplayName("Testing RabbitConnection negative")
  public void testRabbitConnectionNegative() {
    Assertions.assertThrows(NullPointerException.class,
        () -> new RabbitConnection(null, null));

    Assertions.assertThrows(NullPointerException.class,
        () -> new RabbitConnection("", null));
  }

  /**
   * Tests the presence of {@link IRabbitConnection} and {@link RabbitConnection} beans
   * in the Spring application context.
   *
   * <p>This test ensures that the application context correctly initializes and contains
   * the expected beans for RabbitConnection.</p>
   */
  @Test
  @DisplayName("Testing RabbitConnection bean")
  public void testRabbitConnectionBean() {
    Assertions.assertDoesNotThrow(() -> {
      Assertions.assertNotNull(this.springContext.getBean(IRabbitConnection.class));
      Assertions.assertNotNull(this.springContext.getBean(RabbitConnection.class));
    });
  }
}
