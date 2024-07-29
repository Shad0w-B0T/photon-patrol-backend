package de.hdm.photon.logic.logging;

import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardwareSession;
import de.hdm.photon.schema.hardware.HardwareSessionFactory;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Log4j2
public abstract class AbstractHardwareSessionManager implements IHardwareSessionManager {
  private final @NotNull IHardwareSessionHelper loggingHelper;

  protected AbstractHardwareSessionManager(@Nullable final IHardwareSessionHelper loggingHelper) {
    //Null check
    Objects.requireNonNull(loggingHelper);

    this.loggingHelper = loggingHelper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull IHardwareSession createLogging(@Nullable String ipAddress,
                                                 long hardwareId) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return HardwareSessionFactory.convertToRecord(this.loggingHelper.createLogging(ipAddress, hardwareId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull IHardwareSession getLogging(long loggingId) throws EntityNotPresentException {
    return HardwareSessionFactory.convertToRecord(this.loggingHelper.getLogging(loggingId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull List<IHardwareSession> getLoggingList() {
    return this.loggingHelper
        .getLoggingList()
        .stream()
        .map(HardwareSessionFactory::convertToRecord)
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull IHardwareSession getIpAddress(String ipAddress) throws EntityNotPresentException {
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
