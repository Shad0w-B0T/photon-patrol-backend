package de.hdm.photon.schema.hardware;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public interface IHardwareToken {

  /**
   * @return the value of the key.
   */
  @Schema(example="a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", description="Token to verify if the permissions fit.")
  @NotNull
  String token();

}
