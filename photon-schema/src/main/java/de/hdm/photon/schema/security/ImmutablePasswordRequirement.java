package de.hdm.photon.schema.security;

import org.jetbrains.annotations.NotNull;


/**
 * An immutable implementation of the {@code IPasswordRequirement} interface.
 *
 * @param max     -> {@link IPasswordRequirement#max()}
 * @param min     -> {@link IPasswordRequirement#min()}
 * @param pattern .> {@link IPasswordRequirement#pattern()}
 */
public record ImmutablePasswordRequirement(
    int max,
    int min,
    @NotNull String pattern
) implements IPasswordRequirement {
}
