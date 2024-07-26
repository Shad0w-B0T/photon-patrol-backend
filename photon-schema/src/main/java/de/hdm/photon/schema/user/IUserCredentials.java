package de.hdm.photon.schema.user;

import org.jetbrains.annotations.NotNull;


public interface IUserCredentials {

  @NotNull String username();

  @NotNull String password();

}
