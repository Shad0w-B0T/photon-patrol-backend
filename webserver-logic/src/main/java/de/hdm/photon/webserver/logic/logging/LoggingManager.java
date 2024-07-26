package de.hdm.photon.webserver.logic.logging;

import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.logging.ILogging;
import de.hdm.photon.schema.logging.LoggingFactory;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Log4j2
@Component
public class LoggingManager implements ILoggingAccess<ILogging> {

  @Autowired
  private LoggingHelper loggingHelper;

  /**
   * see {@link ILoggingAccess#createLogging(String)}
   */
  @Override
  @Transactional
  public @NotNull ILogging createLogging(@Nullable String ipAddress) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return LoggingFactory.convertToRecord(this.loggingHelper.createLogging(ipAddress));
  }

  /**
   * see {@link ILoggingAccess#getLogging(long)}
   */
  @Override
  @Transactional
  public @NotNull ILogging getLogging(long loggingId) throws EntityNotPresentException {
    return LoggingFactory.convertToRecord(this.loggingHelper.getLogging(loggingId));
  }

  /**
   * see {@link ILoggingAccess#getLoggingList()}
   */
  @Override
  @Transactional
  public @NotNull List<ILogging> getLoggingList() {
    return this.loggingHelper
        .getLoggingList()
        .stream()
        .map(LoggingFactory::convertToRecord)
        .toList();
  }

  /**
   * see {@link ILoggingAccess#getIpAddress(String)}
   */
  @Override
  @Transactional
  public @NotNull ILogging getIpAddress(String ipAddress) throws EntityNotPresentException {
    return this.loggingHelper.getIpAddress(ipAddress);
  }

  /**
   * see {@link ILoggingAccess#getTimestampOfIp(Date)}
   */
  @Override
  @Transactional
  public @NotNull List<ILogging> getTimestampOfIp(Date timestampOfIp) throws EntityNotPresentException {
    return this.loggingHelper
        .getTimestampOfIp(timestampOfIp)
        .stream()
        .map(LoggingFactory::convertToRecord)
        .toList();
  }

  /**
   * see {@link ILoggingAccess#updateLogging(long, String, Date)}
   */
  @Override
  @Transactional
  public @NotNull ILogging updateLogging(long loggingId,
                                         @Nullable String ipAddress,
                                         @Nullable Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {
    return LoggingFactory.convertToRecord(this.loggingHelper.updateLogging(loggingId, ipAddress, timestampOfIp));
  }

  /**
   * see {@link ILoggingAccess#updateIpAddress(long, String)}
   */
  @Override
  @Transactional
  public @NotNull ILogging updateIpAddress(long loggingId,
                                           @Nullable String ipAddress) throws EntityNotPresentException, FormatMismatchException {
    return LoggingFactory.convertToRecord(this.loggingHelper.updateIpAddress(loggingId, ipAddress));
  }

  /**
   * see {@link ILoggingAccess#updateTimestampOfIp(long, Date)}
   */
  @Override
  @Transactional
  public @NotNull ILogging updateTimestampOfIp(long loggingId,
                                               @Nullable Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException {
    return this.loggingHelper.updateTimestampOfIp(loggingId, timestampOfIp);
  }

  /**
   * see {@link ILoggingAccess#deleteLogging(long)}
   */
  @Override
  @Transactional
  public boolean deleteLogging(long loggingId) throws EntityNotPresentException {
    return this.loggingHelper.deleteLogging(loggingId);
  }
}
