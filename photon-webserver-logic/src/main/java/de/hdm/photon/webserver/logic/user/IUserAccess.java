package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.user.IUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * Interface for user access operations.
 *
 * @param <USER> The type of user that this interface will manage.
 */
public interface IUserAccess<USER extends IUser> {

  /**
   * Retrieves all persistent users.
   *
   * @return A list of all persistent users.
   */
  @NotNull
  List<USER> getPersistentUsers();

  /**
   * Looks up a persistent user by username.
   *
   * @param username The username of the user to look up.
   * @return The user associated with the given username.
   * @throws EntityNotPresentException If no user with the specified username is found.
   */
  @NotNull
  USER lookupPersistentUser(@Nullable final String username) throws EntityNotPresentException;

  /**
   * Looks up a persistent user by username and password.
   *
   * @param username The username of the user to look up.
   * @param password The password of the user to look up.
   * @return True if a user with the specified username and password is found, otherwise false.
   * @throws EntityNotPresentException If no user with the specified username is found.
   */
  boolean lookupPersistentUser(@Nullable final String username,
                               @Nullable final String password) throws EntityNotPresentException;

  /**
   * Creates a new persistent user.
   *
   * @param username The username of the new user.
   * @param password The password of the new user.
   * @return The newly created user.
   * @throws EntityAlreadyPresentException If a user with the specified username already exists.
   * @throws FormatMismatchException If the username or password format is incorrect.
   */
  @NotNull
  USER createPersistentUser(@Nullable final String username,
                            @Nullable final String password)
      throws EntityAlreadyPresentException, FormatMismatchException;

  /**
   * Deletes a persistent user by username.
   *
   * @param username The username of the user to delete.
   * @return The deleted user.
   * @throws EntityNotPresentException If no user with the specified username is found.
   */
  @NotNull
  USER deletePersistentUser(@Nullable final String username) throws EntityNotPresentException;
}