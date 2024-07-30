package test.de.hdm.photon.schema.user;

import de.hdm.photon.schema.user.IUserCredentials;
import de.hdm.photon.schema.user.ImmutableUserCredentials;
import de.hdm.photon.schema.user.UserCredentialsFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.de.hdm.photon.TestField;


/**
 * Test class for testing UserCredentials functionalities using the UserCredentialsFactory.
 */
public final class TestUserCredentials {

  /**
   * Tests the creation and integrity of user credentials.
   */
  @Test
  @DisplayName("Testing UserCredentials")
  public void testUserCredentials() {
    final IUserCredentials userCredentials = this.createUserCredentials();
    this.checkUserCredentialsIntegrity(userCredentials);
  }

  /**
   * Tests negative scenarios for UserCredentialsFactory methods.
   * Ensures that appropriate exceptions are thrown when invalid arguments are passed.
   */
  @Test
  @DisplayName("Testing UserCredentialsFactory negative")
  public void testUserCredentialsFactoryNegative() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      UserCredentialsFactory.createUserCredentials(null, null);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      UserCredentialsFactory.createUserCredentials(TestField.USERNAME, null);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      UserCredentialsFactory.convertToRecord(null);
    });
  }

  /**
   * Tests the conversion of user credentials to a record using the UserCredentialsFactory.
   * Ensures the integrity of the converted user credentials record.
   */
  @Test
  @DisplayName("Testing UserCredentialsFactory to-record")
  public void testUserCredentialsFactoryToRecord() {
    // Create user credentials instance
    final IUserCredentials userCredentials = this.createUserCredentials();
    this.checkUserCredentialsIntegrity(userCredentials);

    // Convert to record and check integrity
    final IUserCredentials recordUserCredentials = UserCredentialsFactory.convertToRecord(userCredentials);
    this.checkUserCredentialsIntegrity(recordUserCredentials);
  }

  /**
   * Creates a new IUserCredentials instance with predefined test fields.
   *
   * @return The created IUserCredentials instance.
   */
  private @NotNull IUserCredentials createUserCredentials() {
    return new ImmutableUserCredentials(TestField.USERNAME, TestField.PASSWORD);
  }

  /**
   * Checks the integrity of a given IUserCredentials instance.
   *
   * @param userCredentials The IUserCredentials instance to be checked.
   */
  private void checkUserCredentialsIntegrity(@Nullable final IUserCredentials userCredentials) {
    Assertions.assertNotNull(userCredentials);
    Assertions.assertEquals(userCredentials.username(), TestField.USERNAME);
    Assertions.assertEquals(userCredentials.password(), TestField.PASSWORD);
  }
}