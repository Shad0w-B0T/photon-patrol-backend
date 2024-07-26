package de.hdm.photon.schema.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * A factory class for creating instances of {@code IPasswordRequirement}.
 */
public final class PasswordRequirementFactory {
  /** The maximum allowed length for password requirements. */
  private final static int MAX_LENGTH = 256;

  /**
   * Creates a new {@code IPasswordRequirement} instance with the specified parameters.
   *
   * @param max     the maximum length requirement.
   * @param min     the minimum length requirement.
   * @param pattern the pattern requirement.
   * @return a new {@code IPasswordRequirement} instance.
   * @throws NullPointerException     if {@code pattern} is {@code null}.
   * @throws IndexOutOfBoundsException if {@code max} or {@code min} is out of bounds.
   */
  public static @NotNull IPasswordRequirement create(final int max,
                                                     final int min,
                                                     @Nullable final String pattern) {
    //Null checks
    Objects.requireNonNull(pattern);
    Objects.checkIndex(max, MAX_LENGTH);
    Objects.checkIndex(min, MAX_LENGTH);

    //Check for range.
    final int finalMax = Math.max(max, min);
    final int finalMin = Math.min(max, min);

    return new ImmutablePasswordRequirement(finalMax, finalMin, pattern);
  }

  /**
   * Converts an {@code IPasswordRequirement} instance to another instance.
   *
   * @param passwordRequirement the password requirement to convert.
   * @return a new {@code IPasswordRequirement} instance.
   * @throws NullPointerException if {@code passwordRequirement} is {@code null}.
   */
  public static @NotNull IPasswordRequirement convert(@Nullable final IPasswordRequirement passwordRequirement) {
    //Null check
    Objects.requireNonNull(passwordRequirement);

    return PasswordRequirementFactory.create(
        passwordRequirement.max(),
        passwordRequirement.min(),
        passwordRequirement.pattern());
  }
}
