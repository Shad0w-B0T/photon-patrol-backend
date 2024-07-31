package de.hdm.photon.logic.hardware;

import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.HardwareFactory;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Abstract base class for managing hardware operations.
 * Provides common functionality for interacting with hardware through an {@link IHardwareHelper}.
 */
public abstract class AbstractHardwareManager implements IHardwareManager {

  private final @NotNull IHardwareHelper hardwareHelper;

  /**
   * Constructs an {@link AbstractHardwareManager} with the specified {@link IHardwareHelper}.
   *
   * @param hardwareHelper the hardware helper for hardware operations; cannot be null
   */

  protected AbstractHardwareManager(@Nullable final IHardwareHelper hardwareHelper) {
    //Null check
    Objects.requireNonNull(hardwareHelper);

    this.hardwareHelper = hardwareHelper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware createHardware() {
    return HardwareFactory.convertToRecord(this.hardwareHelper.createHardware());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware getHardware(long hardwareId) throws EntityNotPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.getHardware(hardwareId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware getHardware(@Nullable String token) throws EntityNotPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.getHardware(token));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<IHardware> getHardwareList() {
    return this.hardwareHelper
        .getHardwareList()
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware getMacAddress(@Nullable String macAddress) throws EntityNotPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.getMacAddress(macAddress));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<IHardware> getHardwareAddedList(@Nullable Date hardwareAdded) {
    return this.hardwareHelper
        .getHardwareAddedList(hardwareAdded)
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<IHardware> getMacAddedList(@Nullable Date macAdded) {
    return this.hardwareHelper
        .getMacAddedList(macAdded)
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull List<IHardware> getActiveTokenList(boolean tokenIsActive) {
    return this.hardwareHelper
        .getActiveTokenList(tokenIsActive)
        .stream()
        .map(HardwareFactory::convertToRecord)
        .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware toggleHardwareActive(long hardwareId) throws EntityNotPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.toggleHardwareActive(hardwareId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware updateHardware(long hardwareId,
                                           @Nullable String macAddress,
                                           @Nullable Date hardwareAdded,
                                           @Nullable Date macAdded,
                                           @Nullable Boolean tokenIsActive) throws EntityNotPresentException,
      FormatMismatchException, EntityAlreadyPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateHardware(hardwareId, macAddress, hardwareAdded, macAdded, tokenIsActive));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware updateMacAddress(long hardwareId,
                                             @Nullable String macAddress) throws EntityNotPresentException, FormatMismatchException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateMacAddress(hardwareId, macAddress));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull IHardware updateTokenActive(long hardwareId, boolean tokenIsActive) throws EntityNotPresentException {
    return HardwareFactory.convertToRecord(this.hardwareHelper.updateTokenActive(hardwareId, tokenIsActive));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean deleteHardware(long hardwareId) throws EntityNotPresentException {
    return this.hardwareHelper.deleteHardware(hardwareId);
  }
}
