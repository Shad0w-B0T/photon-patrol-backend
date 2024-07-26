package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.user.IUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;


public abstract class AbstractUserManager implements IUserManager {

  private final @NotNull IUserHelper userHelper;

  public AbstractUserManager(@Nullable final IUserHelper userHelper) {
    //Null check
    Objects.requireNonNull(userHelper);

    this.userHelper = userHelper;
  }

  @Override
  public @NotNull List<IUser> getPersistentUsers() {
    return this.userHelper.getPersistentUsers().stream().map(userEntity -> (IUser) userEntity).toList();
  }

  @Override
  public @NotNull IUser lookupPersistentUser(@Nullable String username) throws EntityNotPresentException {
    return this.userHelper.lookupPersistentUser(username);
  }

  @Override
  public boolean lookupPersistentUser(@Nullable String username,
                                      @Nullable String password) throws EntityNotPresentException {
    return this.userHelper.lookupPersistentUser(username, password);
  }

  @Override
  public @NotNull IUser createPersistentUser(@Nullable String username,
                                             @Nullable String password) throws EntityAlreadyPresentException {
    return this.userHelper.createPersistentUser(username, password);
  }

  @Override
  public @NotNull IUser deletePersistentUser(@Nullable String username) throws EntityNotPresentException {
    return this.userHelper.deletePersistentUser(username);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createUser(@Nullable final UserDetails user) {
    //Null check
    Objects.requireNonNull(user);

    try {
      this.createPersistentUser(user.getUsername(), user.getPassword());
    } catch (final EntityAlreadyPresentException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateUser(@Nullable final UserDetails user) {
    //Null check
    Objects.requireNonNull(user);

    throw new RuntimeException("Not implemented.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteUser(@Nullable final String username) {
    //Null check
    Objects.requireNonNull(username);

    try {
      this.deletePersistentUser(username);
    } catch (final EntityNotPresentException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changePassword(@Nullable final String oldPassword,
                             @Nullable final String newPassword) {
    //Null check
    Objects.requireNonNull(oldPassword);
    Objects.requireNonNull(newPassword);

    throw new RuntimeException("Not implemented.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean userExists(@Nullable final String username) {
    try {
      this.lookupPersistentUser(username);
      return true;
    } catch (final EntityNotPresentException exception) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserDetails loadUserByUsername(@Nullable final String username) throws UsernameNotFoundException {
    final IUser user;

    try {
      user = this.lookupPersistentUser(username);
    } catch (final EntityNotPresentException exception) {
      throw new UsernameNotFoundException("No user with name="+username+" found.");
    }

    return User.withUsername(user.username())
        .password(user.password())
        .build();
  }
}
