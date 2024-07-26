package de.hdm.photon.webserver.logic.logging;

import de.hdm.photon.schema.exception.FormatMismatchException;
import de.hdm.photon.schema.exception.EntityAlreadyPresentException;
import de.hdm.photon.schema.exception.EntityNotPresentException;
import de.hdm.photon.schema.logging.ILogging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;


public interface ILoggingAccess<LOGGING extends ILogging> {


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
  LOGGING createLogging(@Nullable String ipAddress) throws EntityNotPresentException, EntityAlreadyPresentException, FormatMismatchException;


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

  /**
   * Get a list of all logging objects which were added at the given date.
   *
   * @param timestampOfIp date when the ip was used.
   * @return the logging object which matches the timestamp of the ip.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   */
  @NotNull
  List<LOGGING> getTimestampOfIp(final Date timestampOfIp) throws EntityNotPresentException;

  //------------------------------------------------UPDATE--------------------------------------------------------------

  /**
   * This method updates a whole logging object with the given parameters.
   *
   * @param loggingId            unique id of the hardware object.
   * @param ipAddress     value of the ip address.
   * @param timestampOfIp date when the ip was used.
   * @return updated logging object (logging interface or logging entity).
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException   see {@link FormatMismatchException}
   */
  @NotNull
  LOGGING updateLogging(final long loggingId,
                        @Nullable final String ipAddress,
                        @Nullable final Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException, EntityAlreadyPresentException;

  /**
   * This method updates the ip address with the given parameter.
   *
   * @param loggingId        the unique id of the hardware.
   * @param ipAddress value of the updated ip address.
   * @return updated logging object with updated ip.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException   see {@link FormatMismatchException}
   */
  @NotNull
  LOGGING updateIpAddress(final long loggingId,
                          @Nullable final String ipAddress) throws EntityNotPresentException, FormatMismatchException;

  /**
   * This method updates the timestamp of the ip address with the given parameter.
   *
   * @param loggingId            the unique id of the hardware.
   * @param timestampOfIp date of when the ip was used.
   * @return logging object with updated timestamp of ip address.
   * @throws EntityNotPresentException see {@link EntityNotPresentException}
   * @throws FormatMismatchException   see {@link FormatMismatchException}
   */
  @NotNull
  LOGGING updateTimestampOfIp(final long loggingId,
                              @Nullable final Date timestampOfIp) throws EntityNotPresentException, FormatMismatchException;

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
