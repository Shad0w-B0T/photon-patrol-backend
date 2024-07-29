package de.hdm.photon.logic.logging;

import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.hardware.IHardwareSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface IHardwareSessionAccess<LOGGING extends IHardwareSession> {


  //------------------------------------------------CREATE--------------------------------------------------------------

  /**
   * Create a logging object
   *
   * @param ipAddress     value of the ip.
   * @return a new logging object (logging interface or logging entity).
   * @throws EntityNotPresentException     see {@link EntityNotPresentException}.
   * @throws EntityAlreadyPresentException see {@link EntityAlreadyPresentException}.
   * @throws FormatMismatchException       see {@link FormatMismatchException}
   */
  @NotNull
  LOGGING createLogging(@Nullable String ipAddress,
                        long hardwareId) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException;


  //--------------------------------------------------READ--------------------------------------------------------------

  /**
   * Get a logging object via id.
   *
   * @param loggingId the unique id of the hardware object.
   * @return loggingEntity with the given id.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  LOGGING getLogging(final long loggingId) throws EntityNotPresentException;


  /**
   * Get a list of all logging objects.
   *
   * @return a list with LOGGING objects.
   */
  @NotNull
  List<LOGGING> getLoggingList();

  /**
   * Get the logging via IP address.
   *
   * @param ipAddress value of the ip address.
   * @return the logging object which matches the ip address.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  LOGGING getIpAddress(final String ipAddress) throws EntityNotPresentException;

  //------------------------------------------------DELETE--------------------------------------------------------------

  /**
   * Deletes the logging object via id.
   *
   * @param loggingId the unique id of the hardware.
   * @return true if the logging object was deleted.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  boolean deleteLogging(final long loggingId) throws EntityNotPresentException;

}
