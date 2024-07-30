package test.de.hdm.photon.schema.user;

import de.hdm.photon.schema.user.IUser;
import de.hdm.photon.schema.user.ImmutableUser;
import de.hdm.photon.schema.user.UserFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.de.hdm.photon.TestField;

/**
 * Test class for testing User functionalities using the UserFactory.
 */
public final class TestUser {
  /**
   * Tests the creation and integrity of a user.
   */
  @Test
  @DisplayName("Testing User")
  public void testUser() {
    final IUser user = this.createUser();
    this.checkUserIntegrity(user);
  }

  /**
   * Tests negative scenarios for UserFactory methods.
   * Ensures that appropriate exceptions are thrown when invalid arguments are passed.
   */
  @Test
  @DisplayName("Testing UserFactory negative")
  public void testUserFactoryNegative() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      UserFactory.createUser(0, null, null, null);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      UserFactory.createUser(0, TestField.USERNAME, null, null);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      UserFactory.createUser(0, TestField.USERNAME, TestField.PASSWORD, null);
    });

    Assertions.assertThrows(NullPointerException.class, () -> {
      UserFactory.convertToRecord(null);
    });
  }

  /**
   * Tests the conversion of a user to a record using the UserFactory.
   * Ensures the integrity of the converted user record.
   */
  @Test
  @DisplayName("Testing UserFactory to-record")
  public void testUserFactoryToRecord() {
    // Create user instance
    final IUser user = this.createUser();
    this.checkUserIntegrity(user);

    // Convert to record and check integrity
    final IUser recordUser = UserFactory.convertToRecord(user);
    this.checkUserIntegrity(recordUser);
  }

  /**
   * Creates a new IUser instance with predefined test fields.
   *
   * @return The created IUser instance.
   */
  private @NotNull IUser createUser() {
    return new ImmutableUser(TestField.USER_ID,
        TestField.USERNAME,
        TestField.PASSWORD,
        TestField.SALT);
  }

  /**
   * Checks the integrity of a given IUser instance.
   *
   * @param user The IUser instance to be checked.
   */
  private void checkUserIntegrity(@Nullable final IUser user) {
    Assertions.assertNotNull(user);
    Assertions.assertEquals(user.id(), TestField.USER_ID);
    Assertions.assertEquals(user.username(), TestField.USERNAME);
    Assertions.assertEquals(user.password(), TestField.PASSWORD);
    Assertions.assertEquals(user.salt(), TestField.SALT);
  }
}