package de.hdm.photon.schema.hardware;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Schema(implementation = HardwareRecord.class)
public interface IHardware {

  /**
   * @return the id of the object.
   */
  @Schema(example = "1", description = "Unique identifier of an object.")
  long id();

  /**
   * @return the value of the key.
   */
  @Schema(example = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", description = "Key to verify if the permissions fit.")
  @NotNull String key();

  /**
   * @return the value of the MAC-Address.
   */
  @Schema(example = "A1:B4:C5:C1:DD:3E", description = "The MAC address of the connected hardware.")
  @Nullable String macAddress();

  /**
   * @return the time when the hardware was added.
   */
  @Schema(example = "2024-04-20T13:37:00", description = "The date when the hardware was added.")
  @Nullable Date hardwareAdded();

  /**
   * @return the time when the mac was added.
   */
  @Schema(example = "2024-04-20T13:37:00", description = "The date when the MAC address first was added.")
  @Nullable Date macAdded();

  /**
   * @return true if the key is currently being used.
   */
  @Schema(example = "true", description = "If the key is not used it should be false. Therefore it is equivalent to the lowest permission level.")
  @Nullable boolean keyIsActive();

}
