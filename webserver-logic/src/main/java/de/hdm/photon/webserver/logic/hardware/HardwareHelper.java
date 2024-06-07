package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;


public class HardwareHelper implements IHardwareAccess<HardwareEntity> {

  @Override
  public @NotNull HardwareEntity createHardware(@Nullable String token, @Nullable String macAddress, @Nullable Date hardwareAdded, @Nullable Date macAdded, boolean tokenIsActive) throws EntityNotPresentException, EntityAlreadyPresentException {
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
  public @NotNull HardwareEntity updateHardware(long id, @Nullable String token, @Nullable String macAddress, @Nullable Date hardwareAdded, @Nullable Date macAdded, boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateToken(long id, @Nullable String token) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateMacAddress(long id, @Nullable String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateHardwareAdded(long id, @Nullable Date hardwareAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateMacAdded(long id, @Nullable Date macAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull HardwareEntity updateTokenActive(long id, boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  public boolean deleteHardware(long id) throws EntityNotPresentException {
    return false;
  }
}
