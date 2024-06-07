package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;


public class HardwareManager implements IHardwareAccess<IHardware> {


  @Override
  public @NotNull IHardware createHardware(@Nullable String token, @Nullable String macAddress, @Nullable Date hardwareAdded, @Nullable Date macAdded, boolean tokenIsActive) throws EntityNotPresentException, EntityAlreadyPresentException {
    return null;
  }

  @Override
  public @NotNull IHardware getHardware(long id) throws EntityAlreadyPresentException {
    return null;
  }

  @Override
  public @NotNull List<IHardware> getHardwareList() {
    return List.of();
  }

  @Override
  public @NotNull IHardware getMacAddress(String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull List<IHardware> getHardwareAdded(Date hardwareAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  public @NotNull List<IHardware> getMacAdded(Date macAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  public @NotNull List<IHardware> getActiveToken(boolean tokenIsActive) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  public @NotNull IHardware updateHardware(long id, @Nullable String token, @Nullable String macAddress, @Nullable Date hardwareAdded, @Nullable Date macAdded, boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull IHardware updateToken(long id, @Nullable String token) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull IHardware updateMacAddress(long id, @Nullable String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull IHardware updateHardwareAdded(long id, @Nullable Date hardwareAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull IHardware updateMacAdded(long id, @Nullable Date macAdded) throws EntityNotPresentException {
    return null;
  }

  @Override
  public @NotNull IHardware updateTokenActive(long id, boolean tokenIsActive) throws EntityNotPresentException {
    return null;
  }

  @Override
  public boolean deleteHardware(long id) throws EntityNotPresentException {
    return false;
  }
}
