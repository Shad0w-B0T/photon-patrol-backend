package test.de.hdm.photon.queue.logic.config;

import de.hdm.photon.queue.logic.config.IRabbitContext;
import de.hdm.photon.queue.logic.config.ImmutableRabbitContext;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import test.de.hdm.photon.AbstractSpringTest;


/**
 * Test class for validating the RabbitContext within a Spring application.
 *
 * <p>This class performs unit tests to ensure that the {@link ImmutableRabbitContext}
 * behaves as expected under various conditions, and checks the presence of beans
 * in the Spring application context.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @DataJpaTest} - Configures an in-memory database, scans for @Entity classes and Spring Data JPA repositories.</li>
 *   <li>{@code @DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)} - Indicates that the Spring context should be reloaded before each test method.</li>
 *   <li>{@code @AutoConfigureTestDatabase(replace=Replace.NONE)} - Disables the default behavior of replacing the application’s database with an embedded database.</li>
 * </ul>
 */
@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public final class TestRabbitContext extends AbstractSpringTest {

  /**
   * Tests the {@link ImmutableRabbitContext} for negative scenarios where null values are passed.
   *
   * <p>This test verifies that the constructor of {@link ImmutableRabbitContext} correctly throws
   * a {@link NullPointerException} when passed null values.</p>
   */
  @Test
  @DisplayName("Testing RabbitContext negative")
  public void testRabbitContextNegative() {
    Assertions.assertThrows(NullPointerException.class,
        () -> new ImmutableRabbitContext(null, null, null));

    Assertions.assertThrows(NullPointerException.class,
        () -> new ImmutableRabbitContext(null, "", ""));

    Assertions.assertThrows(NullPointerException.class,
        () -> new ImmutableRabbitContext(null, null, ""));
  }

  /**
   * Tests the presence of {@link IRabbitContext} and {@link ImmutableRabbitContext} beans
   * in the Spring application context.
   *
   * <p>This test ensures that the application context correctly initializes and contains
   * the expected beans for RabbitContext.</p>
   */
  @Test
  @DisplayName("Testing RabbitContext bean")
  public void testRabbitContextBean() {
    Assertions.assertDoesNotThrow(() -> {
      Assertions.assertNotNull(this.springContext.getBean(IRabbitContext.class));
      Assertions.assertNotNull(this.springContext.getBean(ImmutableRabbitContext.class));
    });
  }
}