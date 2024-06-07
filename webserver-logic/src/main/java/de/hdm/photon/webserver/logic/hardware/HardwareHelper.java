package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.database.postgresql.hardware.HardwareEntity;
import de.hdm.photon.database.postgresql.hardware.HardwareRepository;
import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.webserver.logic.ceck.AddressVerifier;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Log4j2
@Component
public class HardwareHelper implements IHardwareAccess<HardwareEntity> {

  @Autowired
  private HardwareRepository hardwareRepository;
  @Autowired
  private AddressVerifier addressVerifier;


  @Override
  public @NotNull HardwareEntity createHardware(@Nullable String token, @Nullable String macAddress, @Nullable Date hardwareAdded, @Nullable Date macAdded, boolean keyIsActive) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
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

  private void validateContent(@Nullable String macAddress) throws FormatMismatchException {
    this.addressVerifier.validateAddress(macAddress, 17);

  }
}
