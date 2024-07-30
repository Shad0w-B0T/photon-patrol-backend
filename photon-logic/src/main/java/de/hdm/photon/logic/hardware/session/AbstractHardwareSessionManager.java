package de.hdm.photon.logic.hardware.session;

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
  public @NotNull IHardwareSession createHardwareSession(@Nullable String ipAddress,
                                                         long hardwareId) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return HardwareSessionFactory.convertToRecord(this.loggingHelper.createHardwareSession(ipAddress, hardwareId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull IHardwareSession getHardwareSession(long hardwareSessionId) throws EntityNotPresentException {
    return HardwareSessionFactory.convertToRecord(this.loggingHelper.getHardwareSession(hardwareSessionId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull List<IHardwareSession> getHardwareSessionList() {
    return this.loggingHelper
        .getHardwareSessionList()
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
  public boolean deleteHardwareSession(long hardwareSessionId) throws EntityNotPresentException {
    return this.loggingHelper.deleteHardwareSession(hardwareSessionId);
  }
}
