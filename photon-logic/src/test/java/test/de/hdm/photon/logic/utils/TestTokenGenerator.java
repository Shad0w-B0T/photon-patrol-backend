package test.de.hdm.photon.logic.utils;

import de.hdm.photon.logic.utils.TokenGenerator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public final class TestTokenGenerator {
  @Test
  @DisplayName("Test random string method positive.")
  public void testRandomStringPositive() {
    final String string = TokenGenerator.randomString(64);

    Assertions.assertNotNull(string);
    Assertions.assertEquals(string.length(), 64);

    Assertions.assertEquals(TokenGenerator.randomString(0), "");
  }

  @Test
  @DisplayName("Test random string method negative.")
  public void testRandomStringNegative() {
    Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> TokenGenerator.randomString(-1));
  }
}
