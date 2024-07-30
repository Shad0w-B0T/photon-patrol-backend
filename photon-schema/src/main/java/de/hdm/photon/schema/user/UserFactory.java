package de.hdm.photon.schema.user;


import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * Factory class for creating and converting IUser objects.
 */
@Log4j2
public final class UserFactory {
  /**
   * Creates a new IUser instance with the specified parameters.
   *
   * @param id       The unique identifier for the user.
   * @param username The username of the user, must not be null.
   * @param password The password of the user, must not be null.
   * @param salt     The salt used for hashing the password, must not be null.
   * @return The created IUser instance.
   * @throws NullPointerException if any of the non-null parameters are null.
   */
  public static @NotNull IUser createUser(final long id,
                                          @Nullable final String username,
                                          @Nullable final String password,
                                          @Nullable final String salt) {
    // Null check
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);
    Objects.requireNonNull(salt);

    final IUser user = new ImmutableUser(id, username, password, salt);
    log.debug("The user object with the id: {} was successfully created.", user.id());
    return user;
  }

  /**
   * Converts an IUser instance to a new IUser record.
   *
   * @param user The IUser instance to be converted, must not be null.
   * @return A new IUser record with the same properties as the input user.
   * @throws NullPointerException if the user parameter is null.
   */
  public static @NotNull IUser convertToRecord(@Nullable final IUser user) {
    // Null check
    Objects.requireNonNull(user);
    log.debug("The user interface {} was successfully converted to a record.", user);
    return UserFactory.createUser(
        user.id(),
        user.username(),
        user.password(),
        user.salt()
    );
  }
}
