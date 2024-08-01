package test.de.hdm.photon.webserver.logic.user;

import de.hdm.photon.database.postgresql.user.UserEntity;
import de.hdm.photon.webserver.logic.user.IUserAccess;
import de.hdm.photon.webserver.logic.user.IUserHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


/**
 * Integration test for {@link IUserHelper} implementation.
 * <p>
 * This test class extends {@link TestAbstractUser} to provide concrete test scenarios
 * for user access methods using {@link UserEntity} as the user type. It uses Spring Boot
 * test annotations to set up the application context and ensure that the context is reset
 * before each test method.
 * </p>
 */
@SpringBootTest
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class TestUserHelper extends TestAbstractUser<UserEntity> {

  @Autowired
  private IUserHelper userHelper;

  /**
   * Provides the implementation of user access operations by returning the {@link IUserHelper} instance.
   *
   * @return an instance of {@link IUserHelper} for managing users.
   */
  @Override
  protected @NotNull IUserAccess<UserEntity> userAccess() {
    return this.userHelper;
  }
}
