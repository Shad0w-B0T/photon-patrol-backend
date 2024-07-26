package de.hdm.photon.schema.user;

import de.hdm.photon.schema.entity.Identifiable;
import org.jetbrains.annotations.NotNull;


public interface IUser extends Identifiable {

  @NotNull String username();

  @NotNull String password();

  @NotNull String salt();

}
