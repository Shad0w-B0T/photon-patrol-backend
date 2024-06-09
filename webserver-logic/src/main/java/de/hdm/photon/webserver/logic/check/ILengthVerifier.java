package de.hdm.photon.webserver.logic.check;

import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import org.jetbrains.annotations.Nullable;


public interface ILengthVerifier extends IContentVerifier {


  /**
   * Check if length is valid, if not throw error.
   *
   * @param address to check.
   * @param typeOfAddress whether the address is an IPv4, IPv6 or MAC.
   * @return true, if given address is valid length.
   * @throws FormatMismatchException if content is not valid.
   * @throws NullPointerException    if content is null.
   */
  boolean validateLength(@Nullable final String address,
                         @Nullable final String typeOfAddress) throws FormatMismatchException, NullPointerException;


}
