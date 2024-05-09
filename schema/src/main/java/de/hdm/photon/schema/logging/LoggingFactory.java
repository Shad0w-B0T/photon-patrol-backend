package de.hdm.photon.schema.logging;

import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Objects;


@Log4j2
public final class LoggingFactory {

  /**
   * This method creates a new logging object with the given parameters.
   *
   * @param id            unique id of the hardware object.
   * @param ipAddress     value of the ip address.
   * @param timestampOfIp date when the ip address was used.
   * @return new logging interface.
   */
  public static @NotNull ILogging createHardware(final long id,
                                                 @Nullable String ipAddress,
                                                 @Nullable Date timestampOfIp) {
    Objects.requireNonNull(ipAddress);
    Objects.requireNonNull(timestampOfIp);

    final ILogging logging = new LoggingRecord(id, ipAddress, timestampOfIp);
    log.debug("The logging object with the id: {} was successfully created.", logging.id());
    return logging;
  }

  /**
   * This method takes a logging interface and converts it into a record.
   *
   * @param logging interface
   * @return new logging record.
   */
  public static @NotNull ILogging convertToRecord(@Nullable final ILogging logging) {
    Objects.requireNonNull(logging);
    log.debug("The logging interface {} was successfully converted to a record.", logging);
    return LoggingFactory.createHardware(
        logging.id(),
        logging.ipAddress(),
        logging.timestampOfIp()
    );
  }

}
