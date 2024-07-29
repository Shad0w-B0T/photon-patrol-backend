package test.de.hdm.photon.schema.security;

import de.hdm.photon.schema.security.IPasswordRequirement;
import de.hdm.photon.schema.security.ImmutablePasswordRequirement;
import de.hdm.photon.schema.security.PasswordRequirementFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * A test class for {@code PasswordRequirementFactory} and {@code ImmutablePasswordRequirement}.
 */
public final class TestPasswordRequirement {

  /**
   * Tests the positive scenarios of {@code PasswordRequirementFactory}.
   */
  @Test
  @DisplayName("Testing Password Requirement Factory Positive")
  public void testPasswordRequirementFactoryPositive() {
    //Positive Tests
    Assertions.assertDoesNotThrow(
        () -> PasswordRequirementFactory.create(1, 1, ""));
    Assertions.assertDoesNotThrow(
        () -> PasswordRequirementFactory.create(50, 60, ""));

    //Swap Swap
    final IPasswordRequirement passwordRequirement = PasswordRequirementFactory.create(50, 60, "");
    Assertions.assertEquals(passwordRequirement.max(), 60);
    Assertions.assertEquals(passwordRequirement.min(), 50);

    Assertions.assertDoesNotThrow(() ->
        PasswordRequirementFactory.convert(new ImmutablePasswordRequirement(1, 1, "")));
  }

  /**
   * Tests the negative scenarios of {@code PasswordRequirementFactory}.
   */
  @Test
  @DisplayName("Testing Password Requirement Factory Negative")
  public void testPasswordRequirementFactoryNegative() {
    //Negative Tests
    Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> PasswordRequirementFactory.create(-1, 1, ""));
    Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> PasswordRequirementFactory.create(300, 1, ""));

    Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> PasswordRequirementFactory.create(1, -1, ""));
    Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> PasswordRequirementFactory.create(1, 300, ""));

    Assertions.assertThrows(NullPointerException.class,
        () -> PasswordRequirementFactory.create(1, 1, null));

    Assertions.assertThrows(NullPointerException.class,
        () -> PasswordRequirementFactory.convert(null));
  }

  /**
   * Tests the {@code ImmutablePasswordRequirement} implementation.
   */
  @Test
  @DisplayName("Testing Password Requirement Interface")
  public void testPasswordRequirementInterface() {
    final IPasswordRequirement passwordRequirement = new ImmutablePasswordRequirement(1, 2, "");

    //Positive Tests
    Assertions.assertInstanceOf(IPasswordRequirement.class, passwordRequirement);
    Assertions.assertEquals(passwordRequirement.max(), 1);
    Assertions.assertEquals(passwordRequirement.min(), 2);
    Assertions.assertEquals(passwordRequirement.pattern(), "");
  }
}

