package de.hdm.photon.schema.user;


import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


@Log4j2
public class UserFactory {

  public static @NotNull IUser createUser(final long id,
                                          @Nullable String username,
                                          @Nullable String password,
                                          @Nullable String salt) {
    //Null check
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);
    Objects.requireNonNull(salt);

    final IUser user = new ImmutableUser(id, username, password, salt);
    log.debug("The user object with the id: {} was successfully created.", user.id());
    return user;
  }


  public static @NotNull IUser convertToRecord(@Nullable final IUser user) {
    //Null check
    Objects.requireNonNull(user);
    log.debug("The user interface {} was successfully converted to a record.", user);
    return UserFactory.createUser(
        user.id(),
        user.username(),
        user.password(),
        user.salt()
    );
  }

}
