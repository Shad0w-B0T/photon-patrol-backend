package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.database.postgresql.user.UserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Concrete implementation of {@link AbstractUserHelper} for managing user-related operations.
 * <p>
 * This class extends {@link AbstractUserHelper} and provides a specific implementation
 * for user management using dependency-injected {@link UserRepository} and {@link PasswordEncoder}.
 * <p>
 * It is marked as a Spring {@link Component}, allowing it to be managed by the Spring container
 * and injected wherever needed.
 */
@Component
public final class UserHelper extends AbstractUserHelper {
  /**
   * Constructs a new {@link UserHelper} instance.
   *
   * @param userRepository  the repository used for user data access. Must not be null.
   * @param passwordEncoder the encoder used for password encryption. Must not be null.
   */
  @Autowired
  public UserHelper(@Nullable UserRepository userRepository,
                    @Nullable PasswordEncoder passwordEncoder) {
    super(userRepository, passwordEncoder);
  }
}
