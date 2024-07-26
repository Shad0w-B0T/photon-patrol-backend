package de.hdm.photon.webserver.logic.security;

import de.hdm.photon.schema.security.IPasswordRequirement;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Concrete implementation of {@link AbstractPasswordRequirementManager} for managing password requirements.
 */
public final class PasswordRequirementManager extends AbstractPasswordRequirementManager {
  /**
   * Constructs a PasswordRequirementManager with the specified password requirement.
   *
   * @param passwordRequirement the password requirement to be enforced
   * @throws NullPointerException if {@code passwordRequirement} is {@code null}
   */
  public PasswordRequirementManager(@Autowired @Nullable IPasswordRequirement passwordRequirement) {
    super(passwordRequirement);
  }
}
