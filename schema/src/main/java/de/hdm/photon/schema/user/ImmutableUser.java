package de.hdm.photon.schema.user;

import org.jetbrains.annotations.NotNull;


public record ImmutableUser(long id,
                            @NotNull String username,
                            @NotNull String password,
                            @NotNull String salt
) implements IUser {
}
