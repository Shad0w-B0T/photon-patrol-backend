package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.user.IUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface IUserAccess<USER extends IUser> {

  @NotNull
  List<USER> getPersistentUsers();

  @NotNull USER
  lookupPersistentUser(@Nullable final String username) throws EntityNotPresentException;

  boolean lookupPersistentUser(@Nullable final String username,
                               @Nullable final String password) throws EntityNotPresentException;

  @NotNull
  USER createPersistentUser(@Nullable final String username,
                            @Nullable final String password) throws EntityAlreadyPresentException;

  @NotNull
  USER deletePersistentUser(@Nullable final String username) throws EntityNotPresentException;


}
