package test.de.hdm.photon.queue.logic.connection;

import de.hdm.photon.queue.logic.connection.IRabbitConnection;
import de.hdm.photon.queue.logic.connection.IRabbitPublisher;
import de.hdm.photon.queue.logic.connection.IRabbitSubscriber;
import de.hdm.photon.queue.logic.connection.RabbitPublisher;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import test.de.hdm.photon.AbstractSpringTest;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test class for validating the RabbitMQ Publisher-Subscriber (Pub-Sub) within a Spring application.
 *
 * <p>This class performs integration tests to ensure that the RabbitMQ Publisher and Subscriber
 * work correctly together within the context of the application.</p>
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
public final class TestRabbitPubSub extends AbstractSpringTest {

  @Autowired
  private IRabbitConnection rabbitConnection;

  private final @NotNull String message = "test";

  /**
   * Tests the Publisher-Subscriber interaction within RabbitMQ.
   *
   * <p>This test verifies that the {@link IRabbitPublisher} and {@link IRabbitSubscriber} can
   * successfully publish and subscribe to messages. It checks the following:</p>
   * <ul>
   *   <li>Publisher and Subscriber beans are correctly initialized.</li>
   *   <li>Messages are correctly published and received.</li>
   *   <li>The content of the received message matches the expected message.</li>
   * </ul>
   */
  @Test
  @DisplayName("Testing RabbitPubSub")
  public void testRabbitPubSub() {
    Assertions.assertDoesNotThrow(() -> {
      // Publisher
      final IRabbitPublisher publisher = this.rabbitConnection.publisher();
      Assertions.assertNotNull(publisher);

      // Subscriber
      final IRabbitSubscriber subscriber = this.rabbitConnection.subscriber(true);
      Assertions.assertNotNull(subscriber);

      final AtomicBoolean done = new AtomicBoolean(false);
      subscriber.subscribe(bytes -> {
        Assertions.assertNotNull(bytes);

        // Message
        final String message = new String(bytes, StandardCharsets.UTF_8);
        Assertions.assertEquals(this.message, message);
        done.set(true);
      });

      // Publish the message
      publisher.publish(this.message.getBytes(StandardCharsets.UTF_8));

      // Wait until the message is received
      while (!done.get()) {
      }
    });
  }
}
