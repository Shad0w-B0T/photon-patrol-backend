package de.hdm.photon.schema.hardware;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Objects;


/**
 * Factory class for creating and converting hardware objects.
 * This class provides methods to create new hardware instances and
 * to convert an existing logging interface into a record.
 */
@Log4j2
public final class HardwareFactory {
  /**
   * This method creates a new hardware object with the given parameters.
   *
   * @param id            unique id of the hardware object.
   * @param token         value to verify permissions.
   * @param macAddress    value of the mac address.
   * @param hardwareAdded date when the hardware was added.
   * @param macAdded      date when the mac address first was added.
   * @param tokenIsActive value whether the token is active or not.
   * @return new hardware interface.
   */
  public static @NotNull IHardware createHardware(final long id,
                                                  @Nullable String token,
                                                  @Nullable String macAddress,
                                                  @Nullable Date hardwareAdded,
                                                  @Nullable Date macAdded,
                                                  @Nullable Boolean tokenIsActive) {
    Objects.requireNonNull(token);
    Objects.requireNonNull(hardwareAdded);

    final IHardware hardware = new ImmutableHardware(id, token, macAddress, hardwareAdded, macAdded, tokenIsActive);
    log.debug("The hardware object with the id: {} was successfully created.", hardware.id());
    return hardware;
  }


  /**
   * This method takes a hardware interface and converts it into a record.
   *
   * @param hardware interface
   * @return new hardware record.
   */
  public static @NotNull IHardware convertToRecord(@Nullable final IHardware hardware) {
    Objects.requireNonNull(hardware);
    log.debug("The hardware interface {} was successfully converted to a record.", hardware);
    return HardwareFactory.createHardware(
        hardware.id(),
        hardware.token(),
        hardware.macAddress(),
        hardware.hardwareAdded(),
        hardware.macAdded(),
        hardware.tokenIsActive()
    );
  }

}
