package de.hdm.photon.webserver.logic.security;

import de.hdm.photon.schema.exception.PasswordException;
import jakarta.validation.constraints.NotNull;
import org.jetbrains.annotations.Nullable;


public interface IPasswordManager {

  @NotNull
  boolean match() throws PasswordException;

  @NotNull
  boolean store(@Nullable final String passphrase) throws PasswordException;
}
