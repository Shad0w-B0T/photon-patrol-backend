package test.de.hdm.photon.webserver.logic.user;

import de.hdm.photon.schema.user.IUser;
import de.hdm.photon.webserver.logic.user.IUserAccess;
import de.hdm.photon.webserver.logic.user.IUserManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


/**
 * Integration test for {@link IUserManager} implementation.
 * <p>
 * This test class extends {@link TestAbstractUser} to provide concrete test scenarios
 * for user access methods using {@link IUser} as the user type. It uses Spring Boot
 * test annotations to set up the application context and ensure that the context is reset
 * before each test method.
 * </p>
 */
@SpringBootTest
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class TestUserManager extends TestAbstractUser<IUser> {

  @Autowired
  private IUserManager userManager;

  /**
   * Provides the implementation of user access operations by returning the {@link IUserManager} instance.
   *
   * @return an instance of {@link IUserManager} for managing users.
   */
  @Override
  protected @NotNull IUserAccess<IUser> userAccess() {
    return this.userManager;
  }
}
