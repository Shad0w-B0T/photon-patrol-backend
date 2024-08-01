package de.hdm.photon.schema.hardware;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a hardware token used for permissions verification.
 */
public interface IHardwareToken {

  /**
   * @return the value of the key.
   */
  @Schema(example="a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", description="Token to verify if the permissions fit.")
  @NotNull
  String token();

}
