package de.hdm.photon.webserver.logic.logging;

import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.logging.ILogging;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Log4j2
@Component
public class LoggingManager implements ILoggingAccess<ILogging> {

  @Override
  public @NotNull ILogging createLogging(long hardwareId,
                                         @Nullable String ipAddress,
                                         @Nullable Date timestampOfIp) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return null;
  }

  @Override
  public @NotNull ILogging getLogging(long loggingId) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull List<ILogging> getLoggingList() {
    return List.of();
  }

  @Override
  public @NotNull ILogging getIpAddress(String ipAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull ILogging getTimestampOfIp(Date timestampOfIp) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull ILogging updateLogging(long loggingId,
                                         @Nullable String ipAddress,
                                         @Nullable Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  public @NotNull ILogging updateIpAddress(long loggingId,
                                           @Nullable String ipAddress) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  public @NotNull ILogging updateTimestampOfIp(long loggingId, @Nullable Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  public boolean deleteLogging(long loggingId) throws EntityNotPresentException {
    return false;
  }
}
