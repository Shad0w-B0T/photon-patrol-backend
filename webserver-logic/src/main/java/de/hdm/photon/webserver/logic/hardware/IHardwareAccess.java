package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;


public interface IHardwareAccess <HARDWARE extends IHardware> {

  //------------------------------------------------CREATE--------------------------------------------------------------

  @NotNull HARDWARE createHardware(@Nullable final String token,
                                   @Nullable final String macAddress,
                                   @Nullable final Date hardwareAdded,
                                   @Nullable final Date macAdded,
                                   final boolean keyIsActive) throws EntityNotPresentException, EntityAlreadyPresentException;




  //--------------------------------------------------READ--------------------------------------------------------------

  @NotNull HARDWARE getHardware(final long id) throws EntityAlreadyPresentException;

  //------------------------------------------------UPDATE--------------------------------------------------------------
  //------------------------------------------------DELETE--------------------------------------------------------------


}
