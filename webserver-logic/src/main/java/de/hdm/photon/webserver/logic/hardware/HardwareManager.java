package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.logic.utils.TokenGenerator;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Component
@Log4j2
@NoArgsConstructor
public class HardwareManager implements IHardwareManager {

  @Autowired
  private HardwareHelper hardwareHelper;

  /**
   * see {@link IHardwareAccess#createHardware()}
   */
  @Override
  @Transactional
  public @NotNull IHardware createHardware() throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.createHardware());
  }

  /**
   * see {@link IHardwareAccess#getHardware(long)}
   */
  @Override
  @Transactional
  public @NotNull IHardware getHardware(long hardwareId) throws EntityNotPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.getHardware(hardwareId));
  }

  /**
   * see {@link IHardwareAccess#getHardwareList()}
   */
  @Override
  @Transactional
  public @NotNull List<IHardware> getHardwareList() {
    return this.hardwareHelper
        .getHardwareList()
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * see {@link IHardwareAccess#getMacAddress(String)}
   */
  @Override
  @Transactional
  public @NotNull IHardware getMacAddress(String macAddress) throws EntityNotPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.getMacAddress(macAddress));
  }

  /**
   * see {@link IHardwareAccess#getHardwareAddedList(Date)}
   */
  @Override
  @Transactional
  public @NotNull List<IHardware> getHardwareAddedList(Date hardwareAdded) throws EntityNotPresentException {
    return this.hardwareHelper
        .getHardwareAddedList(hardwareAdded)
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * see {@link IHardwareAccess#getMacAddedList(Date)}
   */
  @Override
  @Transactional
  public @NotNull List<IHardware> getMacAddedList(Date macAdded) throws EntityNotPresentException {
    return this.hardwareHelper
        .getMacAddedList(macAdded)
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * see {@link IHardwareAccess#getActiveTokenList(boolean)}
   */
  @Override
  @Transactional
  public @NotNull List<IHardware> getActiveTokenList(boolean tokenIsActive) throws EntityNotPresentException {
    return this.hardwareHelper
        .getActiveTokenList(tokenIsActive)
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * see {@link IHardwareAccess#updateHardware(long, String, Date, Date, boolean)}
   */
  @Override
  @Transactional
  public @NotNull IHardware updateHardware(long hardwareId,
                                           @Nullable String macAddress,
                                           @Nullable Date hardwareAdded,
                                           @Nullable Date macAdded,
                                           boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateHardware(hardwareId, macAddress, hardwareAdded, macAdded, tokenIsActive));
  }

  /**
   * see {@link IHardwareAccess#updateMacAddress(long, String)}
   */
  @Override
  @Transactional
  public @NotNull IHardware updateMacAddress(long hardwareId,
                                             @Nullable String macAddress) throws EntityNotPresentException, FormatMismatchException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateMacAddress(hardwareId, macAddress));
  }

  /**
   * see {@link IHardwareAccess#updateHardwareAdded(long, Date)}
   */
  @Override
  @Transactional
  public @NotNull IHardware updateHardwareAdded(long hardwareId,
                                                @Nullable Date hardwareAdded) throws EntityNotPresentException, FormatMismatchException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateHardwareAdded(hardwareId, hardwareAdded));
  }

  /**
   * see {@link IHardwareAccess#updateMacAddress(long, String)}
   */
  @Override
  @Transactional
  public @NotNull IHardware updateMacAdded(long hardwareId,
                                           @Nullable Date macAdded) throws EntityNotPresentException, FormatMismatchException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateMacAdded(hardwareId, macAdded));
  }

  /**
   * see {@link IHardwareAccess#updateTokenActive(long, boolean)}
   */
  @Override
  @Transactional
  public @NotNull IHardware updateTokenActive(long hardwareId,
                                              boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateTokenActive(hardwareId, tokenIsActive));
  }

  /**
   * see {@link IHardwareAccess#deleteHardware(long)}
   */
  @Override
  @Transactional
  public boolean deleteHardware(long hardwareId) throws EntityNotPresentException {
    return this.hardwareHelper.deleteHardware(hardwareId);
  }
}
