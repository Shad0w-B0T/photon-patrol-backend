package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.webserver.logic.check.AddressVerifier;
import de.hdm.photon.webserver.logic.check.IAddressVerifier;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Log4j2
@Component
public class HardwareHelper implements IHardwareAccess<HardwareEntity> {

  @Autowired
  private HardwareRepository hardwareRepository;

  @Autowired
  private IAddressVerifier iAddressVerifier;


  @Override
  @Transactional
  public @NotNull HardwareEntity createHardware(@Nullable String token,
                                                @Nullable String macAddress,
                                                @Nullable Date hardwareAdded,
                                                @Nullable Date macAdded,
                                                boolean keyIsActive) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity getHardware(long id) throws EntityAlreadyPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getHardwareList() {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity getMacAddress(String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getHardwareAdded(Date hardwareAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getMacAdded(Date macAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull List<HardwareEntity> getActiveToken(boolean tokenIsActive) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity updateHardware(long id,
                                                @Nullable String token,
                                                @Nullable String macAddress,
                                                @Nullable Date hardwareAdded,
                                                @Nullable Date macAdded,
                                                boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity updateToken(long id,
                                             @Nullable String token) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity updateMacAddress(long id,
                                                  @Nullable String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity updateHardwareAdded(long id,
                                                     @Nullable Date hardwareAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity updateMacAdded(long id,
                                                @Nullable Date macAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull HardwareEntity updateTokenActive(long id,
                                                   boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public boolean deleteHardware(long id) throws EntityNotPresentException {
    return false;
  }

}
