package de.hdm.photon.logic.logging;

import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.logging.ILogging;
import de.hdm.photon.schema.logging.LoggingFactory;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Log4j2
public abstract class AbstractLoggingManager implements ILoggingManager {
  private final @NotNull ILoggingHelper loggingHelper;

  protected AbstractLoggingManager(@Nullable final ILoggingHelper loggingHelper) {
    //Null check
    Objects.requireNonNull(loggingHelper);

    this.loggingHelper = loggingHelper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull ILogging createLogging(@Nullable String ipAddress,
                                         long hardwareId) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return LoggingFactory.convertToRecord(this.loggingHelper.createLogging(ipAddress, hardwareId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull ILogging getLogging(long loggingId) throws EntityNotPresentException {
    return LoggingFactory.convertToRecord(this.loggingHelper.getLogging(loggingId));
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull ILogging getIpAddress(String ipAddress) throws EntityNotPresentException {
    return this.loggingHelper.getIpAddress(ipAddress);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public boolean deleteLogging(long loggingId) throws EntityNotPresentException {
    return this.loggingHelper.deleteLogging(loggingId);
  }
}
