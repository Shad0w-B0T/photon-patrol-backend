package de.hdm.photon.schema.user;


import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * Factory class for creating and converting IUserCredentials objects.
 */
@Log4j2
public final class UserCredentialsFactory {
  /**
   * Creates a new IUserCredentials instance with the specified username and password.
   *
   * @param username The username of the user, must not be null.
   * @param password The password of the user, must not be null.
   * @return The created IUserCredentials instance.
   * @throws NullPointerException if either the username or password is null.
   */
  public static @NotNull IUserCredentials createUserCredentials(@Nullable final String username,
                                                                @Nullable final String password) {
    // Null check
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    final IUserCredentials credentials = new ImmutableUserCredentials(username, password);
    log.debug("The user object with the username: {} was successfully created.", username);
    return credentials;
  }

  /**
   * Converts an IUserCredentials instance to a new IUserCredentials record.
   *
   * @param userCredentials The IUserCredentials instance to be converted, must not be null.
   * @return A new IUserCredentials record with the same properties as the input userCredentials.
   * @throws NullPointerException if the userCredentials parameter is null.
   */
  public static @NotNull IUserCredentials convertToRecord(@Nullable final IUserCredentials userCredentials) {
    // Null check
    Objects.requireNonNull(userCredentials);
    log.debug("The user interface {} was successfully converted to a record.", userCredentials.username());
    return UserCredentialsFactory.createUserCredentials(
        userCredentials.username(),
        userCredentials.password()
    );
  }
}
