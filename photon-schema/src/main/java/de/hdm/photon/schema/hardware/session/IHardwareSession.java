package de.hdm.photon.schema.hardware.session;

import de.hdm.photon.schema.entity.Identifiable;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;
import java.util.Date;


@Schema(implementation = ImmutableHardwareSession.class)
public interface IHardwareSession extends Identifiable {

  /**
   * @return the id of the object.
   */
  @Schema(example = "1", description = "Unique identifier of an object.")
  long id();

  /**
   * @return the IP address.
   */
  @Schema(example = "127.0.0.1", description = "The ip address which was connected last.")
  @NotNull String ipAddress();

  /**
   * @return the time stamp when the IP was used/connected.
   */
  @Schema(example = "2024-04-20T13:37:00", description = "The exact time when the ip was connected.")
  @NotNull Date timestamp();

}
