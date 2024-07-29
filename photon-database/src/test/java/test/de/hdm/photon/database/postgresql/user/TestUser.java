package test.de.hdm.photon.database.postgresql.user;

import de.hdm.photon.database.postgresql.user.UserEntity;
import de.hdm.photon.database.postgresql.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import test.de.hdm.photon.AbstractSpringTest;


@DataJpaTest // Only scans for @Entity and @Repository and store it to the context
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD) // Build new context before each test
@AutoConfigureTestDatabase(replace=Replace.NONE)
public final class TestUser extends AbstractSpringTest {

  @Autowired
  private UserRepository userRepository;

  private String user;
  private String password;
  private String newPassword;
  private String salt;

  @BeforeEach
  public void setUp() {
    this.userRepository.deleteAll();

    this.user = "user";
    this.password = "pass";
    this.newPassword = "newpass";
    this.salt = "123";
  }

  @Test
  @DisplayName("Create UserEntity")
  public void createUser() {
    // Given
    final UserEntity userEntity = new UserEntity(this.user, this.password, this.salt);

    // When
    final UserEntity savedUser = this.userRepository.save(userEntity);

    // Then
    Assertions.assertNotNull(savedUser);
    Assertions.assertEquals(savedUser.username(), this.user);
    Assertions.assertEquals(savedUser.password(), this.password);
    Assertions.assertEquals(savedUser.salt(), this.salt);
  }

  @Test
  @DisplayName("Get UserEntity")
  public void getUser() {
    // Given
    final UserEntity userEntity = new UserEntity(this.user, this.password, this.salt);
    this.userRepository.save(userEntity);

    // When
    final UserEntity retrievedUser = this.userRepository
        .findById(userEntity.id())
        .orElse(null);

    // Then
    Assertions.assertNotNull(retrievedUser);
    Assertions.assertEquals(retrievedUser.username(), this.user);
    Assertions.assertEquals(retrievedUser.password(), this.password);
    Assertions.assertEquals(retrievedUser.salt(), this.salt);
  }

  @Test
  @DisplayName("Update UserEntity")
  public void updateUser() {
    // Given
    final UserEntity userEntity = new UserEntity(this.user, this.password, this.salt);
    final UserEntity savedUser = this.userRepository.save(userEntity);

    // When
    savedUser.password(this.newPassword);
    this.userRepository.save(savedUser);
    final UserEntity updatedUser = this.userRepository
        .findById(userEntity.id())
        .orElse(null);


    // Then
    Assertions.assertNotNull(updatedUser);
    Assertions.assertEquals(updatedUser.password(), this.newPassword);
  }

  @Test
  @DisplayName("Delete UserEntity")
  public void deleteUser() {
    // Given
    final UserEntity userEntity = new UserEntity(this.user, this.password, this.salt);
    final UserEntity savedUser = this.userRepository.save(userEntity);

    // When
    this.userRepository.delete(savedUser);
    final UserEntity deleteUser = this.userRepository
        .findById(userEntity.id())
        .orElse(null);

    // Then
    Assertions.assertNull(deleteUser);
  }
}
