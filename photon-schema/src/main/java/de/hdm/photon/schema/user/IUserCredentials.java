package de.hdm.photon.schema.user;

import org.jetbrains.annotations.NotNull;


/**
 * Interface representing user credentials with username and password.
 */
public interface IUserCredentials {

  @NotNull String username();

  @NotNull String password();

}
