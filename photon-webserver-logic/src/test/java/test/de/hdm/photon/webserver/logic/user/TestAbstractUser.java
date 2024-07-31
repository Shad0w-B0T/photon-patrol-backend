package test.de.hdm.photon.webserver.logic.user;

import de.hdm.photon.database.postgresql.user.UserRepository;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.user.IUser;
import de.hdm.photon.webserver.logic.user.IUserAccess;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.de.hdm.photon.AbstractSpringTest;
import test.de.hdm.photon.TestField;


/**
 * Abstract base class for testing user-related functionality.
 * It uses a generic type for user operations and defines common test scenarios
 * for user access methods such as creation, retrieval, and deletion.
 * <p>
 * This class is meant to be extended by concrete test classes that specify the
 * user type and implement the {@link #userAccess()} method to provide the
 * specific implementation of user access operations.
 * </p>
 *
 * @param <USER> the type of user entity being tested, extending {@link IUser}.
 */
@Disabled
public abstract class TestAbstractUser<USER extends IUser> extends AbstractSpringTest {

  @Autowired
  private UserRepository userRepository;

  /**
   * Provides the implementation of user access operations.
   *
   * @return an instance of {@link IUserAccess} for managing users.
   */
  protected abstract @NotNull IUserAccess<USER> userAccess();

  /**
   * Cleans up the user repository before each test by deleting all user entries.
   */
  @BeforeEach
  public void cleanUp() {
    this.userRepository.deleteAll();
  }

  /**
   * Tests the retrieval of persistent users.
   * <p>
   * Verifies that no users exist initially, then adds a user and confirms that
   * the user list is no longer empty.
   * </p>
   */
  @Test
  @DisplayName("Test get users")
  public void testGetUsers() {
    Assertions.assertTrue(this.userAccess().getPersistentUsers().isEmpty());
    // Add user
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().createPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
    Assertions.assertFalse(this.userAccess().getPersistentUsers().isEmpty());
  }

  /**
   * Tests the lookup of users by username.
   * <p>
   * Verifies that looking up a user with null username throws {@link NullPointerException},
   * and looking up a non-existent user throws {@link EntityNotPresentException}.
   * After adding a user, confirms that looking up the user does not throw an exception.
   * </p>
   */
  @Test
  @DisplayName("Test lookup user and password")
  public void testLookUpUser() {
    // Negative case
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.userAccess().lookupPersistentUser(null);
    });

    // Positive case
    Assertions.assertThrows(EntityNotPresentException.class, () -> {
      this.userAccess().lookupPersistentUser(TestField.USERNAME);
    });
    // Add user
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().createPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().lookupPersistentUser(TestField.USERNAME);
    });
  }

  /**
   * Tests the lookup of users by username and password.
   * <p>
   * Verifies that looking up a user with null username and password throws {@link NullPointerException},
   * and looking up a non-existent user with given credentials throws {@link EntityNotPresentException}.
   * After adding a user, confirms that looking up the user with correct credentials succeeds and
   * looking up with incorrect credentials fails.
   * </p>
   */
  @Test
  @DisplayName("Test lookup user and password")
  public void testLookUpUserAndPassword() {
    // Negative case
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.userAccess().lookupPersistentUser(null, null);
    });

    // Positive case
    Assertions.assertThrows(EntityNotPresentException.class, () -> {
      this.userAccess().lookupPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
    // Add user
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().createPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
    Assertions.assertDoesNotThrow(() -> {
      Assertions.assertTrue(this.userAccess().lookupPersistentUser(TestField.USERNAME, TestField.PASSWORD));
    });
    Assertions.assertDoesNotThrow(() -> {
      Assertions.assertFalse(this.userAccess().lookupPersistentUser(TestField.USERNAME, "other_pass"));
    });
  }

  /**
   * Tests the creation of users.
   * <p>
   * Verifies that creating a user with null username or password throws {@link NullPointerException},
   * and that creating a user with valid credentials does not throw exceptions.
   * After creation, confirms that the user can be looked up successfully.
   * </p>
   */
  @Test
  @DisplayName("Test create user")
  public void testCreateUser() {
    // Negative cases
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.userAccess().createPersistentUser(null, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.userAccess().createPersistentUser(TestField.USERNAME, null);
    });
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.userAccess().createPersistentUser(null, TestField.PASSWORD);
    });

    Assertions.assertThrows(EntityNotPresentException.class, () -> {
      this.userAccess().lookupPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
    // Add user
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().createPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().lookupPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
  }

  /**
   * Tests the deletion of users.
   * <p>
   * Verifies that deleting a user with null username throws {@link NullPointerException},
   * and confirms that deleting an existing user is successful.
   * After deletion, confirms that the user cannot be found.
   * </p>
   */
  @Test
  @DisplayName("Test delete user")
  public void testDeleteUser() {
    // Negative case
    Assertions.assertThrows(NullPointerException.class, () -> {
      this.userAccess().deletePersistentUser(null);
    });

    // Positive case
    // Add user
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().createPersistentUser(TestField.USERNAME, TestField.PASSWORD);
    });
    Assertions.assertDoesNotThrow(() -> {
      this.userAccess().deletePersistentUser(TestField.USERNAME);
    });
    Assertions.assertThrows(EntityNotPresentException.class, () -> {
      this.userAccess().deletePersistentUser(TestField.USERNAME);
    });
  }
}