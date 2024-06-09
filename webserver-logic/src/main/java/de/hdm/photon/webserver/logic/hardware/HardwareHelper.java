package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.webserver.logic.ceck.AddressVerifier;
import de.hdm.photon.webserver.logic.ceck.IAddressVerifier;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Log4j2
@Component
public class HardwareHelper implements IHardwareAccess<HardwareEntity> {

  @Autowired
  private HardwareRepository hardwareRepository;
  @Autowired
  private IAddressVerifier addressVerifier;


  @Override
  public @NotNull HardwareEntity createHardware(@Nullable String token,
                                                @Nullable String macAddress,
                                                @Nullable Date hardwareAdded,
                                                @Nullable Date macAdded,
                                                boolean keyIsActive) throws FormatMismatchException {
    this.validateContent(macAddress);

    log.info("Try to creating validated hardware");

    // ID NACHSCHAUEN WIE WIR DES MACHEN

    final HardwareEntity hardwareEntity = new HardwareEntity(1, token, macAddress, hardwareAdded, macAdded,
        keyIsActive);

    log.info("Created new validated hardware");

    // WHAT IS MIT DEM SAVE?
    // return this.hardwareRepository.save(hardwareEntity);
    return null;
  }

  @Override
  public @NotNull HardwareEntity getHardware(long id) throws EntityAlreadyPresentException {
    return null;
  }

  @Override
  public @NotNull List<HardwareEntity> getHardwareList() {
    return List.of();
  }

  @Override
  public @NotNull HardwareEntity getMacAddress(String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull List<HardwareEntity> getHardwareAdded(Date hardwareAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  public @NotNull List<HardwareEntity> getMacAdded(Date macAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  public @NotNull List<HardwareEntity> getActiveToken(boolean tokenIsActive) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  public @NotNull HardwareEntity updateHardware(long id,
                                                @Nullable String token,
                                                @Nullable String macAddress,
                                                @Nullable Date hardwareAdded,
                                                @Nullable Date macAdded,
                                                boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateToken(long id,
                                             @Nullable String token) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateMacAddress(long id,
                                                  @Nullable String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateHardwareAdded(long id,
                                                     @Nullable Date hardwareAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateMacAdded(long id,
                                                @Nullable Date macAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateTokenActive(long id,
                                                   boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  public boolean deleteHardware(long id) throws EntityNotPresentException {
    return false;
  }

  private void validateContent(@Nullable String macAddress) throws FormatMismatchException {
    Objects.requireNonNull(macAddress);
    this.addressVerifier.validateAddress(macAddress, 17);

  }
}
