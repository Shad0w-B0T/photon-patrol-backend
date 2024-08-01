package de.hdm.photon.logic.hardware.session;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.hardware.session.IHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface IHardwareSessionAccess<HARDWARE_SESSION extends IHardwareSession> {


  //------------------------------------------------CREATE--------------------------------------------------------------

  /**
   * Create a hardware session object
   *
   * @param ipAddress  value of the ip.
   * @param hardwareId id of the hardware.
   * @return a new hardware session object (hardware session interface or hardware session entity).
   * @throws EntityNotPresentException     see {@link EntityNotPresentException}.
   * @throws EntityAlreadyPresentException see {@link EntityAlreadyPresentException}.
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  HARDWARE_SESSION createHardwareSession(@Nullable String ipAddress,
                                         long hardwareId) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException;


  //--------------------------------------------------READ--------------------------------------------------------------

  /**
   * Get a hardware session object via id.
   *
   * @param hardwareSessionId the unique id of the hardware object.
   * @return hardwareSessionEntity with the given id.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE_SESSION getHardwareSession(final long hardwareSessionId) throws EntityNotPresentException;


  /**
   * Get a list of all hardware session objects.
   *
   * @return a list with HARDWARE_SESSION objects.
   */
  @NotNull
  List<HARDWARE_SESSION> getHardwareSessionList();

  /**
   * Get the hardware session via IP address.
   *
   * @param ipAddress value of the ip address.
   * @return the hardware session object which matches the ip address.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  HARDWARE_SESSION getIpAddress(final String ipAddress) throws EntityNotPresentException;

  //------------------------------------------------DELETE--------------------------------------------------------------

  /**
   * Deletes the hardware session object via id.
   *
   * @param hardwareSessionId the unique id of the hardware.
   * @return true if the hardware session object was deleted.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  boolean deleteHardwareSession(final long hardwareSessionId) throws EntityNotPresentException;

}
