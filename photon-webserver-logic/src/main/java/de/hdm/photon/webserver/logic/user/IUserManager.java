package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.schema.user.IUser;
import org.springframework.security.provisioning.UserDetailsManager;


/**
 * Interface for user management operations, extending both Spring Security's {@link UserDetailsManager}
 * and custom user access functionalities.
 * <p>
 * This interface combines user management functionalities with additional user access methods.
 * It provides operations for managing user details and accessing persistent user data.
 */
public interface IUserManager extends UserDetailsManager, IUserAccess<IUser> {
}
