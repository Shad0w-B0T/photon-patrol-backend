package de.hdm.photon.logic.hardwareSession;

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
   * see {@link IHardwareSessionAccess#createHardwareSession(String, long)}}
   */
  @Override
  @Transactional
  @SuppressWarnings("all")
  public @NotNull HardwareSessionEntity createHardwareSession(@Nullable String ipAddress,
                                                              long hardwareId) throws EntityNotPresentException, EntityAlreadyPresentException {
    log.info("Attempting to create a new hardware session object.");
    log.debug("Validating if the ip address is valid.");

    this.addressFilter.checkWithException(ipAddress);

    log.debug("Creating a new hardware object.");
    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);

    log.debug("Creating a timestamp for the ip.");
    final Date now = new Date();
    this.isPresent(hardwareEntity, ipAddress, now);
    final HardwareSessionEntity hardwareSessionEntity = new HardwareSessionEntity(hardwareEntity, ipAddress, now);

    log.info("New hardware session entity successfully created: {}", hardwareSessionEntity.toString());
    return this.hardwareSessionRepository.save(hardwareSessionEntity);
  }

  /**
   * see {@link IHardwareSessionAccess#getHardwareSession(long)}
   */
  @Override
  @Transactional
  public @NotNull HardwareSessionEntity getHardwareSession(long hardwareSessionId) throws EntityNotPresentException {
    return this.hardwareSessionRepository.hardwareSessionElseThrow(hardwareSessionId);
  }

  /**
   * see {@link IHardwareSessionAccess#getHardwareSessionList()}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareSessionEntity> getHardwareSessionList() {
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
            () -> new EntityNotPresentException("No hardware session entity with the ip address = "+ipAddress+" available.")
        );
  }

  /**
   * see {@link IHardwareSessionAccess#deleteHardwareSession(long)}
   */
  @Override
  @Transactional
  public boolean deleteHardwareSession(long hardwareSessionId) throws EntityNotPresentException {
    log.info("Attempting to delete hardware session entity with id: {}", hardwareSessionId);
    //Check if present.
    this.getHardwareSession(hardwareSessionId);

    this.hardwareSessionRepository.deleteById(hardwareSessionId);
    log.info("Hardware session entity with id {} was successfully deleted.", hardwareSessionId);

    return this.hardwareSessionRepository.findById(hardwareSessionId).isEmpty();
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
    throw new EntityAlreadyPresentException("Hardware session entity with this hardware entity, ip and timestamp already exists.");
  }
}
