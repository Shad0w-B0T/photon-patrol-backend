package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.database.postgresql.user.UserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public final class UserHelper extends AbstractUserHelper {

  @Autowired
  public UserHelper(@Nullable UserRepository userRepository,
                    @Nullable PasswordEncoder passwordEncoder) {
    super(userRepository, passwordEncoder);
  }
}
