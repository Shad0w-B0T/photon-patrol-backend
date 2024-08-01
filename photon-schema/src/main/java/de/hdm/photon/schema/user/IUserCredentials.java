package de.hdm.photon.schema.user;

import org.jetbrains.annotations.NotNull;


/**
 * Interface representing user credentials.
 * <p>
 * This interface provides a contract for user credentials which includes a username and password.
 * Implementations of this interface should provide methods to retrieve these credentials.
 * </p>
 */
public interface IUserCredentials {


  /**
   * Gets the username associated with these credentials.
   *
   * @return the username, never {@code null}.
   */
  @NotNull String username();

  /**
   * Gets the password associated with these credentials.
   *
   * @return the password, never {@code null}.
   */
  @NotNull String password();

}
