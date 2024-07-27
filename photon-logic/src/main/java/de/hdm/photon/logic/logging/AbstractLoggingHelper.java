package de.hdm.photon.logic.logging;

import de.hdm.photon.database.postgresql.logging.LoggingEntity;
import de.hdm.photon.database.postgresql.logging.LoggingRepository;
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
public abstract class AbstractLoggingHelper implements ILoggingHelper {
  private final LoggingRepository loggingRepository;
  private final ISecurityFilter addressFilter;

  protected AbstractLoggingHelper(@Nullable final LoggingRepository loggingRepository) {
    //Null check
    this.loggingRepository = loggingRepository;
    this.addressFilter = SecurityFilters.ipVersion4StringFilter();
  }


  /**
   * see {@link ILoggingAccess#createLogging(String)}
   */
  @Override
  @Transactional
  @SuppressWarnings("all")
  public @NotNull LoggingEntity createLogging(@Nullable String ipAddress) throws EntityNotPresentException, EntityAlreadyPresentException {
    log.info("Attempting to create a new logging object.");
    log.debug("Validating if the ip address is valid.");

    this.addressFilter.checkWithException(ipAddress);

    log.debug("Creating a timestamp for the ip.");

    final Date now = new Date();
    this.ipAndTimestampIsPresent(ipAddress, now);
    final LoggingEntity loggingEntity = new LoggingEntity(ipAddress, now);

    log.info("New logging entity successfully created: {}", loggingEntity.toString());
    return this.loggingRepository.save(loggingEntity);
  }

  /**
   * see {@link ILoggingAccess#getLogging(long)}
   */
  @Override
  @Transactional
  public @NotNull LoggingEntity getLogging(long loggingId) throws EntityNotPresentException {
    return this.loggingRepository.loggingElseThrow(loggingId);
  }

  /**
   * see {@link ILoggingAccess#getLoggingList()}
   */
  @Override
  @Transactional
  public @NotNull List<LoggingEntity> getLoggingList() {
    return this.loggingRepository.findAll();
  }

  /**
   * see {@link ILoggingAccess#getIpAddress(String)}
   */
  @Override
  @Transactional
  public @NotNull LoggingEntity getIpAddress(String ipAddress) throws EntityNotPresentException {
    return this.loggingRepository
        .findByIpAddress(ipAddress)
        .orElseThrow(
            () -> new EntityNotPresentException("No logging entity with the ip address = "+ipAddress+" available.")
        );
  }

  /**
   * see {@link ILoggingAccess#deleteLogging(long)}
   */
  @Override
  @Transactional
  public boolean deleteLogging(long loggingId) throws EntityNotPresentException {
    log.info("Attempting to delete logging entity with id: {}", loggingId);
    //Check if present.
    this.getLogging(loggingId);

    this.loggingRepository.deleteById(loggingId);
    log.info("Logging entity with id {} was successfully deleted.", loggingId);

    return this.loggingRepository.findById(loggingId).isEmpty();
  }

  /**
   * Checks if a hardware with the specified parameter already exists in the database.
   *
   * @param ipAddress The ip address of the hardware. Not null.
   * @throws EntityAlreadyPresentException If the hardware already exists.
   */
  @Transactional
  private void ipAndTimestampIsPresent(@Nullable final String ipAddress,
                                       @Nullable final Date timestampOfIp) throws EntityAlreadyPresentException {
    Objects.requireNonNull(timestampOfIp);
    if (ipAddress == null || ipAddress.isBlank()) {
      throw new NullPointerException("IP address is null!");
    }
    if (!this.loggingRepository.existsByIpAddressAndTimestampOfIp(ipAddress, timestampOfIp)) {
      return;
    }
    throw new EntityAlreadyPresentException("Logging entity with this ip and timestamp already exists.");
  }
}
