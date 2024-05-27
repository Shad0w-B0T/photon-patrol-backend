package de.hdm.photon.schema.hardware;

import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Objects;


@Log4j2
public final class HardwareFactory {

  /**
   * This method creates a new hardware object with the given parameters.
   *
   * @param id            unique id of the hardware object.
   * @param key           value to verify permissions.
   * @param macAddress    value of the mac address.
   * @param hardwareAdded date when the hardware was added.
   * @param macAdded      date when the mac address first was added.
   * @param keyIsActive   value whether the key is active or not.
   * @return new hardware interface.
   */
  public static @NotNull IHardware createHardware(final long id,
                                                  @Nullable String key,
                                                  @Nullable String macAddress,
                                                  @Nullable Date hardwareAdded,
                                                  @Nullable Date macAdded,
                                                  @Nullable boolean keyIsActive) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(macAddress);
    Objects.requireNonNull(hardwareAdded);
    Objects.requireNonNull(macAdded);

    final IHardware hardware = new HardwareRecord(id, key, macAddress, hardwareAdded, macAdded, keyIsActive);
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
        hardware.keyIsActive()
    );
  }

}
