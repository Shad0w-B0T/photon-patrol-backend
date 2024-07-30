package de.hdm.photon.webserver.logic.user;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public final class UserManager extends AbstractUserManager {

  @Autowired
  public UserManager(@Nullable IUserHelper userHelper) {
    super(userHelper);
  }
}
