package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Component
@Log4j2
@NoArgsConstructor
public class HardwareManager implements IHardwareManager {

  @Autowired
  private HardwareHelper hardwareHelper;

  @Override
  @Transactional
  public @NotNull IHardware createHardware(long loggingId,
                                           @Nullable String token,
                                           @Nullable String macAddress,
                                           @Nullable Date hardwareAdded,
                                           @Nullable Date macAdded,
                                           boolean tokenIsActive) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull IHardware getHardware(long hardwareId) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull List<IHardware> getHardwareList() {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull IHardware getMacAddress(String macAddress) throws EntityNotPresentException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull List<IHardware> getHardwareAdded(Date hardwareAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull List<IHardware> getMacAdded(Date macAdded) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull List<IHardware> getActiveToken(boolean tokenIsActive) throws EntityNotPresentException {
    return List.of();
  }

  @Override
  @Transactional
  public @NotNull IHardware updateHardware(long hardwareId,
                                           @Nullable String macAddress,
                                           @Nullable Date hardwareAdded,
                                           @Nullable Date macAdded,
                                           boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull IHardware updateMacAddress(long hardwareId,
                                             @Nullable String macAddress) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull IHardware updateHardwareAdded(long hardwareId,
                                                @Nullable Date hardwareAdded) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull IHardware updateMacAdded(long hardwareId,
                                           @Nullable Date macAdded) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  @Transactional
  public @NotNull IHardware updateTokenActive(long hardwareId,
                                              boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException {
    return null;
  }

  @Override
  @Transactional
  public boolean deleteHardware(long hardwareId) throws EntityNotPresentException {
    return false;
  }
}
