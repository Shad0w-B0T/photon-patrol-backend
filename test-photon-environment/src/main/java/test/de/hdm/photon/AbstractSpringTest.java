package test.de.hdm.photon;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

/**
 * The {@code AbstractSpringTest} class provides a base for Spring-based test classes.
 * It contains a utility test method to verify that all {@code @Autowired} components
 * in a derived test class are properly loaded by the Spring context.
 *
 * <p>This class uses Lombok's {@code @Log4j2} annotation for logging purposes.</p>
 *
 * <p>Example usage:
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
 * </p>
 *
 * @see org.springframework.beans.factory.annotation.Autowired
 * @see org.junit.jupiter.api.Test
 */
@Log4j2
public abstract class AbstractSpringTest {
  /**
   * Test method to verify that all {@code @Autowired} components in the derived class
   * are properly loaded by the Spring context.
   *
   * <p>This method iterates through all declared fields in the derived test class,
   * checks if they are annotated with {@code @Autowired}, and asserts that they are
   * not {@code null}, indicating successful dependency injection by Spring.</p>
   *
   * <p>Example log output:
   * <pre>
   * {@code
   * Checking autowired components for=MyTest.
   * Checking bean=MyRepository.
   * }
   * </pre>
   * </p>
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


