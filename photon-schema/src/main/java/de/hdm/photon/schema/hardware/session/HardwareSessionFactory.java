package de.hdm.photon.schema.hardware.session;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Objects;


/**
 * Factory class for creating and converting hardware session objects.
 * This class provides methods to create new hardware session instances and
 * to convert an existing logging interface into a record.
 */
@Log4j2
public final class HardwareSessionFactory {

  /**
   * This method creates a new logging object with the given parameters.
   *
   * @param id            unique id of the hardware object.
   * @param ipAddress     value of the ip address.
   * @param timestampOfIp date when the ip address was used.
   * @return new logging interface.
   */
  public static @NotNull IHardwareSession createHardware(final long id,
                                                         @Nullable String ipAddress,
                                                         @Nullable Date timestampOfIp) {
    Objects.requireNonNull(ipAddress);
    Objects.requireNonNull(timestampOfIp);

    final IHardwareSession logging = new ImmutableHardwareSession(id, ipAddress, timestampOfIp);
    log.debug("The logging object with the id: {} was successfully created.", logging.id());
    return logging;
  }

  /**
   * This method takes a logging interface and converts it into a record.
   *
   * @param hardwareSession interface
   * @return new logging record.
   */
  public static @NotNull IHardwareSession convertToRecord(@Nullable final IHardwareSession hardwareSession
  ) {
    Objects.requireNonNull(hardwareSession);
    log.debug("The logging interface {} was successfully converted to a record.", hardwareSession.id());
    return HardwareSessionFactory.createHardware(
        hardwareSession.id(),
        hardwareSession.ipAddress(),
        hardwareSession.timestamp()
    );
  }

}
