package de.hdm.photon.schema.user;

import org.jetbrains.annotations.NotNull;

/**
 * Immutable implementation of the {@link IUser} interface.
 * This record represents a user with an id, username, password, and salt.
 */
public record ImmutableUser(long id,
                            @NotNull String username,
                            @NotNull String password,
                            @NotNull String salt
) implements IUser {
}
