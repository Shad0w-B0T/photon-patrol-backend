package de.hdm.photon.logic.hardware;

import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;


public interface IHardwareAccess<HARDWARE extends IHardware> {

  //------------------------------------------------CREATE--------------------------------------------------------------

  /**
   * Create a new empty hardware object.
   *
   * @return a new hardware object.
   */
  @NotNull
  HARDWARE createHardware();


  //--------------------------------------------------READ--------------------------------------------------------------

  /**
   * Get a hardware object via id.
   *
   * @param hardwareId the unique id of the hardware object.
   * @return hardware with the given id.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE getHardware(final long hardwareId) throws EntityNotPresentException;

  @NotNull
  HARDWARE getHardware(@Nullable final String token) throws EntityNotPresentException;

  /**
   * Get a list of all hardware objects.
   *
   * @return a list with HARDWARE objects.
   */
  @NotNull
  List<HARDWARE> getHardwareList();

  /**
   * Get the hardware via MAC address.
   *
   * @param macAddress value of the mac address.
   * @return the hardware which matches the mac address.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE getMacAddress(@Nullable final  String macAddress) throws EntityNotPresentException;

  /**
   * Get a list of all hardware objects which were added at the given date.
   *
   * @param hardwareAdded date when the hardware was added.
   * @return a list of hardware objects.
   */
  @NotNull
  List<HARDWARE> getHardwareAddedList(@Nullable final  Date hardwareAdded);

  /**
   * Get a list of all hardware objects when the mac was added.
   *
   * @param macAdded date when the mac was added.
   * @return a list of hardware objects.
   */
  @NotNull
  List<HARDWARE> getMacAddedList(@Nullable final  Date macAdded);

  /**
   * Get a list of all hardware objects which have an active/inactive token.
   *
   * @param tokenIsActive value whether the token is active or not.
   * @return a list of hardware objects.
   */
  @NotNull
  List<HARDWARE> getActiveTokenList(final boolean tokenIsActive);

  //------------------------------------------------UPDATE--------------------------------------------------------------

  //Todo: Docs
  @NotNull
  HARDWARE toggleHardwareActive(final long hardwareId) throws EntityNotPresentException;

  /**
   * This method updates a whole hardware object with the given parameters.
   *
   * @param hardwareId    unique id of the hardware object.
   * @param macAddress    value of the mac address.
   * @param hardwareAdded date when the hardware was added.
   * @param macAdded      date when the mac address first was added.
   * @param tokenIsActive value whether the token is active or not.
   * @return updated hardware object (hardware interface or hardware entity).
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException   see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateHardware(final long hardwareId,
                          @Nullable final String macAddress,
                          @Nullable final Date hardwareAdded,
                          @Nullable final Date macAdded,
                          final boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException;

  /**
   * This method updates the MAC address with the given parameter.
   *
   * @param hardwareId the unique id of the hardware.
   * @param macAddress value of the mac address.
   * @return hardware object with updated MAC address.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException   see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateMacAddress(final long hardwareId,
                            @Nullable final String macAddress) throws EntityNotPresentException, FormatMismatchException;

  /**
   * This method updates the token whether it is active or not.
   *
   * @param hardwareId    the unique id of the hardware.
   * @param tokenIsActive value whether the token is active or not.
   * @return hardware object with updated token.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException   see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateTokenActive(final long hardwareId,
                             final boolean tokenIsActive) throws EntityNotPresentException;

  //------------------------------------------------DELETE--------------------------------------------------------------

  /**
   * Deletes the hardware via id.
   *
   * @param hardwareId the unique id of the hardware.
   * @return true if the hardware was deleted.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  boolean deleteHardware(final long hardwareId) throws EntityNotPresentException;

}
