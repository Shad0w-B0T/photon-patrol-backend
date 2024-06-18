package de.hdm.photon.logic.security.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public final class SecurityFilterTest {

  @Test
  @DisplayName("Test HeartbeatInfo Positive")
  public void testSecurityFilterPositive() {
    //Positive Tests
    Assertions.assertDoesNotThrow(() -> new SecurityFilter());
  }

  @Test
  @DisplayName("Test HeartbeatInfo Positive Specific")
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

  @Test
  @DisplayName("Test HeartbeatInfo Negative")
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
