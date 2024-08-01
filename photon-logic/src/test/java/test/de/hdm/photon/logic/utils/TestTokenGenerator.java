package test.de.hdm.photon.logic.utils;

import de.hdm.photon.logic.utils.TokenGenerator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for the TokenGenerator class.
 */
public final class TestTokenGenerator {

  /**
   * Tests the randomString method with a positive case.
   * Verifies that the method returns a non-null string of the correct length and handles zero length input.
   */
  @Test
  @DisplayName("Test random string method positive.")
  public void testRandomStringPositive() {
    final String string = TokenGenerator.randomString(64);

    Assertions.assertNotNull(string);
    Assertions.assertEquals(string.length(), 64);

    Assertions.assertEquals(TokenGenerator.randomString(0), "");
  }

  /**
   * Tests the randomString method with a negative case.
   * Verifies that the method throws an IllegalArgumentException when given a negative length.
   */
  @Test
  @DisplayName("Test random string method negative.")
  public void testRandomStringNegative() {
    Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> TokenGenerator.randomString(-1));
  }
}
