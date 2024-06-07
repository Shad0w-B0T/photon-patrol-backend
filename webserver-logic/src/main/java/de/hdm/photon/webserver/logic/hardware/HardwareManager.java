package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.HardwareFactory;
import de.hdm.photon.schema.hardware.IHardware;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
@NoArgsConstructor
public class HardwareManager implements IHardwareAccess<IHardware> {

  @Autowired
  private HardwareHelper hardwareHelper;

  @Override
  public @NotNull IHardware createHardware(@Nullable String token, @Nullable String macAddress, @Nullable Date hardwareAdded, @Nullable Date macAdded, boolean keyIsActive) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.createHardware(token, macAddress, hardwareAdded, macAdded, keyIsActive));
  }

  @Override
  public @NotNull IHardware getHardware(long id) throws EntityAlreadyPresentException {
    return null;
  }
}
