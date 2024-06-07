package de.hdm.photon.webserver.logic.ceck;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import de.hdm.photon.schema.exception.auth.FormatMismatchException;

public interface IAddressVerifier {

  void validateAddress(@NotNull String address, final long size) throws FormatMismatchException;

  /**
   * Check if content is valid, if not throw error.
   *
   * @param address to check.
   * @throws FormatMismatchException if content is not valid.
   * @throws NullPointerException    if content is null.
   */
  void validateLength(@Nullable final String address, final long size) throws FormatMismatchException;


  /**
   * Check if content is valid, if not throw error.
   *
   * @param address to check.
   * @throws FormatMismatchException if content is not valid.
   * @throws NullPointerException    if content is null.
   */
  void validateContent(@Nullable final String address) throws FormatMismatchException;

}
