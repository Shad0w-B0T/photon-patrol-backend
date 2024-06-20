package de.hdm.photon.webserver.logic.logging;

import de.hdm.photon.database.postgresql.logging.LoggingEntity;
import de.hdm.photon.database.postgresql.logging.LoggingRepository;
import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.webserver.logic.check.IAddressVerifier;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Log4j2
@Component
public class LoggingHelper implements ILoggingAccess<LoggingEntity> {

  @Autowired
  private IAddressVerifier addressVerifier;

  @Autowired
  private LoggingRepository loggingRepository;


  /**
   * see {@link ILoggingAccess#createLogging(long, String, Date)}
   */
  @Override
  @Transactional
  @SuppressWarnings("all")
  public @NotNull LoggingEntity createLogging(long hardwareId,
                                              @Nullable String ipAddress,
                                              @Nullable Date timestampOfIp) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    this.addressVerifier.validateIpAddress(ipAddress);
    this.addressVerifier.validateContentOfIP(ipAddress);
    this.ipAndTimestampIsPresent(ipAddress, timestampOfIp);


    log.info("Attempting to create a new logging object with id: {}", hardwareId);

    final LoggingEntity loggingEntity = new LoggingEntity(hardwareId, ipAddress, timestampOfIp);

    log.info("New logging entity successfully created with the id: {}", hardwareId);

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
            () -> new EntityNotPresentException("No logging entity with the ip address = "+ ipAddress+ " available.")
        );
  }

  /**
   * see {@link ILoggingAccess#getTimestampOfIp(Date)}
   */
  @Override
  @Transactional
  public @NotNull List<LoggingEntity> getTimestampOfIp(Date timestampOfIp) throws EntityNotPresentException {
    return this.loggingRepository
        .findByTimestampOfIp(timestampOfIp)
        .stream()
        .toList();
  }

  /**
   * see {@link ILoggingAccess#updateLogging(long, String, Date)}
   */
  @Override
  @Transactional
  public @NotNull LoggingEntity updateLogging(long loggingId,
                                              @Nullable String ipAddress,
                                              @Nullable Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {
    Objects.requireNonNull(ipAddress);
    Objects.requireNonNull(timestampOfIp);

    this.addressVerifier.validateIpAddress(ipAddress);
    this.addressVerifier.validateContentOfIP(ipAddress);

    log.info("Attempting to update logging entity with id: '{}'. New ip address: '{}'. New time of ip: '{}'.",
        loggingId,
        ipAddress,
        timestampOfIp);

    final LoggingEntity loggingEntity = this.loggingRepository.loggingElseThrow(loggingId);

    this.ipAndTimestampIsPresent(ipAddress, timestampOfIp);
    loggingEntity.ipAddress(ipAddress);
    loggingEntity.timestampOfIp(timestampOfIp);

    final LoggingEntity updatedLoggingEntity = this.loggingRepository.save(loggingEntity);
    log.info("Logging entity successfully updated with the id: {}", loggingId);
    return updatedLoggingEntity;
  }

  /**
   * see {@link ILoggingAccess#updateIpAddress(long, String)}
   */
  @Override
  @Transactional
  public @NotNull LoggingEntity updateIpAddress(long loggingId,
                                                @Nullable String ipAddress) throws EntityNotPresentException, FormatMismatchException {
    final LoggingEntity loggingEntity = this.loggingRepository.loggingElseThrow(loggingId);
    loggingEntity.ipAddress(ipAddress);
    return this.loggingRepository.save(loggingEntity);
  }

  /**
   * see {@link ILoggingAccess#updateTimestampOfIp(long, Date)}
   */
  @Override
  @Transactional
  public @NotNull LoggingEntity updateTimestampOfIp(long loggingId,
                                                    @Nullable Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException {
    final LoggingEntity loggingEntity = this.loggingRepository.loggingElseThrow(loggingId);
    loggingEntity.timestampOfIp(timestampOfIp);
    return this.loggingRepository.save(loggingEntity);
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
  protected void ipAndTimestampIsPresent(@Nullable final String ipAddress,
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
