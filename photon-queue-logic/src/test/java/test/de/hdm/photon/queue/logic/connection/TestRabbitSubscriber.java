package test.de.hdm.photon.queue.logic.connection;

import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.RabbitPublisher;
import de.hdm.photon.queue.logic.connection.RabbitSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import test.de.hdm.photon.AbstractSpringTest;

/**
 * Test class for validating the RabbitSubscriber within a Spring application.
 *
 * <p>This class performs unit tests to ensure that the {@link RabbitSubscriber}
 * behaves as expected under various conditions, and checks the presence of the
 * subscriber method in the {@link IRabbitConnection} bean.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @SpringBootTest} - Indicates that the class should use Spring Boot's testing support.</li>
 *   <li>{@code @DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)} - Indicates that the Spring context should be reloaded before each test method.</li>
 *   <li>{@code @AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)} - Disables the default behavior of replacing the application’s database with an embedded database.</li>
 * </ul>
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public final class TestRabbitSubscriber extends AbstractSpringTest {

  @Autowired
  private IRabbitConnection rabbitConnection;

  /**
   * Tests the {@link RabbitSubscriber} for negative scenarios where null values are passed.
   *
   * <p>This test verifies that the constructor of {@link RabbitSubscriber} correctly throws
   * a {@link NullPointerException} when passed null values.</p>
   */
  @Test
  @DisplayName("Testing RabbitSubscriber negative")
  public void testRabbitSubscriberNegative() {
    Assertions.assertThrows(NullPointerException.class,
        () -> new RabbitSubscriber(null, null, false));
  }

  /**
   * Tests the presence of the subscriber method in the {@link IRabbitConnection} bean.
   *
   * <p>This test ensures that the {@link IRabbitConnection} bean correctly initializes
   * and contains the subscriber method without throwing any exceptions.</p>
   */
  @Test
  @DisplayName("Testing RabbitSubscriber bean")
  public void testRabbitSubscriberBean() {
    Assertions.assertDoesNotThrow(() -> {
      Assertions.assertNotNull(this.rabbitConnection.subscriber(false));
    });
  }
}
