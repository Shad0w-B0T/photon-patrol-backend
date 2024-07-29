package test.de.hdm.photon.schema.exception;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.PasswordException;
import de.hdm.photon.schema.exception.PasswordRequirementMismatchException;
import de.hdm.photon.schema.exception.RabbitConnectionException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * The {@code ExceptionTest} class is a parameterized test class designed to verify the behavior
 * of various custom exception classes. It ensures that each exception class has at least one constructor
 * that takes a {@code String} argument and that instances of the exception can be correctly thrown and
 * caught.
 *
 * <p>This class uses Lombok's {@code @Log4j2} annotation for logging purposes.</p>
 *
 * <p>The class is annotated with {@code @TestInstance(Lifecycle.PER_CLASS)} to ensure that a single instance
 * of the test class is used for all test methods. This is useful when tests are dependent on shared state.</p>
 *
 * @see org.junit.jupiter.params.ParameterizedTest
 * @see org.junit.jupiter.params.provider.MethodSource
 * @see org.apache.logging.log4j.LogManager
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log4j2
public final class TestException {

  /**
   * Parameterized test method that verifies the behavior of various exception classes.
   *
   * <p>This test method performs the following checks for each exception class:
   * <ul>
   *     <li>Ensures that the exception class has at least one constructor that takes a {@code String} argument.</li>
   *     <li>Creates an instance of the exception using the constructor with a {@code String} argument.</li>
   *     <li>Verifies that the instance of the exception is not {@code null}.</li>
   *     <li>Confirms that the exception can be correctly thrown and caught.</li>
   * </ul>
   * </p>
   *
   * <p>Logs the class of the exception being tested and verifies that the necessary conditions are met.</p>
   *
   * @param exceptionClass the exception class to be tested. It must extend {@code Exception}.
   */
  @ParameterizedTest
  @MethodSource("exceptionClasses")
  @DisplayName("Test exceptions.")
  void testHashAlgorithm(@NotNull final Class<? extends Exception> exceptionClass) {
    log.info("Testing exception={}.", exceptionClass);

    // Ensure that the exception class has at least one constructor that takes a String argument
    Assertions.assertTrue(exceptionClass.getConstructors().length > 0);

    Assertions.assertDoesNotThrow(() -> {
      final Exception exception =
          exceptionClass.getConstructor(String.class).newInstance("Test");

      // Check if the exception instance is not null and throw it to verify behavior
      Assertions.assertNotNull(exception);
      Assertions.assertThrows(exceptionClass, () -> {
        throw exception;
      });
    });
  }

  /**
   * Provides a stream of exception classes to be tested by the {@code testHashAlgorithm} method.
   *
   * <p>The stream includes various custom exception classes to ensure that they all meet the expected criteria.</p>
   *
   * @return a stream of exception classes to be tested.
   */
  Stream<Class<? extends Exception>> exceptionClasses() {
    return Stream.of(
        EntityAlreadyPresentException.class,
        EntityNotPresentException.class,
        FormatMismatchException.class,
        PasswordException.class,
        PasswordRequirementMismatchException.class,
        RabbitConnectionException.class
    );
  }
}
