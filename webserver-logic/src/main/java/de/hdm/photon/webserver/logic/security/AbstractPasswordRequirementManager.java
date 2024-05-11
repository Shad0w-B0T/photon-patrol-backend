package de.hdm.photon.webserver.logic.security;

import de.hdm.photon.schema.exception.PasswordRequirementMismatchException;
import de.hdm.photon.schema.security.IPasswordRequirement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * Abstract class for managing password requirements.
 */
public abstract class AbstractPasswordRequirementManager implements IPasswordRequirementManager {
  /**
   * The password requirement to be enforced.
   */
  private final @NotNull IPasswordRequirement passwordRequirement;

  /**
   * Constructs an AbstractPasswordRequirementManager with the specified password requirement.
   *
   * @param passwordRequirement the password requirement to be enforced
   * @throws NullPointerException if {@code passwordRequirement} is {@code null}
   */
  protected AbstractPasswordRequirementManager(@Nullable final IPasswordRequirement passwordRequirement) {
    //Null check
    Objects.requireNonNull(passwordRequirement);

    this.passwordRequirement = passwordRequirement;
  }

  /**
   * Checks if the provided password meets the requirements.
   *
   * @param password the password to be checked
   * @return {@code true} if the password meets the requirements, {@code false} otherwise
   */
  @Override
  public boolean allowed(@Nullable String password) {
    try {
      return this.allowedImplementation(password);
    } catch (final PasswordRequirementMismatchException exception) {
      return false;
    }
  }

  /**
   * Checks if the provided password meets the requirements. Throws a {@link PasswordRequirementMismatchException}
   * if the password does not meet the requirements.
   *
   * @param password the password to be checked
   * @throws PasswordRequirementMismatchException if the password does not meet the requirements
   */
  @Override
  public void allowedElseThrow(@Nullable String password) throws PasswordRequirementMismatchException {
    this.allowedImplementation(password);
  }

  /**
   * Implementation of password requirement checking.
   *
   * @param password the password to be checked
   * @return {@code true} if the password meets the requirements
   * @throws PasswordRequirementMismatchException if the password does not meet the requirements
   */
  private boolean allowedImplementation(@Nullable final String password) throws PasswordRequirementMismatchException {
    //Null check
    Objects.requireNonNull(password);

    final int length = password.length();

    //Check if password is long enough
    if (length < this.passwordRequirement.min()) {
      throw new PasswordRequirementMismatchException(
          "Password is too short, minimum " + this.passwordRequirement.min() + ". Characters were given " + length + ".");
    }

    //Check if password is in range
    if (length > this.passwordRequirement.max()) {
      throw new PasswordRequirementMismatchException(
          "Password is too long, maximum " + this.passwordRequirement.max() + ". Characters were given " + length + ".");
    }

    //Check if password matches the pattern
    if (!password.matches(this.passwordRequirement.pattern())) {
      throw new PasswordRequirementMismatchException("Password does not match the requirement.");
    }

    //Password meets all requirements
    return true;
  }
}