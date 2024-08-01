package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.session.IHardwareSession;
import de.hdm.photon.schema.hardware.session.HardwareSessionFactory;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Abstract manager class for handling hardware sessions.
 * Provides methods to create, retrieve, list, and delete hardware sessions.
 */
@Log4j2
public abstract class AbstractHardwareSessionManager implements IHardwareSessionManager {
  private final @NotNull IHardwareSessionHelper loggingHelper;

  /**
   * Constructs an instance of {@code AbstractHardwareSessionManager} with the specified hardware session helper.
   *
   * @param loggingHelper the helper responsible for managing hardware sessions. Must not be {@code null}.
   * @throws NullPointerException if {@code loggingHelper} is {@code null}.
   */
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
