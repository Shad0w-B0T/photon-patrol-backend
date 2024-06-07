package de.hdm.photon.webserver.logic.hardware;

import de.hdm.photon.schema.exception.entity.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import jakarta.validation.constraints.Null;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;


public interface IHardwareAccess<HARDWARE extends IHardware> {

  //------------------------------------------------CREATE--------------------------------------------------------------

  /**
   * @param token         value to verify permissions.
   * @param macAddress    value of the mac address.
   * @param hardwareAdded date when the hardware was added.
   * @param macAdded      date when the mac address first was added.
   * @param tokenIsActive value whether the token is active or not.
   * @return a new hardware object (hardware interface or hardware entity).
   * @throws EntityNotPresentException     see {@link EntityNotPresentException}
   * @throws EntityAlreadyPresentException see {@link EntityAlreadyPresentException}
   */
  @NotNull
  HARDWARE createHardware(@Nullable final String token,
                          @Nullable final String macAddress,
                          @Nullable final Date hardwareAdded,
                          @Nullable final Date macAdded,
                          final boolean tokenIsActive) throws EntityNotPresentException, EntityAlreadyPresentException;


  //--------------------------------------------------READ--------------------------------------------------------------

  /**
   * Get a hardware object via id.
   *
   * @param id the unique id of the hardware object.
   * @return hardware with the given id.
   * @throws EntityAlreadyPresentException see {@link EntityAlreadyPresentException}
   */
  @NotNull
  HARDWARE getHardware(final long id) throws EntityAlreadyPresentException;


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
   * @param id            unique id of the hardware object.
   * @param token         value to verify permissions.
   * @param macAddress    value of the mac address.
   * @param hardwareAdded date when the hardware was added.
   * @param macAdded      date when the mac address first was added.
   * @param tokenIsActive value whether the token is active or not.
   * @return updated hardware object (hardware interface or hardware entity).
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE updateHardware(final long id,
                          @Nullable final String token,
                          @Nullable final String macAddress,
                          @Nullable final Date hardwareAdded,
                          @Nullable final Date macAdded,
                          final boolean tokenIsActive) throws EntityNotPresentException;

  /**
   * This method updates the token with the given parameter.
   *
   * @param id    the unique id of the hardware.
   * @param token value to verify permissions.
   * @return updated hardware object with updated token.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE updateToken(final long id,
                       @Nullable final String token) throws EntityNotPresentException;

  /**
   * This method updates the MAC address with the given parameter.
   *
   * @param id         the unique id of the hardware.
   * @param macAddress value of the mac address.
   * @return hardware object with updated MAC address.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE updateMacAddress(final long id,
                            @Nullable final String macAddress) throws EntityNotPresentException;

  /**
   * This method updates the date when the hardware was added with the given parameter.
   *
   * @param id            the unique id of the hardware.
   * @param hardwareAdded date when the hardware was added.
   * @return hardware object with updated date of when the hardware was added.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE updateHardwareAdded(final long id,
                               @Nullable final Date hardwareAdded) throws EntityNotPresentException;

  /**
   * This method updates the date when the MAC address was added with the given parameter.
   *
   * @param id       the unique id of the hardware.
   * @param macAdded date when the mac address first was added.
   * @return hardware object with updated date of when the MAC was added.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE updateMacAdded(final long id,
                          @Nullable final Date macAdded) throws EntityNotPresentException;

  /**
   * This method updates the token whether it is active or not.
   *
   * @param id            the unique id of the hardware.
   * @param tokenIsActive value whether the token is active or not.
   * @return hardware object with updated token.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE updateTokenActive(final long id,
                             final boolean tokenIsActive) throws EntityNotPresentException;

  //------------------------------------------------DELETE--------------------------------------------------------------

  /**
   * Deletes the hardware via id.
   *
   * @param id the unique id of the hardware.
   * @return true if the hardware was deleted.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  boolean deleteHardware(final long id) throws EntityNotPresentException;

}
