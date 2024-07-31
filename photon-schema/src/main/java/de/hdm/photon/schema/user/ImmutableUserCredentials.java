package de.hdm.photon.schema.user;

import org.jetbrains.annotations.NotNull;


/**
 * Immutable implementation of the {@link IUserCredentials} interface.
 * This record holds the username and password for a user.
 */
public record ImmutableUserCredentials(@NotNull String username,
                                       @NotNull String password
) implements IUserCredentials {
}
