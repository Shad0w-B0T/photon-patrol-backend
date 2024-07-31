package test.de.hdm.photon.logic.security.io;

import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for the {@link SecurityFilter} class.
 */
public final class SecurityFilterTest {
  /**
   * Tests that a {@link SecurityFilter} instance can be created without exceptions.
   */
  @Test
  @DisplayName("Test SecurityFilter Positive")
  public void testSecurityFilterPositive() {
    //Positive Tests
    Assertions.assertDoesNotThrow(() -> new SecurityFilter());
  }

  /**
   * Tests positive cases for the {@link SecurityFilter} class.
   * Verifies rule application for valid and invalid inputs.
   */
  @Test
  @DisplayName("Test SecurityFilter Positive Specific")
  public void testSecurityFilterPositiveSpecific() {
    //Positive Tests
    final ISecurityFilter securityFilter = new SecurityFilter();

    Assertions.assertDoesNotThrow(() -> {
      //String must be longer than 5.
      securityFilter.rule(String.class, string -> string.length()>5);
    });

    //Check rules negative
    Assertions.assertThrows(SecurityException.class, () -> {
      securityFilter.checkWithException("a".repeat(4));
    });
    Assertions.assertFalse(securityFilter.check("a".repeat(4)));

    //Check rules positive
    Assertions.assertDoesNotThrow(() -> {
      securityFilter.checkWithException("a".repeat(6));
    });
    Assertions.assertTrue(securityFilter.check("a".repeat(6)));
    Assertions.assertTrue(securityFilter.check((Object) ("a".repeat(6))));
  }

  /**
   * Tests negative cases for the {@link SecurityFilter} class.
   * Verifies handling of null inputs and invalid rule definitions.
   */
  @Test
  @DisplayName("Test SecurityFilter Negative")
  public void testSecurityFilterNegative() {
    //Negative Tests
    Assertions.assertThrows(NullPointerException.class, () -> new SecurityFilter(null));

    final ISecurityFilter securityFilter = new SecurityFilter();

    Assertions.assertThrows(NullPointerException.class, () -> {
      securityFilter.rule(null, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      securityFilter.checkWithException(null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      securityFilter.check(null);
    });
  }
}
