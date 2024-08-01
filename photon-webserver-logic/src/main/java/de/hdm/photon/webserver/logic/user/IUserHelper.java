package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.database.postgresql.user.UserEntity;


/**
 * Interface for user helper operations, extending basic user access functionalities.
 * This interface provides additional helper methods for user management.
 */
public interface IUserHelper extends IUserAccess<UserEntity> {
}