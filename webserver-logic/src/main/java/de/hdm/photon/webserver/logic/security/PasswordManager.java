package de.hdm.photon.webserver.logic.security;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;


public final class PasswordManager extends AbstractPasswordManager {

  public PasswordManager(@Value("${app.security.hash}") @Nullable String hashName,
                         @Value("${app.security.passwordStorage}") @Nullable String passwordStorage) {
    super(hashName, passwordStorage);
  }
}
