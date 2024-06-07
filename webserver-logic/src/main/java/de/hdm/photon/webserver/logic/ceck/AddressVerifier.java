package de.hdm.photon.webserver.logic.ceck;


import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public class AddressVerifier implements IAddressVerifier {

  @Override
  public void validateAddress(@NotNull String address, long size) throws FormatMismatchException {
    this.validateLength(address, size);
    this.validateContent(address);
    }

  @Override
  public void validateLength(@Nullable String address, long size) throws NullPointerException, FormatMismatchException {
    if (Objects.requireNonNull(address).length() != size) {
      throw new FormatMismatchException("MacAddress with invalid length");
    }
  }
  @Override
  public void validateContent(@Nullable String address) throws FormatMismatchException {
    address = Objects.requireNonNull(address).toLowerCase();

    for (char c : address.toCharArray()) {
      if (c<'a' || c>'f') {
        throw new FormatMismatchException("MacAddress contains not allowed Chars");
      }
    }
  }
}
