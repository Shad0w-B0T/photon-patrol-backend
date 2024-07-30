package de.hdm.photon.schema.user;

import org.jetbrains.annotations.NotNull;


public record ImmutableUserCredentials(@NotNull String username,
                                       @NotNull String password) implements IUserCredentials {
}
