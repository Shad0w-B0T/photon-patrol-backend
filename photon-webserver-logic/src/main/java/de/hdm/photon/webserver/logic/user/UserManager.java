package de.hdm.photon.webserver.logic.user;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Concrete implementation of {@link AbstractUserManager} for managing user-related operations.
 * <p>
 * This class extends {@link AbstractUserManager} and provides a specific implementation for
 * user management using the dependency-injected {@link IUserHelper}. It is marked as a Spring
 * {@link Component}, which allows it to be managed by the Spring container and injected wherever needed.
 */
@Component
public final class UserManager extends AbstractUserManager {
  /**
   * Constructs a new {@link UserManager} instance.
   *
   * @param userHelper the helper used for user-related operations. May be null.
   */
  @Autowired
  public UserManager(@Nullable IUserHelper userHelper) {
    super(userHelper);
  }
}
