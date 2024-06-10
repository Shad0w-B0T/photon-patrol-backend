package de.hdm.photon.webserver.logic.check;

import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Log4j2
@Component
public final class LengthVerifier implements ILengthVerifier {

  /**
   * see {@link ILengthVerifier#validateLength(String, String)}
   */
  @Override
  public boolean validateLength(@Nullable String address,
                                @Nullable String typeOfAddress) throws NullPointerException, FormatMismatchException {
    //Null checks
    Objects.requireNonNull(address);
    Objects.requireNonNull(typeOfAddress);
    if (address.isBlank() || typeOfAddress.isBlank()) {
      throw new FormatMismatchException("Address is empty.");
    }

    /*
    * Compare if the given address is as long as it's supposed to be.
    * If not, throw FormatMismatchException.
    */
    int lengthOfAddress = 0;
    switch (typeOfAddress) {
      case "IPv4" -> lengthOfAddress = IPv4_CHARACTER_LENGTH;
      case "MAC" -> lengthOfAddress = MAC_ADDRESS_LENGTH;
      default -> throw new FormatMismatchException("Invalid type of address. Valid types are IPv4, MAC.");
    }

    return address.length() == lengthOfAddress;
  }
}
