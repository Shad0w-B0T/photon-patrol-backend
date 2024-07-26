package de.hdm.photon.webserver.logic.user;

import de.hdm.photon.schema.user.IUser;
import org.springframework.security.provisioning.UserDetailsManager;


public interface IUserManager extends UserDetailsManager, IUserAccess<IUser> {
}
