package de.hdm.photon.schema.hardware;

import de.hdm.photon.schema.entity.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Schema(implementation = HardwareRecord.class)
public interface IHardware extends Identifiable, IHardwareAddress, IHardwareToken {

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
