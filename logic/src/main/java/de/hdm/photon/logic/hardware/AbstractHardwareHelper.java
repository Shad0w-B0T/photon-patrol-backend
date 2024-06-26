package de.hdm.photon.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.logic.security.io.ISecurityFilter;
import de.hdm.photon.logic.security.io.SecurityFilters;
import de.hdm.photon.logic.utils.TokenGenerator;
import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Log4j2
public abstract class AbstractHardwareHelper implements IHardwareHelper {

  private final @NotNull HardwareRepository hardwareRepository;
  private final @NotNull ISecurityFilter macFilter;

  protected AbstractHardwareHelper(@Nullable final HardwareRepository hardwareRepository) {
    //Null check
    Objects.requireNonNull(hardwareRepository);

    this.hardwareRepository = hardwareRepository;

    this.macFilter = SecurityFilters.macStringFilter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity createHardware() {

    log.info("Attempting to create a new hardware object.");

    final String token = TokenGenerator.randomString(64);
    final HardwareEntity hardwareEntity = new HardwareEntity(token, null, new Date(), null, true);

    log.info("New hardware successfully created: {}", hardwareEntity.toString());
    return this.hardwareRepository.save(hardwareEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity getHardware(long hardwareId) throws EntityNotPresentException {
    return this.hardwareRepository.hardwareElseThrow(hardwareId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity getHardware(@Nullable String token) throws EntityNotPresentException {
    //Null check
    Objects.requireNonNull(token);

    return this.hardwareRepository
        .findByToken(token)
        .orElseThrow(() -> new EntityNotPresentException("No entity with given token found."));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getHardwareList() {
    return this.hardwareRepository.findAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity getMacAddress(@Nullable String macAddress) throws EntityNotPresentException {
    //Null check
    Objects.requireNonNull(macAddress);

    return this.hardwareRepository
        .findByMacAddress(macAddress)
        .orElseThrow(() -> new EntityNotPresentException("No hardware with the mac address = "+macAddress+" available."));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getHardwareAddedList(@Nullable Date hardwareAdded) {
    //Null check
    Objects.requireNonNull(hardwareAdded);

    return this.hardwareRepository
        .findByHardwareAdded(hardwareAdded)
        .stream()
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getMacAddedList(@Nullable Date macAdded) {
    //Null check
    Objects.requireNonNull(macAdded);

    return this.hardwareRepository
        .findByMacAdded(macAdded)
        .stream()
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getActiveTokenList(boolean tokenIsActive) {
    return this.hardwareRepository
        .findByTokenIsActive(tokenIsActive)
        .stream()
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity updateHardware(long hardwareId,
                                                @Nullable String macAddress,
                                                @Nullable Date hardwareAdded,
                                                @Nullable Date macAdded,
                                                boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {
    //Null check
    Objects.requireNonNull(macAddress);
    Objects.requireNonNull(hardwareAdded);
    Objects.requireNonNull(macAdded);

    if (!this.macFilter.check(macAdded)) {
      throw new FormatMismatchException("Can't update hardware, given mac address is illegal.");
    }

    log.info("Attempting to update hardware with id: '{}'. New mac address: '{}'. New date when the "+
            "hardware was added: '{}'. New date when the mac was added: '{}'",
        hardwareId,
        macAddress,
        hardwareAdded,
        macAdded);

    if (!this.hardwareRepository.existsByMacAddress(macAddress)) {
      throw new EntityAlreadyPresentException("Hardware with this mac already exists.");
    }

    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);

    hardwareEntity.macAddress(macAddress);
    hardwareEntity.hardwareAdded(hardwareAdded);
    hardwareEntity.tokenIsActive(tokenIsActive);

    final HardwareEntity updatedHardware = this.hardwareRepository.save(hardwareEntity);
    log.info("Hardware with id: {} updated successfully.", hardwareId);
    return updatedHardware;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity updateMacAddress(long hardwareId,
                                                  @Nullable String macAddress) throws EntityNotPresentException, FormatMismatchException {
    //Null check
    Objects.requireNonNull(macAddress);

    if (!this.macFilter.check(macAddress)) {
      throw new FormatMismatchException("Can't update mac, given mac address is illegal.");
    }

    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);
    hardwareEntity.macAddress(macAddress);
    hardwareEntity.macAdded(new Date());
    return this.hardwareRepository.save(hardwareEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity updateTokenActive(long hardwareId,
                                                   boolean tokenIsActive) throws EntityNotPresentException {
    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);
    hardwareEntity.tokenIsActive(tokenIsActive);
    return this.hardwareRepository.save(hardwareEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public boolean deleteHardware(long hardwareId) throws EntityNotPresentException {
    log.info("Attempting to delete Hardware with id: {}", hardwareId);

    //Check if present.
    this.getHardware(hardwareId);

    this.hardwareRepository.deleteById(hardwareId);
    log.info("Hardware with id {} was successfully deleted.", hardwareId);

    return this.hardwareRepository.findById(hardwareId).isEmpty();
  }
}
