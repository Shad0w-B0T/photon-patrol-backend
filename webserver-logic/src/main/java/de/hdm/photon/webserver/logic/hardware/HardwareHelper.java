package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.logic.utils.TokenGenerator;
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
public class HardwareHelper implements IHardwareAccess<HardwareEntity> {

  @Autowired
  private HardwareRepository hardwareRepository;

  @Autowired
  private IAddressVerifier iAddressVerifier;

  /**
   * see {@link IHardwareAccess#createHardware()}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity createHardware() throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {

    log.info("Attempting to create a new hardware object.");

    final String token = TokenGenerator.randomString(64);
    final Date hardwareAdded = new Date();
    final boolean tokenIsActive = true;
    final HardwareEntity hardwareEntity = new HardwareEntity(token, null, hardwareAdded, null, tokenIsActive);

    log.info("New hardware successfully created: {}", hardwareEntity.toString());
    return this.hardwareRepository.save(hardwareEntity);
  }

  /**
   * see {@link IHardwareAccess#getHardware(long)}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity getHardware(long hardwareId) throws EntityNotPresentException {
    return this.hardwareRepository.hardwareElseThrow(hardwareId);
  }

  /**
   * see {@link IHardwareAccess#getHardwareList()}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getHardwareList() {
    return this.hardwareRepository.findAll();
  }

  /**
   * see {@link IHardwareAccess#getMacAddress(String)}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity getMacAddress(String macAddress) throws EntityNotPresentException {
    return this.hardwareRepository
        .findByMacAddress(macAddress)
        .orElseThrow(
            () -> new EntityNotPresentException("No hardware with the mac address = "+macAddress+" available.")
        );
  }

  /**
   * see {@link IHardwareAccess#getHardwareAddedList(Date)}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getHardwareAddedList(Date hardwareAdded) throws EntityNotPresentException {
    return this.hardwareRepository
        .findByHardwareAdded(hardwareAdded)
        .stream()
        .toList();
  }

  /**
   * see {@link IHardwareAccess#getMacAddedList(Date)}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getMacAddedList(Date macAdded) throws EntityNotPresentException {
    return this.hardwareRepository
        .findByMacAdded(macAdded)
        .stream()
        .toList();
  }

  /**
   * see {@link IHardwareAccess#getActiveTokenList(boolean)}
   */
  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getActiveTokenList(boolean tokenIsActive) throws EntityNotPresentException {
    return this.hardwareRepository
        .findByTokenIsActive(tokenIsActive)
        .stream()
        .toList();
  }

  /**
   * see {@link IHardwareAccess#updateHardware(long, String, Date, Date, boolean)}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity updateHardware(long hardwareId,
                                                @Nullable String macAddress,
                                                @Nullable Date hardwareAdded,
                                                @Nullable Date macAdded,
                                                boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {

    Objects.requireNonNull(macAddress);
    Objects.requireNonNull(hardwareAdded);
    Objects.requireNonNull(macAdded);

    this.iAddressVerifier.validateMacAddress(macAddress);
    this.iAddressVerifier.validateContentOfMac(macAddress);

    log.info("Attempting to update hardware with id: '{}'. New mac address: '{}'. New date when the "+
            "hardware was added: '{}'. New date when the mac was added: '{}'",
        hardwareId,
        macAddress,
        hardwareAdded,
        macAdded);

    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);
    this.macIsPresent(macAddress);

    hardwareEntity.macAddress(macAddress);
    hardwareEntity.hardwareAdded(hardwareAdded);
    hardwareEntity.tokenIsActive(tokenIsActive);

    final HardwareEntity updatedHardware = this.hardwareRepository.save(hardwareEntity);
    log.info("Hardware with id: {} updated successfully.", hardwareId);
    return updatedHardware;
  }

  /**
   * see {@link IHardwareAccess#updateMacAddress(long, String)}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity updateMacAddress(long hardwareId,
                                                  @Nullable String macAddress) throws EntityNotPresentException {
    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);
    hardwareEntity.macAddress(macAddress);
    return this.hardwareRepository.save(hardwareEntity);
  }

  /**
   * see {@link IHardwareAccess#updateHardwareAdded(long, Date)}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity updateHardwareAdded(long hardwareId,
                                                     @Nullable Date hardwareAdded) throws EntityNotPresentException {
    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);
    hardwareEntity.hardwareAdded(hardwareAdded);
    return this.hardwareRepository.save(hardwareEntity);
  }

  /**
   * see {@link IHardwareAccess#updateMacAdded(long, Date)}
   */
  @Override
  @Transactional
  public @NotNull HardwareEntity updateMacAdded(long hardwareId,
                                                @Nullable Date macAdded) throws EntityNotPresentException {
    final HardwareEntity hardwareEntity = this.hardwareRepository.hardwareElseThrow(hardwareId);
    hardwareEntity.macAdded(macAdded);
    return this.hardwareRepository.save(hardwareEntity);
  }

  /**
   * see {@link IHardwareAccess#updateTokenActive(long, boolean)}
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
   * see {@link IHardwareAccess#deleteHardware(long)}
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


  /**
   * Checks if a hardware with the specified parameter already exists in the database.
   *
   * @param macAddress The mac address of the hardware. Not null.
   * @throws EntityAlreadyPresentException If the hardware already exists.
   */
  @Transactional
  protected void macIsPresent(@Nullable String macAddress) throws EntityAlreadyPresentException {
    if (macAddress == null || macAddress.isBlank()) {
      throw new NullPointerException("MAC address is null!");
    }
    if (!this.hardwareRepository.existsByMacAddress(macAddress)) {
      return;
    }
    throw new EntityAlreadyPresentException("Hardware with this mac already exists.");
  }

}
