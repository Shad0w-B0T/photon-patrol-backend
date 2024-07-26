package de.hdm.photon.webserver.logic.check;

import de.hdm.photon.schema.exception.FormatMismatchException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface IAddressVerifier extends IContentVerifier {

  /**
   * Validates the mac by the {@link ILengthVerifier#validateLength(String, String)}} to check if the length is
   * accepted and {@link IAddressVerifier#validateContentOfIP(String)} or {@link IAddressVerifier#validateContentOfMac(String)}
   * to check if the content matches the conditions.
   *
   * @param macAddress value of the mac address.
   * @return true if the mac is valid.
   * @throws FormatMismatchException if content is not valid.
   * @throws NullPointerException    if content is null.
   */
  boolean validateMacAddress(@NotNull final String macAddress) throws FormatMismatchException, NullPointerException;

  /**
   * Validates the ip by the {@link ILengthVerifier#validateLength(String, String)}} to check if the length is
   * accepted and {@link IAddressVerifier#validateContentOfIP(String)} or {@link IAddressVerifier#validateContentOfMac(String)}
   * to check if the content matches the conditions.
   *
   * @param ipAddress the value of the ip address.
   * @return true if the ip is valid.
   * @throws FormatMismatchException if content is not valid.
   * @throws NullPointerException    if content is null.
   */
  boolean validateIpAddress(@NotNull final String ipAddress) throws FormatMismatchException, NullPointerException;

  /**
   * Check if content is valid, if not throw error.
   *
   * @param ipAddress to check.
   * @return true if content is valid.
   * @throws FormatMismatchException if content is not valid.
   * @throws NullPointerException    if content is null.
   */
  boolean validateContentOfIP(@Nullable final String ipAddress) throws FormatMismatchException, NullPointerException;


  /**
   * Check if content is valid, if not throw error.
   *
   * @param macAddress to check.
   * @return true if content is valid.
   * @throws FormatMismatchException if content is not valid.
   * @throws NullPointerException    if content is null.
   */
  boolean validateContentOfMac(@Nullable final String macAddress) throws FormatMismatchException, NullPointerException;
}
