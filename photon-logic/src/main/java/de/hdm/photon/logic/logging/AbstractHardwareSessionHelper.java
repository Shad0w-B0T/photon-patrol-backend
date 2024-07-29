package de.hdm.photon.logic.logging;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.database.postgresql.hardware.HardwareSessionEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareSessionRepository;
import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilters;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Log4j2
public abstract class AbstractHardwareSessionHelper implements IHardwareSessionHelper {
  private final HardwareSessionRepository hardwareSessionRepository;
  private final ISecurityFilter addressFilter;
  private final HardwareRepository hardwareRepository;

  protected AbstractHardwareSessionHelper(@Nullable final HardwareSessionRepository hardwareSessionRepository,
                                          HardwareRepository hardwareRepository) {
    //Null check
    this.hardwareSessionRepository = hardwareSessionRepository;
    this.hardwareRepository = hardwareRepository;
    this.addressFilter = SecurityFilters.ipVersion4StringFilter();
  }


  /**
   * see {@link IHardwareSessionAccess#createLogging(String)}
   */
  @Override
  @Transactional
  @SuppressWarnings("all")
  public @NotNull HardwareSessionEntity createLogging(@Nullable String ipAddress,
                                                      long hardwareId) throws EntityNotPresentException, EntityAlreadyPresentException {
    log.info("Attempting to create a new logging object.");
    log.debug("Validating if the ip address is valid.");

    this.addressFilter.checkWithException(ipAddress);

    log.debug("Creating a new hardware object.");
    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);

    log.debug("Creating a timestamp for the ip.");
    final Date now = new Date();
    this.isPresent(hardwareEntity, ipAddress, now);
    final HardwareSessionEntity loggingEntity = new HardwareSessionEntity(hardwareEntity, ipAddress, now);

    log.info("New logging entity successfully created: {}", loggingEntity.toString());
    return this.hardwareSessionRepository.save(loggingEntity);
  }

  /**
   * see {@link IHardwareSessionAccess#getLogging(long)}
   */
  @Override
  @Transactional
  public @NotNull HardwareSessionEntity getLogging(long loggingId) throws EntityNotPresentException {
    return this.hardwareSessionRepository.loggingElseThrow(loggingId);
  }

  /**
   * see {@link IHardwareSessionAccess#getLoggingList()}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareSessionEntity> getLoggingList() {
    return this.hardwareSessionRepository.findAll();
  }

  /**
   * see {@link IHardwareSessionAccess#getIpAddress(String)}
   */
  @Override
  @Transactional
  public @NotNull HardwareSessionEntity getIpAddress(String ipAddress) throws EntityNotPresentException {
    return this.hardwareSessionRepository
        .findByIpAddress(ipAddress)
        .orElseThrow(
            () -> new EntityNotPresentException("No logging entity with the ip address = "+ipAddress+" available.")
        );
  }

  /**
   * see {@link IHardwareSessionAccess#deleteLogging(long)}
   */
  @Override
  @Transactional
  public boolean deleteLogging(long loggingId) throws EntityNotPresentException {
    log.info("Attempting to delete logging entity with id: {}", loggingId);
    //Check if present.
    this.getLogging(loggingId);

    this.hardwareSessionRepository.deleteById(loggingId);
    log.info("Logging entity with id {} was successfully deleted.", loggingId);

    return this.hardwareSessionRepository.findById(loggingId).isEmpty();
  }

  /**
   * Checks if a hardware with the specified parameter already exists in the database.
   *
   * @param ipAddress The ip address of the hardware. Not null.
   * @throws EntityAlreadyPresentException If the hardware already exists.
   */
  @Transactional
  protected void isPresent(@NotNull final HardwareEntity hardwareEntity,
                           @Nullable final String ipAddress,
                           @Nullable final Date timestampOfIp) throws EntityAlreadyPresentException {

    Objects.requireNonNull(timestampOfIp);
    Objects.requireNonNull(hardwareEntity);

    if (ipAddress == null || ipAddress.isBlank()) {
      throw new NullPointerException("IP address is null!");
    }

    if (!this.hardwareSessionRepository.existsByHardwareEntityAndIpAddressAndTimestamp(hardwareEntity, ipAddress, timestampOfIp)) {
      return;
    }
    throw new EntityAlreadyPresentException("Logging entity with this hardware entity, ip and timestamp already exists.");
  }
}
