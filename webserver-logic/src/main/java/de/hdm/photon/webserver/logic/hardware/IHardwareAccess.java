package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;


public interface IHardwareAccess<HARDWARE extends IHardware> {

  //------------------------------------------------CREATE--------------------------------------------------------------

  /**
   * @param loggingId     id of the logging object.
   * @param token         value to verify permissions.
   * @param macAddress    value of the mac address.
   * @param hardwareAdded date when the hardware was added.
   * @param macAdded      date when the mac address first was added.
   * @param tokenIsActive value whether the token is active or not.
   * @return a new hardware object (hardware interface or hardware entity).
   * @throws EntityNotPresentException     see {@link EntityNotPresentException}.
   * @throws EntityAlreadyPresentException see {@link EntityAlreadyPresentException}.
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE createHardware(final long loggingId,
                          @Nullable final String token,
                          @Nullable final String macAddress,
                          @Nullable final Date hardwareAdded,
                          @Nullable final Date macAdded,
                          final boolean tokenIsActive) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException;


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
  HARDWARE getMacAddress(final String macAddress) throws EntityNotPresentException;

  /**
   * Get a list of all hardware objects which were added at the given date.
   *
   * @param hardwareAdded date when the hardware was added.
   * @return a list of hardware objects.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  List<HARDWARE> getHardwareAdded(final Date hardwareAdded) throws EntityNotPresentException;

  /**
   * Get a list of all hardware objects when the mac was added.
   *
   * @param macAdded date when the mac was added.
   * @return a list of hardware objects.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  List<HARDWARE> getMacAdded(final Date macAdded) throws EntityNotPresentException;

  /**
   * Get a list of all hardware objects which have an active/inactive token.
   *
   * @param tokenIsActive value whether the token is active or not.
   * @return a list of hardware objects.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  List<HARDWARE> getActiveToken(final boolean tokenIsActive) throws EntityNotPresentException;

  //------------------------------------------------UPDATE--------------------------------------------------------------

  /**
   * This method updates a whole hardware object with the given parameters.
   *
   * @param hardwareId            unique id of the hardware object.
   * @param token         value to verify permissions.
   * @param macAddress    value of the mac address.
   * @param hardwareAdded date when the hardware was added.
   * @param macAdded      date when the mac address first was added.
   * @param tokenIsActive value whether the token is active or not.
   * @return updated hardware object (hardware interface or hardware entity).
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateHardware(final long hardwareId,
                          @Nullable final String token,
                          @Nullable final String macAddress,
                          @Nullable final Date hardwareAdded,
                          @Nullable final Date macAdded,
                          final boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException;

  /**
   * This method updates the token with the given parameter.
   *
   * @param hardwareId    the unique id of the hardware.
   * @param token value to verify permissions.
   * @return updated hardware object with updated token.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateToken(final long hardwareId,
                       @Nullable final String token) throws EntityNotPresentException, FormatMismatchException;

  /**
   * This method updates the MAC address with the given parameter.
   *
   * @param hardwareId         the unique id of the hardware.
   * @param macAddress value of the mac address.
   * @return hardware object with updated MAC address.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateMacAddress(final long hardwareId,
                            @Nullable final String macAddress) throws EntityNotPresentException, FormatMismatchException;

  /**
   * This method updates the date when the hardware was added with the given parameter.
   *
   * @param hardwareId            the unique id of the hardware.
   * @param hardwareAdded date when the hardware was added.
   * @return hardware object with updated date of when the hardware was added.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateHardwareAdded(final long hardwareId,
                               @Nullable final Date hardwareAdded) throws EntityNotPresentException, FormatMismatchException;

  /**
   * This method updates the date when the MAC address was added with the given parameter.
   *
   * @param hardwareId       the unique id of the hardware.
   * @param macAdded date when the mac address first was added.
   * @return hardware object with updated date of when the MAC was added.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateMacAdded(final long hardwareId,
                          @Nullable final Date macAdded) throws EntityNotPresentException, FormatMismatchException;

  /**
   * This method updates the token whether it is active or not.
   *
   * @param hardwareId            the unique id of the hardware.
   * @param tokenIsActive value whether the token is active or not.
   * @return hardware object with updated token.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE updateTokenActive(final long hardwareId,
                             final boolean tokenIsActive) throws EntityNotPresentException, FormatMismatchException;

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
