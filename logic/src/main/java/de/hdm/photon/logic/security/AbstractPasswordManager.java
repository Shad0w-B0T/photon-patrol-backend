package de.hdm.photon.logic.security;

import de.hdm.photon.schema.exception.PasswordException;
import org.jetbrains.annotations.Nullable;


public abstract class AbstractPasswordManager implements IPasswordManager {

  @Override
  public boolean match() throws PasswordException {
    return false;
  }

  @Override
  public boolean store(@Nullable String passphrase) throws PasswordException {
    return false;
  }
}
