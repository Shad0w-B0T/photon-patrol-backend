package de.hdm.photon.schema.user;

import de.hdm.photon.schema.entity.Identifiable;
import org.jetbrains.annotations.NotNull;


/**
 * Interface representing a user entity that combines user credentials with an identifiable entity.
 * <p>
 * This interface extends both {@link IUserCredentials} and {@link Identifiable}. It provides methods
 * to retrieve the user's username, password, and a salt value used for password hashing.
 * </p>
 */
public interface IUser extends IUserCredentials, Identifiable {

  /**
   * Gets the salt value associated with this user.
   * <p>
   * The salt is used in conjunction with the password to enhance security by mitigating certain types of
   * attacks, such as rainbow table attacks. It should never be {@code null}.
   * </p>
   *
   * @return the salt, never {@code null}.
   */
  @NotNull String salt();

}
