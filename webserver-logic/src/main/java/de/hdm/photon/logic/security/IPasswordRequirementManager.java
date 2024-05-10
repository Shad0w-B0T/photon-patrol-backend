package de.hdm.photon.logic.security;

import de.hdm.photon.schema.exception.PasswordRequirementMismatchException;
import org.jetbrains.annotations.Nullable;


/**
 * Interface for managing password requirements.
 */
public interface IPasswordRequirementManager {
  /**
   * Checks if the provided password meets the requirements.
   *
   * @param password the password to be checked
   * @return {@code true} if the password meets the requirements, {@code false} otherwise
   */
  boolean allowed(@Nullable final String password);

  /**
   * Checks if the provided password meets the requirements. Throws a {@link PasswordRequirementMismatchException}
   * if the password does not meet the requirements.
   *
   * @param password the password to be checked
   * @throws PasswordRequirementMismatchException if the password does not meet the requirements
   */
  void allowedElseThrow(@Nullable final String password) throws PasswordRequirementMismatchException;
}
