package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;


public class HardwareManager implements IHardwareAccess<IHardware> {


  @Override
  public @NotNull IHardware createHardware(@Nullable String token, @Nullable String macAddress, @Nullable Date hardwareAdded, @Nullable Date macAdded, boolean keyIsActive) throws EntityNotPresentException, EntityAlreadyPresentException {
    return null;
  }

  @Override
  public @NotNull IHardware getHardware(long id) throws EntityAlreadyPresentException {
    return null;
  }
}
