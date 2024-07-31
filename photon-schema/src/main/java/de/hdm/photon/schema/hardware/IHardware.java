package de.hdm.photon.schema.hardware;

import de.hdm.photon.schema.entity.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.Nullable;

import java.util.Date;


/**
 * Represents hardware details, including MAC address, token status, and timestamps for when the hardware and MAC address were added.
 * This interface extends {@link Identifiable} for a unique identifier and includes {@link IHardwareAddress} and {@link IHardwareToken}
 * for accessing hardware address and token information.
 */
@Schema(implementation=ImmutableHardware.class)
public interface IHardware extends Identifiable, IHardwareAddress, IHardwareToken {

  /**
   * @return the time when the hardware was added.
   */
  @Schema(example="2024-04-20T13:37:00", description="The date when the hardware was added.")
  @Nullable
  Date hardwareAdded();

  /**
   * @return the time when the mac was added.
   */
  @Schema(example="2024-04-20T13:37:00", description="The date when the MAC address first was added.")
  @Nullable
  Date macAdded();

  /**
   * @return true if the key is currently being used.
   */
  @Schema(example="true", description="If the key is not used it should be false. Therefore it is equivalent to the lowest permission level.")
  Boolean tokenIsActive();

}
