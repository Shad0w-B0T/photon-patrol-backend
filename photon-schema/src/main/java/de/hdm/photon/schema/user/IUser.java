package de.hdm.photon.schema.user;

import de.hdm.photon.schema.entity.Identifiable;
import org.jetbrains.annotations.NotNull;


/**
 * Interface representing user entity that combines user credentials and an identifiable entity
 * This interface provides methods to retrieve the users username, password and salt.
 */

public interface IUser extends IUserCredentials, Identifiable {


  @NotNull String salt();

}
