package de.hdm.photon.schema.security;

import org.jetbrains.annotations.NotNull;


/**
 * Interface representing password requirements.
 */
public interface IPasswordRequirement {
  /**
   * Retrieves the maximum length of the password.
   *
   * @return the maximum length of the password.
   */
  int max();

  /**
   * Retrieves the minimum length of the password.
   *
   * @return the minimum length of the password.
   */
  int min();

  /**
   * Retrieves the pattern that the password must match.
   *
   * @return the pattern that the password must match.
   */
  @NotNull String pattern();
}
